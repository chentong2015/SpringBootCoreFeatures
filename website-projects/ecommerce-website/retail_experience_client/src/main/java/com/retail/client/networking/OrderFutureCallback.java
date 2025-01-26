package com.retail.client.networking;

import base.model.response.OrderResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.client.cache.LocalOrderDetailsCache;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class OrderFutureCallback implements FutureCallback<HttpResponse> {

    private final String orderId;
    private static final Logger logger = LogManager.getLogger(OrderFutureCallback.class.getName());

    public OrderFutureCallback(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public void completed(final HttpResponse response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String responseJson = EntityUtils.toString(response.getEntity());
            OrderResponse responseOrder = objectMapper.readValue(responseJson, OrderResponse.class);
            LocalOrderDetailsCache.update(orderId, responseOrder.getMessages());
        } catch (IOException e) {
            logger.error("Request IOException: {}", e.getMessage());
            LocalOrderDetailsCache.update(orderId, List.of("Failed: Send request failed, server problem !"));
        }
    }

    @Override
    public void failed(final Exception ex) {
        String message = "Send Request Failed ! Please check server side problems.";
        logger.error(message, ex.getMessage());
        LocalOrderDetailsCache.update(orderId, List.of(message));
    }

    @Override
    public void cancelled() {
        String message = "Request has been cancelled !";
        logger.error(message);
        LocalOrderDetailsCache.update(orderId, List.of(message));
    }
}
