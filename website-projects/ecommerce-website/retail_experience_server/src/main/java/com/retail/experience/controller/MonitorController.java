package com.retail.experience.controller;

import base.model.monitor.OrderStatus;
import com.retail.experience.dao.ServerOrderStatusCache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MonitorController {

    @ResponseBody
    @GetMapping("/monitor/order/status/{orderId}")
    public OrderStatus getOrderStatus(@PathVariable String orderId) {
        return ServerOrderStatusCache.getOrderStatusById(orderId);
    }
}
