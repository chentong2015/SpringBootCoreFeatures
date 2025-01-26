package com.retail.client.cache;

import base.model.request.OrderDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Should save this order details on the server side
 * Once restart the client, update these cache automatically
 */
public class LocalOrderDetailsCache {

    private static final ConcurrentHashMap<String, OrderDetail> orderDetailsMap = new ConcurrentHashMap<>();

    private LocalOrderDetailsCache() {
    }

    public static void addOrderDetail(OrderDetail orderDetail) {
        orderDetailsMap.put(orderDetail.getOrderId(), orderDetail);
    }

    public static void update(String orderId, List<String> messages) {
        if (orderDetailsMap.containsKey(orderId)) {
            List<String> statusList = orderDetailsMap.get(orderId).getStatusList();
            for (String message : messages) {
                if (!statusList.contains(message)) {
                    statusList.add(message);
                }
            }
        }
    }

    public static OrderDetail getOrderDetails(String orderId) {
        if (orderDetailsMap.containsKey(orderId)) {
            return orderDetailsMap.get(orderId);
        }
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setStatusList(new ArrayList<>());
        return orderDetail;
    }

    /**
     * If order status list contains the final message "Time Spent..."
     * There is no need to update the status anymore.
     */
    public static List<String> getOrderIdsNotCompleted() {
        List<String> orderIds = new ArrayList<>();
        for (Map.Entry<String, OrderDetail> entry : orderDetailsMap.entrySet()) {
            List<String> statusList = entry.getValue().getStatusList();
            if (statusList.size() > 1 && !statusList.get(1).contains("Failed:")) {
                String lastMessage = statusList.get(statusList.size() - 1);
                if (!lastMessage.contains("Time Spent")) {
                    orderIds.add(entry.getKey());
                }
            }
        }
        return orderIds;
    }
}
