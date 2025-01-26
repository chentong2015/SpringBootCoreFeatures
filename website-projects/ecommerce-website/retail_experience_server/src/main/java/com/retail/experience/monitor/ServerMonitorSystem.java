package com.retail.experience.monitor;

import base.model.monitor.AdminStatus;
import com.retail.experience.util.DoubleFormatter;

import java.util.concurrent.atomic.AtomicInteger;

public class ServerMonitorSystem {

    private static final AtomicInteger numberOfConnectedClients = new AtomicInteger();
    private static final AtomicInteger numberOfOrdersWaiting = new AtomicInteger();
    private static final AtomicInteger numberOfOrdersProcessing = new AtomicInteger();
    private static final AtomicInteger numberOfOrdersDone = new AtomicInteger();

    private ServerMonitorSystem() {
    }

    public static int getNumberOfConnectedClients() {
        return numberOfConnectedClients.get();
    }

    public static int increaseNumberOfConnectedClients() {
        return numberOfConnectedClients.incrementAndGet();
    }

    public static int decreaseNumberOfConnectedClients() {
        return numberOfConnectedClients.decrementAndGet();
    }

    public static int getNumberOfOrdersWaiting() {
        return numberOfOrdersWaiting.get();
    }

    public static int increaseNumberOfOrdersWaiting() {
        return numberOfOrdersWaiting.incrementAndGet();
    }

    public static int decreaseNumberOfOrdersWaiting() {
        return numberOfOrdersWaiting.decrementAndGet();
    }

    public static int getNumberOfOrdersProcessing() {
        return numberOfOrdersProcessing.get();
    }

    public static int increaseNumberOfOrdersProcessing() {
        return numberOfOrdersProcessing.incrementAndGet();
    }

    public static int decreaseNumberOfOrdersProcessing() {
        return numberOfOrdersProcessing.decrementAndGet();
    }

    public static int getNumberOfOrdersDone() {
        return numberOfOrdersDone.get();
    }

    public static int increaseNumberOfOrdersDone() {
        return numberOfOrdersDone.incrementAndGet();
    }

    public static int decreaseNumberOfOrdersDone() {
        return numberOfOrdersDone.decrementAndGet();
    }

    public static AdminStatus reportStatus() {
        AdminStatus adminStatus = new AdminStatus();
        adminStatus.setNumberOfConnectedClients(getNumberOfConnectedClients());
        adminStatus.setNumberOfOrdersWaiting(getNumberOfOrdersWaiting());
        adminStatus.setNumberOfOrdersProcessing(getNumberOfOrdersProcessing());
        adminStatus.setNumberOfOrdersDone(getNumberOfOrdersDone());
        adminStatus.setMemoryUsage(getCurrentMemoryUsage());
        return adminStatus;
    }

    public static String getCurrentMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long usedMemory = totalMemory - runtime.freeMemory();
        double percent = (double) usedMemory / totalMemory * 100;
        return DoubleFormatter.format(percent) + "%";
    }
}
