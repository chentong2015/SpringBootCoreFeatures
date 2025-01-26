package com.retail.client.monitor;

import base.model.monitor.OrderStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.client.cache.LocalOrderDetailsCache;
import com.retail.client.networking.ApacheHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

public class MonitorStatusTask extends TimerTask {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LogManager.getLogger(MonitorStatusTask.class.getName());

    @Override
    public void run() {
        try {
            List<String> orderIds = LocalOrderDetailsCache.getOrderIdsNotCompleted();
            for (String orderId : orderIds) {
                String orderStatusJson = ApacheHttpClient.getOrderStatus(orderId);
                OrderStatus orderStatus = objectMapper.readValue(orderStatusJson, OrderStatus.class);
                LocalOrderDetailsCache.update(orderId, orderStatus.getStatusList());
            }
        } catch (IOException e) {
            logger.error("Monitor Status Task failed: {}", e.getMessage());
        }
    }
}
