package com.retail.experience.service.controller;

import base.model.monitor.OrderStatus;
import base.model.request.Item;
import base.model.request.OrderRequest;
import base.model.response.OrderResponse;
import com.retail.experience.ServerApplication;
import com.retail.experience.dao.ServerOrderStatusCache;
import com.retail.experience.exception.ApplicationProcessException;
import com.retail.experience.exception.RepositoryManipulationException;
import com.retail.experience.exception.ServiceMappingException;
import com.retail.experience.helper.OrderHelper;
import com.retail.experience.model.*;
import com.retail.experience.service.base.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class)
class OrderControllerServiceTest {

    @Autowired
    private OrderControllerService orderService;

    @Test
    void listCpuItems() throws RepositoryManipulationException {
        List<CPU> cpuList = orderService.listCpuItems();
        Assertions.assertTrue(cpuList.size() > 0);
    }

    @Test
    void listGpuItems() throws RepositoryManipulationException {
        List<GPU> gpuList = orderService.listGpuItems();
        Assertions.assertTrue(gpuList.size() > 0);
    }

    @Test
    void listKeyboardItems() throws RepositoryManipulationException {
        List<Keyboard> keyboardList = orderService.listKeyboardItems();
        Assertions.assertTrue(keyboardList.size() > 0);
    }

    @Test
    void listMonitorItems() throws RepositoryManipulationException {
        List<Monitor> monitorList = orderService.listMonitorItems();
        Assertions.assertTrue(monitorList.size() > 0);
    }

    @Test
    void listMouseItems() throws RepositoryManipulationException {
        List<Mouse> mouseList = orderService.listMouseItems();
        Assertions.assertTrue(mouseList.size() > 0);
    }

    @Test
    void listStorageItems() throws RepositoryManipulationException {
        List<Storage> storageList = orderService.listStorageItems();
        Assertions.assertTrue(storageList.size() > 0);
    }

    @Test
    void listMemoryItems() throws RepositoryManipulationException {
        List<Memory> memoryList = orderService.listMemoryItems();
        Assertions.assertTrue(memoryList.size() > 0);
    }

    @Test
    void assemble_computer_successfully() throws ApplicationProcessException, InterruptedException {
        OrderRequest order = OrderHelper.generateSameOrderRequest();
        OrderResponse response = orderService.assembleComputer(order);
        Assertions.assertEquals(response.getOrderId(), order.getOrderId());
    }

    @Test
    void assemble_computer_failed() throws ApplicationProcessException, InterruptedException {
        OrderRequest orderRequest = OrderHelper.generateOrderRequestWithOneItem();
        OrderResponse response = orderService.assembleComputer(orderRequest);
        Assertions.assertEquals("orderId", response.getOrderId());
    }

    @Test
    void assemble_same_order() throws InterruptedException {
        OrderRequest order = OrderHelper.generateSameOrderRequest();
        Thread[] threads = new Thread[20];
        for (int index = 0; index < 20; index++) {
            threads[index] = new Thread(() -> {
                try {
                    OrderResponse response = orderService.assemble(order);
                    boolean hasCompleted = order.hasCompletedAllItems();
                    if (response.getOrderStatus()) {
                        Assertions.assertTrue(hasCompleted);
                    } else {
                        Assertions.assertFalse(hasCompleted);
                    }
                } catch (ApplicationProcessException e) {
                    e.printStackTrace();
                }
            });
            threads[index].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    @Test
    void assemble_random_order() throws InterruptedException {
        OrderRequest order = OrderHelper.generateRandomOrderRequest();
        Thread[] threads = new Thread[20];
        for (int index = 0; index < 20; index++) {
            threads[index] = new Thread(() -> {
                try {
                    OrderResponse response = orderService.assemble(order);
                    boolean hasCompleted = order.hasCompletedAllItems();
                    if (response.getOrderStatus()) {
                        Assertions.assertTrue(hasCompleted);
                    } else {
                        Assertions.assertFalse(hasCompleted);
                    }
                } catch (ApplicationProcessException e) {
                    e.printStackTrace();
                }
            });
            threads[index].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    @Test
    void process_order_items_successfully() throws ApplicationProcessException {
        List<Item> itemList = new ArrayList<>();
        Item itemCpu = new Item(1, "CPU", 1);
        Item itemGpu = new Item(81, "GPU", 1);
        itemList.add(itemCpu);
        itemList.add(itemGpu);
        orderService.processOrderItems(itemList);
        for (Item item : itemList) {
            Assertions.assertTrue(item.isHasCompleted());
        }
    }

    @Test
    void process_order_items_failed_with_exception() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(1, "XXX", 1));
        Assertions.assertThrows(ServiceMappingException.class, () -> orderService.processOrderItems(itemList));
    }

    @Test
    void restore_order_items_successfully() throws ApplicationProcessException {
        List<Item> itemList = new ArrayList<>();
        Item itemCpu = new Item(1, "CPU", 1, true);
        Item itemGpu = new Item(81, "GPU", 1, true);
        itemList.add(itemCpu);
        itemList.add(itemGpu);
        orderService.restoreOrderItems(itemList);
        for (Item item : itemList) {
            Assertions.assertFalse(item.isHasCompleted());
        }
    }

    @Test
    void restore_order_items_failed_with_exception() {
        List<Item> itemList = new ArrayList<>();
        Item itemCpu = new Item(1, "XXX", 1, true);
        itemList.add(itemCpu);
        Assertions.assertThrows(ServiceMappingException.class, () -> orderService.restoreOrderItems(itemList));
    }

    @Test
    void get_cpu_service_by_category_name() throws ServiceMappingException {
        BaseService componentService = orderService.getServiceByCategory("cpu");
        Assertions.assertTrue(componentService instanceof CpuService);
    }

    @Test
    void get_gpu_service_by_category_name() throws ServiceMappingException {
        BaseService componentService = orderService.getServiceByCategory("gpu");
        Assertions.assertTrue(componentService instanceof GpuService);
    }

    @Test
    void get_keyboard_service_by_category_name() throws ServiceMappingException {
        BaseService componentService = orderService.getServiceByCategory("keyboard");
        Assertions.assertTrue(componentService instanceof KeyboardService);
    }

    @Test
    void get_memory_service_by_category_name() throws ServiceMappingException {
        BaseService componentService = orderService.getServiceByCategory("memory");
        Assertions.assertTrue(componentService instanceof MemoryService);
    }

    @Test
    void get_monitor_service_by_category_name() throws ServiceMappingException {
        BaseService componentService = orderService.getServiceByCategory("monitor");
        Assertions.assertTrue(componentService instanceof MonitorService);
    }

    @Test
    void get_mouse_service_by_category_name() throws ServiceMappingException {
        BaseService componentService = orderService.getServiceByCategory("mouse");
        Assertions.assertTrue(componentService instanceof MouseService);
    }

    @Test
    void get_storage_service_by_category_name() throws ServiceMappingException {
        BaseService componentService = orderService.getServiceByCategory("storage");
        Assertions.assertTrue(componentService instanceof StorageService);
    }

    @Test
    void get_service_by_category_name_failed() {
        Assertions.assertThrows(ServiceMappingException.class, () -> orderService.getServiceByCategory("xxx"));
    }

    @Test
    void publish_order_response_messages_successfully() throws InterruptedException {
        String orderId = UUID.randomUUID().toString();
        List<String> messages = new ArrayList<>();
        messages.add("Finished OK.");
        orderService.publishOrderResponseMessages(orderId, messages, "0");

        OrderStatus orderStatus = ServerOrderStatusCache.getOrderStatusById(orderId);
        Assertions.assertEquals(orderId, orderStatus.getOrderId());
        Assertions.assertTrue(orderStatus.getStatusList().get(0).contains("Order Ready"));
    }

    @Test
    void publish_order_response_messages_failed() throws InterruptedException {
        String orderId = UUID.randomUUID().toString();
        List<String> messages = new ArrayList<>();
        messages.add("message failed");
        orderService.publishOrderResponseMessages(orderId, messages, "0");

        OrderStatus orderStatus = ServerOrderStatusCache.getOrderStatusById(orderId);
        Assertions.assertEquals(orderId, orderStatus.getOrderId());
        Assertions.assertTrue(orderStatus.getStatusList().get(0).contains("Order Failed"));
    }
}