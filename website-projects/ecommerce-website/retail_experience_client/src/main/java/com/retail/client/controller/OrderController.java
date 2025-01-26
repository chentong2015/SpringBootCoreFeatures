package com.retail.client.controller;

import base.model.request.Item;
import base.model.request.OrderDetail;
import base.model.request.OrderItem;
import base.model.request.OrderRequest;
import com.retail.client.cache.LocalComponentCache;
import com.retail.client.cache.LocalOrderDetailsCache;
import com.retail.client.model.order.OrderInput;
import com.retail.client.networking.ApacheHttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class OrderController {

    private final LocalComponentCache componentCache;

    public OrderController() {
        componentCache = new LocalComponentCache();
    }

    @GetMapping("/list")
    public String list(Model model) throws IOException {
        componentCache.loadComponentsToCache();
        model.addAttribute("cpuList", componentCache.getCpuList());
        model.addAttribute("gpuList", componentCache.getGpuList());
        model.addAttribute("keyboardList", componentCache.getKeyboardList());
        model.addAttribute("memoryList", componentCache.getMemoryList());
        model.addAttribute("monitorList", componentCache.getMonitorList());
        model.addAttribute("mouseList", componentCache.getMouseList());
        model.addAttribute("storageList", componentCache.getStorageList());
        return "order";
    }

    @PostMapping("/order")
    public String order(String checkboxInput, String selectInput, String username, String address) throws IOException {
        // 每拿到一个新的请求，则生成一个随机的UUID
        String orderId = UUID.randomUUID().toString();
        OrderInput orderInput = new OrderInput(orderId, username, address);
        orderInput.setCategoriesAndIds(checkboxInput);
        orderInput.setQuantities(selectInput);

        // 缓存请求的订单信息在本地的Cache
        LocalOrderDetailsCache.addOrderDetail(generateOrderDetail(orderInput));
        ApacheHttpClient.sendOrderAsync(generateOrderRequest(orderInput));

        // 拿到结果之后会
        return "redirect:/status/" + orderId;
    }

    private OrderDetail generateOrderDetail(OrderInput orderInput) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(orderInput.getOrderId());
        orderDetail.setDeliveryTime(LocalDate.now().plusDays(5).toString());
        orderDetail.setUsername(orderInput.getUsername());
        orderDetail.setAddress(orderInput.getAddress());
        List<OrderItem> orderItems = componentCache.getOrderItemsList(orderInput);
        orderDetail.setOrderItems(orderItems);
        List<String> statusList = new ArrayList<>();
        statusList.add("Order has been created successfully.");
        orderDetail.setStatusList(statusList);
        return orderDetail;
    }

    private OrderRequest generateOrderRequest(OrderInput orderInput) {
        OrderRequest order = new OrderRequest();
        order.setOrderId(orderInput.getOrderId());
        for (int index = 0; index < orderInput.getCategories().length; index++) {
            Item item = new Item();
            item.setId(Long.parseLong(orderInput.getIds()[index]));
            item.setCategory(orderInput.getCategories()[index]);
            item.setQuantity(Integer.parseInt(orderInput.getQuantities()[index]));
            order.getItemList().add(item);
        }
        return order;
    }
}
