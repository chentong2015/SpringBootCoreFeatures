package com.retail.experience.controller;

import base.model.request.OrderRequest;
import base.model.response.OrderResponse;
import com.retail.experience.exception.ApplicationProcessException;
import com.retail.experience.exception.RepositoryManipulationException;
import com.retail.experience.model.*;
import com.retail.experience.service.controller.OrderControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderControllerService orderService;

    @Autowired
    public OrderController(OrderControllerService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/list/cpu")
    public List<CPU> listCpu() throws RepositoryManipulationException {
        return orderService.listCpuItems();
    }

    @GetMapping(value = "/list/gpu")
    public List<GPU> listGpu() throws RepositoryManipulationException {
        return orderService.listGpuItems();
    }

    @GetMapping(value = "/list/keyboard")
    public List<Keyboard> listKeyboard() throws RepositoryManipulationException {
        return orderService.listKeyboardItems();
    }

    @GetMapping(value = "/list/memory")
    public List<Memory> listMemory() throws RepositoryManipulationException {
        return orderService.listMemoryItems();
    }

    @GetMapping(value = "/list/monitor")
    public List<Monitor> listMonitor() throws RepositoryManipulationException {
        return orderService.listMonitorItems();
    }

    @GetMapping(value = "/list/mouse")
    public List<Mouse> listMouse() throws RepositoryManipulationException {
        return orderService.listMouseItems();
    }

    @GetMapping(value = "/list/storage")
    public List<Storage> listStorage() throws RepositoryManipulationException {
        return orderService.listStorageItems();
    }

    @PostMapping(value = "/computer")
    public OrderResponse orderComputerAsync(@RequestBody OrderRequest order) throws InterruptedException, ApplicationProcessException {
        orderService.processOrderAsync(order);
        List<String> messages = new ArrayList<>();
        messages.add("Order Received: Order has been received by the server.");
        return new OrderResponse(order.getOrderId(), true, messages);
    }
}
