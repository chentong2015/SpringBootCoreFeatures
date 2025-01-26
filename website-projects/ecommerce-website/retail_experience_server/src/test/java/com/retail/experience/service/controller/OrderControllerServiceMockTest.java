package com.retail.experience.service.controller;

import base.model.request.OrderRequest;
import base.model.response.OrderResponse;
import com.retail.experience.exception.ApplicationProcessException;
import com.retail.experience.exception.RepositoryManipulationException;
import com.retail.experience.helper.OrderHelper;
import com.retail.experience.service.base.CpuService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderControllerServiceMockTest {

    @Autowired
    OrderControllerService mockOrderControllerService;

    @MockBean
    CpuService cpuService;

    @Test
    public void assemble_failed_with_reduce_stock_exception() throws ApplicationProcessException {
        OrderRequest order = OrderHelper.generateSameOrderRequest();
        Mockito.doThrow(new RepositoryManipulationException()).when(cpuService).reduceStockById(1L, 1);

        AtomicReference<OrderResponse> response = new AtomicReference<>();
        Assertions.assertDoesNotThrow(() -> response.set(mockOrderControllerService.assemble(order)));
        Assertions.assertFalse(response.get().getOrderStatus());
        Mockito.verify(cpuService).reduceStockById(1L, 1);
    }

    /**
     * Once reduce Item(2, "CPU", 1) failed, it will restore the previous one Item(1, "CPU", 1)
     * If these two steps cannot be completed, an exception will be reported
     */
    @Test
    public void assemble_failed_with_restore_stock_exception() throws RepositoryManipulationException {
        OrderRequest order = OrderHelper.generateOrderRequestWithTwoItems();
        Mockito.doThrow(new RepositoryManipulationException()).when(cpuService).reduceStockById(2L, 1);
        Mockito.doThrow(new RepositoryManipulationException()).when(cpuService).restoreStockById(1L, 1);

        Assertions.assertThrows(RepositoryManipulationException.class, () -> mockOrderControllerService.assemble(order));
        Mockito.verify(cpuService).reduceStockById(2L, 1);
        Mockito.verify(cpuService).restoreStockById(1L, 1);
    }
}
