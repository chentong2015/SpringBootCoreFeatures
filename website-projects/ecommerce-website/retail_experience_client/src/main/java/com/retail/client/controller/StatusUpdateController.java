package com.retail.client.controller;

import base.model.request.OrderDetail;
import com.retail.client.cache.LocalOrderDetailsCache;
import com.retail.client.model.order.OrderStatusProgress;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatusUpdateController {

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping("/status/refresh/{orderId}")
    public OrderStatusProgress refreshProgress(@PathVariable String orderId) {
        OrderDetail orderDetail = LocalOrderDetailsCache.getOrderDetails(orderId);
        int percentage = calculatePercentage(orderDetail);
        List<String> statusList = orderDetail.getStatusList();
        return new OrderStatusProgress(percentage, statusList);
    }

    private int calculatePercentage(OrderDetail orderDetail) {
        int sizeStatus = orderDetail.getStatusList().size();
        return sizeStatus >= 5 ? 100 : sizeStatus * 20;
    }
}
