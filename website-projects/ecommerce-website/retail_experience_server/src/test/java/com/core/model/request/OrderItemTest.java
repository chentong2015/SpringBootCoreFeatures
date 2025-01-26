package com.core.model.request;

import base.model.request.OrderItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderItemTest {

    @Test
    void test_id() {
        OrderItem orderItem = new OrderItem();
        Assertions.assertEquals(0, orderItem.getId());
    }

    @Test
    void test_id_with_setter() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1);
        Assertions.assertEquals(1, orderItem.getId());
    }

    @Test
    void test_category() {
        OrderItem orderItem = new OrderItem();
        Assertions.assertNull(orderItem.getCategory());
    }

    @Test
    void test_category_with_setter() {
        OrderItem orderItem = new OrderItem();
        orderItem.setCategory("cpu");
        Assertions.assertEquals("cpu", orderItem.getCategory());
    }

    @Test
    void test_name() {
        OrderItem orderItem = new OrderItem();
        Assertions.assertNull(orderItem.getName());
    }

    @Test
    void test_name_with_setter() {
        OrderItem orderItem = new OrderItem();
        orderItem.setName("name");
        Assertions.assertEquals("name", orderItem.getName());
    }

    @Test
    void test_brand() {
        OrderItem orderItem = new OrderItem();
        Assertions.assertNull(orderItem.getBrand());
    }

    @Test
    void test_brand_with_setter() {
        OrderItem orderItem = new OrderItem();
        orderItem.setBrand("brand");
        Assertions.assertEquals("brand", orderItem.getBrand());
    }

    @Test
    void test_description() {
        OrderItem orderItem = new OrderItem();
        Assertions.assertNull(orderItem.getDescription());
    }

    @Test
    void test_description_with_setter() {
        OrderItem orderItem = new OrderItem();
        orderItem.setDescription("description");
        Assertions.assertEquals("description", orderItem.getDescription());
    }

    @Test
    void test_price() {
        OrderItem orderItem = new OrderItem();
        Assertions.assertEquals(0, orderItem.getPrice());
    }

    @Test
    void test_price_with_setter() {
        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(10);
        Assertions.assertEquals(10, orderItem.getPrice());
    }

    @Test
    void test_quantity() {
        OrderItem orderItem = new OrderItem();
        Assertions.assertEquals(0, orderItem.getQuantity());
    }

    @Test
    void test_quantity_with_setter() {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(10);
        Assertions.assertEquals(10, orderItem.getQuantity());
    }
}