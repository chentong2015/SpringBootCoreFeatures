package com.retail.experience.helper;

import base.model.request.Item;
import base.model.request.OrderRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class OrderHelper {

    private OrderHelper() {
    }

    public static OrderRequest generateOrderRequestWithOneItem() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderId("orderId");
        Item item = new Item(1, "cpu", 1, false);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        orderRequest.setItemList(itemList);
        return orderRequest;
    }

    public static OrderRequest generateOrderRequestWithTwoItems() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderId("orderId");
        Item item1 = new Item(1, "cpu", 1, false);
        Item item2 = new Item(2, "cpu", 1, false);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item2);
        orderRequest.setItemList(itemList);
        return orderRequest;
    }

    public static OrderRequest generateOrderRequestCompleted() {
        OrderRequest orderRequest = new OrderRequest();
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(1, "cpu", 1, true));
        itemList.add(new Item(1, "gpu", 1, true));
        orderRequest.setOrderId("order id");
        orderRequest.setItemList(itemList);
        return orderRequest;
    }

    public static OrderRequest generateSameOrderRequest() {
        OrderRequest order = new OrderRequest();
        order.setOrderId(UUID.randomUUID().toString());
        order.getItemList().add(new Item(1, "CPU", 1));
        order.getItemList().add(new Item(84, "GPU", 1));
        order.getItemList().add(new Item(51, "Keyboard", 1));
        order.getItemList().add(new Item(90, "Memory", 1));
        order.getItemList().add(new Item(40, "Monitor", 1));
        order.getItemList().add(new Item(70, "Mouse", 1));
        order.getItemList().add(new Item(99, "Storage", 1));
        return order;
    }

    public static OrderRequest generateRandomOrderRequest() {
        Random random = new Random();
        OrderRequest order = new OrderRequest();
        order.setOrderId(UUID.randomUUID().toString());
        order.getItemList().add(new Item(1, "CPU", random.nextInt(5)));
        order.getItemList().add(new Item(83, "GPU", random.nextInt(5)));
        order.getItemList().add(new Item(50, "Keyboard", random.nextInt(5)));
        order.getItemList().add(new Item(88, "Memory", random.nextInt(5)));
        order.getItemList().add(new Item(39, "Monitor", random.nextInt(5)));
        order.getItemList().add(new Item(69, "Mouse", random.nextInt(5)));
        order.getItemList().add(new Item(97, "Storage", random.nextInt(5)));
        return order;
    }
}
