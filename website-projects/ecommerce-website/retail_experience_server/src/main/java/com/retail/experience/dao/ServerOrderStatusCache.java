package com.retail.experience.dao;

import base.model.monitor.OrderStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Should load order status from repository
 * Database stores all the order details with user information
 */
public class ServerOrderStatusCache {

    private static final ConcurrentHashMap<String, List<String>> mapOrderStatus = new ConcurrentHashMap<>();

    private ServerOrderStatusCache() {
    }

    public static void addStatusMessage(String orderId, String message) {
        if (mapOrderStatus.containsKey(orderId)) {
            mapOrderStatus.get(orderId).add(message);
        } else {
            List<String> messages = new ArrayList<>();
            messages.add(message);
            mapOrderStatus.put(orderId, messages);
        }
    }

    public static void deleteOrderIfExist(String orderId) {
        mapOrderStatus.remove(orderId);
    }

    public static OrderStatus getOrderStatusById(String orderId) {
        if (mapOrderStatus.containsKey(orderId)) {
            return new OrderStatus(orderId, mapOrderStatus.get(orderId));
        }
        return new OrderStatus();
    }
}
