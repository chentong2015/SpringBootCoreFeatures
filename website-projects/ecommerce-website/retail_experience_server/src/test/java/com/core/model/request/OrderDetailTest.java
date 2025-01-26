package com.core.model.request;

import base.model.request.OrderDetail;
import base.model.request.OrderItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class OrderDetailTest {

    @Test
    void test_order_id() {
        OrderDetail orderDetail = new OrderDetail();
        Assertions.assertNull(orderDetail.getOrderId());
    }

    @Test
    void test_order_id_with_setter() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId("orderId");
        Assertions.assertEquals("orderId", orderDetail.getOrderId());
    }

    @Test
    void test_username() {
        OrderDetail orderDetail = new OrderDetail();
        Assertions.assertNull(orderDetail.getUsername());
    }

    @Test
    void test_username_with_setter() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setUsername("username");
        Assertions.assertEquals("username", orderDetail.getUsername());
    }

    @Test
    void test_address() {
        OrderDetail orderDetail = new OrderDetail();
        Assertions.assertNull(orderDetail.getAddress());
    }

    @Test
    void test_address_with_setter() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setAddress("address");
        Assertions.assertEquals("address", orderDetail.getAddress());
    }

    @Test
    void test_delivery_time() {
        OrderDetail orderDetail = new OrderDetail();
        Assertions.assertNull(orderDetail.getDeliveryTime());
    }

    @Test
    void test_delivery_time_with_setter() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDeliveryTime("2022-03-01");
        Assertions.assertEquals("2022-03-01", orderDetail.getDeliveryTime());
    }

    @Test
    void get_total_price_by_default() {
        OrderDetail orderDetail = new OrderDetail();
        Assertions.assertEquals(0, orderDetail.getTotalPrice());
    }

    @Test
    void get_total_price() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderItems(generateBaseOrderItemList());
        List<OrderItem> orderItemList = orderDetail.getOrderItems();
        Assertions.assertEquals(10, orderItemList.get(0).getPrice());
    }

    private List<OrderItem> generateBaseOrderItemList() {
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem item = new OrderItem();
        item.setId(1);
        item.setPrice(10);
        item.setQuantity(1);
        orderItemList.add(item);
        return orderItemList;
    }

    @Test
    void test_order_items() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderItems(generateBaseOrderItemList());
        List<OrderItem> orderItemList = orderDetail.getOrderItems();
        Assertions.assertEquals(1, orderItemList.get(0).getId());
    }

    @Test
    void test_status_list_by_default() {
        OrderDetail orderDetail = new OrderDetail();
        Assertions.assertNull(orderDetail.getStatusList());
    }

    @Test
    void test_status_list_with_setter() {
        OrderDetail orderDetail = new OrderDetail();
        List<String> statusList = new ArrayList<>();
        statusList.add("Status 01");
        orderDetail.setStatusList(statusList);
        Assertions.assertEquals("Status 01", orderDetail.getStatusList().get(0));
    }
}