package com.core.model.monitor;

import base.model.monitor.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class OrderStatusTest {

    @Test
    void get_order_id_null() {
        OrderStatus orderStatus = new OrderStatus();
        Assertions.assertNull(orderStatus.getOrderId());
    }

    @Test
    void set_order_id_by_setter() {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId("order id");
        Assertions.assertEquals("order id", orderStatus.getOrderId());
    }

    @Test
    void set_order_id_by_constructor() {
        OrderStatus orderStatus = new OrderStatus("order id", null);
        Assertions.assertEquals("order id", orderStatus.getOrderId());
    }

    @Test
    void get_status_list_by_default() {
        OrderStatus orderStatus = new OrderStatus();
        Assertions.assertEquals(0, orderStatus.getStatusList().size());
    }

    @Test
    void get_status_list_by_constructor() {
        List<String> statusList = new ArrayList<>();
        statusList.add("status test");
        OrderStatus orderStatus = new OrderStatus("order id", statusList);
        Assertions.assertEquals("status test", orderStatus.getStatusList().get(0));
    }

    @Test
    void set_status_list() {
        List<String> statusList = new ArrayList<>();
        statusList.add("status test");
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setStatusList(statusList);
        Assertions.assertEquals("status test", orderStatus.getStatusList().get(0));
    }
}