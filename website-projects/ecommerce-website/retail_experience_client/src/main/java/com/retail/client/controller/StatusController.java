package com.retail.client.controller;

import base.model.request.OrderDetail;
import com.retail.client.cache.LocalOrderDetailsCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StatusController {

    private static final String STATUS_PAGE = "status";

    @GetMapping("/status/{orderId}")
    public String status(@PathVariable String orderId, Model model) {
        OrderDetail orderDetail = LocalOrderDetailsCache.getOrderDetails(orderId);
        model.addAttribute("orderDetail", orderDetail);
        return STATUS_PAGE;
    }

    @GetMapping("/status/search")
    public String search(@RequestParam(name = "searchOrderId", defaultValue = "default") String searchOrderId, Model model) {
        OrderDetail orderDetail = LocalOrderDetailsCache.getOrderDetails(searchOrderId);
        model.addAttribute("orderDetail", orderDetail);
        return STATUS_PAGE;
    }
}
