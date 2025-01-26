package com.retail.experience.monitor;

import base.model.monitor.AdminStatus;
import com.retail.experience.util.DoubleFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Should test ServerMonitorSystem with multi-threads
 */
class ServerMonitorSystemTest {

    @Test
    void increase_number_of_connected_clients() {
        int number1 = ServerMonitorSystem.getNumberOfConnectedClients();
        int number2 = ServerMonitorSystem.increaseNumberOfConnectedClients();
        Assertions.assertEquals(number1 + 1, number2);
    }

    @Test
    void decrease_number_of_connected_clients() {
        int number1 = ServerMonitorSystem.getNumberOfConnectedClients();
        int number2 = ServerMonitorSystem.decreaseNumberOfConnectedClients();
        Assertions.assertEquals(number1 - 1, number2);
    }

    @Test
    void increase_number_of_Orders_waiting() {
        int number1 = ServerMonitorSystem.getNumberOfOrdersWaiting();
        int number2 = ServerMonitorSystem.increaseNumberOfOrdersWaiting();
        Assertions.assertEquals(number1 + 1, number2);
    }

    @Test
    void decrease_number_of_orders_waiting() {
        int number1 = ServerMonitorSystem.getNumberOfOrdersWaiting();
        int number2 = ServerMonitorSystem.decreaseNumberOfOrdersWaiting();
        Assertions.assertEquals(number1 - 1, number2);
    }

    @Test
    void increase_number_of_orders_processing() {
        int number1 = ServerMonitorSystem.getNumberOfOrdersProcessing();
        int number2 = ServerMonitorSystem.increaseNumberOfOrdersProcessing();
        Assertions.assertEquals(number1 + 1, number2);
    }

    @Test
    void decrease_number_of_orders_processing() {
        int number1 = ServerMonitorSystem.getNumberOfOrdersProcessing();
        int number2 = ServerMonitorSystem.decreaseNumberOfOrdersProcessing();
        Assertions.assertEquals(number1 - 1, number2);
    }

    @Test
    void increaseNumberOfOrdersDone() {
        int number1 = ServerMonitorSystem.getNumberOfOrdersDone();
        int number2 = ServerMonitorSystem.increaseNumberOfOrdersDone();
        Assertions.assertEquals(number1 + 1, number2);
    }

    @Test
    void decrease_number_of_orders_done() {
        int number1 = ServerMonitorSystem.getNumberOfOrdersDone();
        int number2 = ServerMonitorSystem.decreaseNumberOfOrdersDone();
        Assertions.assertEquals(number1 - 1, number2);
    }

    @Test
    void get_current_memory_usage() {
        String status = ServerMonitorSystem.getCurrentMemoryUsage();
        String expectedStatus = getMemoryUsage();
        Assertions.assertEquals(expectedStatus, status);
    }

    private String getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long usedMemory = totalMemory - runtime.freeMemory();
        double percent = (double) usedMemory / totalMemory * 100;
        return DoubleFormatter.format(percent) + "%";
    }

    @Test
    void report_status() {
        AdminStatus adminStatus = ServerMonitorSystem.reportStatus();
        Assertions.assertNotNull(adminStatus);
    }
}