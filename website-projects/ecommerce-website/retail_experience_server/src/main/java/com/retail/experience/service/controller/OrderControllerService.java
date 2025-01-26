package com.retail.experience.service.controller;

import base.model.request.Item;
import base.model.request.OrderRequest;
import base.model.response.OrderResponse;
import com.retail.experience.dao.ServerOrderStatusCache;
import com.retail.experience.exception.ApplicationProcessException;
import com.retail.experience.exception.RepositoryManipulationException;
import com.retail.experience.exception.ServiceMappingException;
import com.retail.experience.model.*;
import com.retail.experience.monitor.ServerMonitorSystem;
import com.retail.experience.service.base.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class OrderControllerService {

    private final CpuService cpuService;
    private final GpuService gpuService;
    private final KeyboardService keyboardService;
    private final MemoryService memoryService;
    private final MonitorService monitorService;
    private final MouseService mouseService;
    private final StorageService storageService;
    private static final ReentrantLock reentrantLock = new ReentrantLock(true);

    @Autowired
    public OrderControllerService(CpuService cpuService, GpuService gpuService,
                                  KeyboardService keyboardService, MemoryService memoryService,
                                  MonitorService monitorService, MouseService mouseService,
                                  StorageService storageService) {
        this.cpuService = cpuService;
        this.gpuService = gpuService;
        this.keyboardService = keyboardService;
        this.memoryService = memoryService;
        this.monitorService = monitorService;
        this.mouseService = mouseService;
        this.storageService = storageService;
    }

    public List<CPU> listCpuItems() throws RepositoryManipulationException {
        return cpuService.getAllComponents();
    }

    public List<GPU> listGpuItems() throws RepositoryManipulationException {
        return gpuService.getAllComponents();
    }

    public List<Keyboard> listKeyboardItems() throws RepositoryManipulationException {
        return keyboardService.getAllComponents();
    }

    public List<Monitor> listMonitorItems() throws RepositoryManipulationException {
        return monitorService.getAllComponents();
    }

    public List<Mouse> listMouseItems() throws RepositoryManipulationException {
        return mouseService.getAllComponents();
    }

    public List<Storage> listStorageItems() throws RepositoryManipulationException {
        return storageService.getAllComponents();
    }

    public List<Memory> listMemoryItems() throws RepositoryManipulationException {
        return memoryService.getAllComponents();
    }

    @Async("threadPoolTaskExecutor")
    public void processOrderAsync(OrderRequest order) throws ApplicationProcessException, InterruptedException {
        long startTime = System.currentTimeMillis();
        OrderResponse response = assembleComputer(order);
        long endTime = System.currentTimeMillis();
        String timeSpent = "Time Spent: " + (endTime - startTime) + "(ms)";
        publishOrderResponseMessages(order.getOrderId(), response.getMessages(), timeSpent);
    }

    public OrderResponse assembleComputer(OrderRequest order) throws InterruptedException, ApplicationProcessException {
        ServerMonitorSystem.increaseNumberOfConnectedClients();
        ServerMonitorSystem.increaseNumberOfOrdersWaiting();
        // Simulate waiting 5 seconds before assembling the computer
        Thread.sleep(5000);
        ServerOrderStatusCache.addStatusMessage(order.getOrderId(), "Order In-Progress: The assembler has started building the computer.");
        ServerMonitorSystem.decreaseNumberOfOrdersWaiting();
        ServerMonitorSystem.increaseNumberOfOrdersProcessing();
        // Assembling a computer requires manual work, assume that this work requires 15 seconds
        Thread.sleep(15000);
        OrderResponse response = assemble(order);
        ServerMonitorSystem.decreaseNumberOfOrdersProcessing();
        ServerMonitorSystem.increaseNumberOfOrdersDone();
        return response;
    }

    public OrderResponse assemble(OrderRequest order) throws ApplicationProcessException {
        reentrantLock.lock();
        try {
            processOrderItems(order.getItemList());
            List<String> messages = new ArrayList<>();
            messages.add("Finished OK.");
            return new OrderResponse(order.getOrderId(), true, messages);
        } catch (ApplicationProcessException exception) {
            List<String> message = order.recordItemsStatus();
            restoreOrderItems(order.getItemList());
            return new OrderResponse(order.getOrderId(), false, message);
        } finally {
            reentrantLock.unlock();
        }
    }

    public void processOrderItems(List<Item> items) throws ApplicationProcessException {
        for (Item item : items) {
            BaseService service = getServiceByCategory(item.getCategory());
            service.reduceStockById(item.getId(), item.getQuantity());
            item.setHasCompleted(true);
        }
    }

    public void restoreOrderItems(List<Item> items) throws ApplicationProcessException {
        for (Item item : items) {
            if (item.isHasCompleted()) {
                BaseService service = getServiceByCategory(item.getCategory());
                service.restoreStockById(item.getId(), item.getQuantity());
                item.setHasCompleted(false);
            }
        }
    }

    public BaseService getServiceByCategory(String category) throws ServiceMappingException {
        if (category.equalsIgnoreCase("cpu")) {
            return cpuService;
        } else if (category.equalsIgnoreCase("gpu")) {
            return gpuService;
        } else if (category.equalsIgnoreCase("keyboard")) {
            return keyboardService;
        } else if (category.equalsIgnoreCase("memory")) {
            return memoryService;
        } else if (category.equalsIgnoreCase("monitor")) {
            return monitorService;
        } else if (category.equalsIgnoreCase("mouse")) {
            return mouseService;
        } else if (category.equalsIgnoreCase("storage")) {
            return storageService;
        } else {
            throw new ServiceMappingException("Can not map category: " + category);
        }
    }

    public void publishOrderResponseMessages(String orderId, List<String> messages, String timeSpent) throws InterruptedException {
        if (messages.get(0).contains("OK")) {
            ServerOrderStatusCache.addStatusMessage(orderId, "Order Ready: the computer has been built and ready for delivery.");
            // Simulate waiting 3 seconds for computer delivery
            Thread.sleep(3000);
            ServerOrderStatusCache.addStatusMessage(orderId, "Delivery Order Success.");
        } else {
            ServerOrderStatusCache.addStatusMessage(orderId, "Order Failed: please check the order feedback below...");
            for (String message : messages) {
                ServerOrderStatusCache.addStatusMessage(orderId, message);
            }
        }
        // Final message: return the time spent for this order id
        ServerOrderStatusCache.addStatusMessage(orderId, timeSpent);
    }
}
