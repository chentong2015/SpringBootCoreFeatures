package com.retail.experience.service.controller;

import base.model.monitor.OrderStatus;
import base.model.request.OrderRequest;
import com.retail.experience.ServerApplication;
import com.retail.experience.config.ApplicationContextProvider;
import com.retail.experience.dao.ServerOrderStatusCache;
import com.retail.experience.exception.ApplicationProcessException;
import com.retail.experience.helper.OrderHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = ServerApplication.class)
public class OrderControllerServiceAsyncTest {

    @Autowired
    private OrderControllerService orderService;

    @Test
    void process_order_async() throws InterruptedException, ApplicationProcessException {
        OrderRequest orderRequest = OrderHelper.generateOrderRequestWithOneItem();
        orderService.processOrderAsync(orderRequest);
        ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor)
                ApplicationContextProvider.getBean("threadPoolTaskExecutor");
        if (!executor.getThreadPoolExecutor().awaitTermination(50, TimeUnit.SECONDS)) {
            executor.getThreadPoolExecutor().shutdown();
        }
        OrderStatus orderStatus = ServerOrderStatusCache.getOrderStatusById(orderRequest.getOrderId());
        Assertions.assertEquals(orderRequest.getOrderId(), orderStatus.getOrderId());
    }
}
