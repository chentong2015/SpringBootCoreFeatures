package com.retail.client.model.order;

import java.util.ArrayList;
import java.util.List;

public class OrderStatusProgress {

    private final int percentage;
    private final List<String> statusList;

    public OrderStatusProgress(int percentage) {
        this.percentage = percentage;
        statusList = new ArrayList<>();
    }

    public OrderStatusProgress(int percentage, List<String> statusList) {
        this.percentage = percentage;
        this.statusList = statusList;
    }

    public int getPercentage() {
        return percentage;
    }

    public List<String> getStatusList() {
        return statusList;
    }
}
