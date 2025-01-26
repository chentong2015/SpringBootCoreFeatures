package com.core.model.request;

import base.model.request.OrderRequest;
import com.retail.experience.helper.OrderHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class OrderRequestTest {

    @Test
    void record_items_status_null() {
        OrderRequest orderRequest = new OrderRequest();
        List<String> itemStatus = orderRequest.recordItemsStatus();
        Assertions.assertEquals(0, itemStatus.size());
    }

    @Test
    void record_items_status() {
        OrderRequest orderRequest = OrderHelper.generateOrderRequestCompleted();
        List<String> itemStatus = orderRequest.recordItemsStatus();
        Assertions.assertEquals(2, itemStatus.size());
    }

    @Test
    void has_completed_all_items_true() {
        OrderRequest orderRequest = OrderHelper.generateOrderRequestCompleted();
        Assertions.assertTrue(orderRequest.hasCompletedAllItems());
    }

    @Test
    void has_completed_all_items_false_without_items() {
        OrderRequest orderRequest = new OrderRequest();
        Assertions.assertFalse(orderRequest.hasCompletedAllItems());
    }

    @Test
    void has_completed_all_items_false_with_items() {
        OrderRequest orderRequest = OrderHelper.generateOrderRequestWithOneItem();
        Assertions.assertFalse(orderRequest.hasCompletedAllItems());
    }
}