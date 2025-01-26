package com.retail.client.networking;

import base.model.request.OrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ApacheHttpClient {

    private static final String CONTENT_TYPE = "application/json";
    private static final CloseableHttpAsyncClient httpAsyncClient;
    private static final String URL_LIST_COMPONENTS = "http://localhost:8080/order/list/";
    private static final String URL_ORDERS = "http://localhost:8080/order/computer";
    private static final String URL_MONITOR_ORDER_STATUS = "http://localhost:8080/monitor/order/status/";

    private ApacheHttpClient() {
    }

    static {
        httpAsyncClient = HttpAsyncClients.createDefault();
        httpAsyncClient.start();
    }

    public static String listComponents(String componentName) throws IOException {
        String listUrl = URL_LIST_COMPONENTS + componentName;
        HttpGet request = new HttpGet(listUrl);
        request.setHeader("Content-Type", CONTENT_TYPE);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        }
    }

    public static String sendOrderSync(OrderRequest order) throws IOException {
        HttpPost post = createHttpPost(order);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
    }

    public static void sendOrderAsync(OrderRequest order) throws IOException {
        HttpPost post = createHttpPost(order);
        httpAsyncClient.execute(post, new OrderFutureCallback(order.getOrderId()));
    }

    private static HttpPost createHttpPost(OrderRequest order)
            throws JsonProcessingException, UnsupportedEncodingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonOrder = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(order);
        HttpPost post = new HttpPost(URL_ORDERS);
        post.addHeader("content-type", CONTENT_TYPE);
        post.setEntity(new StringEntity(jsonOrder));
        return post;
    }

    public static String getOrderStatus(String orderId) throws IOException {
        HttpGet request = new HttpGet(URL_MONITOR_ORDER_STATUS + orderId);
        request.setHeader("Content-Type", CONTENT_TYPE);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        }
    }
}
