package com.retail.experience.dao;

import base.model.monitor.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Should test ServerOrderStatusCache with multi-threads
 */
class ServerOrderStatusCacheTest {

    private final String orderId = "orderId";
    private final String message = "test message";

    @Test
    void add_status_message_with_new_key() {
        ServerOrderStatusCache.deleteOrderIfExist(orderId);
        ServerOrderStatusCache.addStatusMessage(orderId, message);
        OrderStatus orderStatus = ServerOrderStatusCache.getOrderStatusById(orderId);
        Assertions.assertEquals(message, orderStatus.getStatusList().get(0));
    }

    @Test
    void add_status_message_with_existing_key() {
        ServerOrderStatusCache.deleteOrderIfExist(orderId);
        ServerOrderStatusCache.addStatusMessage(orderId, message);
        ServerOrderStatusCache.addStatusMessage(orderId, message);
        OrderStatus orderStatus = ServerOrderStatusCache.getOrderStatusById(orderId);
        Assertions.assertEquals(message, orderStatus.getStatusList().get(0));
        Assertions.assertEquals(message, orderStatus.getStatusList().get(1));
    }

    @Test
    void delete_order_if_existing() {
        ServerOrderStatusCache.addStatusMessage(orderId, message);
        ServerOrderStatusCache.deleteOrderIfExist(orderId);
        Assertions.assertNull(ServerOrderStatusCache.getOrderStatusById(orderId).getOrderId());
    }

    @Test
    void get_order_status_by_id_existing() {
        ServerOrderStatusCache.deleteOrderIfExist(orderId);
        ServerOrderStatusCache.addStatusMessage(orderId, message);
        OrderStatus orderStatus = ServerOrderStatusCache.getOrderStatusById(orderId);
        Assertions.assertEquals(message, orderStatus.getStatusList().get(0));
    }

    @Test
    void get_order_status_by_id_not_existing() {
        String orderIdNotExisting = "test order id";
        OrderStatus orderStatus = ServerOrderStatusCache.getOrderStatusById(orderIdNotExisting);
        Assertions.assertNull(orderStatus.getOrderId());
    }
}