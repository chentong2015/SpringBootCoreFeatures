package com.core.model.response;

import base.model.response.OrderResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class OrderResponseTest {

    @Test
    void test_order_id() {
        OrderResponse response = new OrderResponse();
        response.setOrderId("orderId");
        Assertions.assertEquals("orderId", response.getOrderId());
    }

    @Test
    void test_order_id_null() {
        OrderResponse response = new OrderResponse();
        Assertions.assertNull(response.getOrderId());
    }

    @Test
    void test_order_status() {
        OrderResponse response = new OrderResponse();
        response.setOrderStatus(true);
        Assertions.assertTrue(response.getOrderStatus());
    }

    @Test
    void test_order_status_false() {
        OrderResponse response = new OrderResponse();
        Assertions.assertFalse(response.getOrderStatus());
    }

    @Test
    void test_messages() {
        OrderResponse response = new OrderResponse();
        List<String> message = response.getMessages();
        Assertions.assertEquals(0, message.size());
    }

    @Test
    void test_messages_with_parameter() {
        List<String> messages = new ArrayList<>();
        messages.add("message 1");
        messages.add("message 2");
        OrderResponse response = new OrderResponse("orderId", false, messages);
        List<String> result = response.getMessages();
        Assertions.assertEquals("message 1", result.get(0));
        Assertions.assertEquals("message 2", result.get(1));
    }

    @Test
    void test_messages_with_setter() {
        List<String> messages = new ArrayList<>();
        messages.add("message 1");
        messages.add("message 2");
        OrderResponse response = new OrderResponse();
        response.setMessages(messages);
        List<String> result = response.getMessages();
        Assertions.assertEquals("message 1", result.get(0));
        Assertions.assertEquals("message 2", result.get(1));
    }

    @Test
    void test_time_spent() {
        OrderResponse response = new OrderResponse();
        response.setTimeSpent(10);
        Assertions.assertEquals(10, response.getTimeSpent());
    }

    @Test
    void test_time_spent_zero() {
        OrderResponse response = new OrderResponse();
        Assertions.assertEquals(0, response.getTimeSpent());
    }

    @Test
    void test_to_string() {
        OrderResponse response = new OrderResponse();
        String result = response.toString();
        Assertions.assertTrue(result.contains("Order Response"));
    }
}