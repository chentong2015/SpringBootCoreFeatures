package com.retail.client.monitor;

import java.util.Timer;
import java.util.TimerTask;

public class BackgroundThread implements Runnable {

    @Override
    public void run() {
        TimerTask task = new MonitorStatusTask();
        Timer timer = new Timer("Order Status Monitor");
        timer.schedule(task, 1000L, 1000L);
    }
}
