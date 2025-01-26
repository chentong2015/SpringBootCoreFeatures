package base.model.monitor;

import java.util.ArrayList;
import java.util.List;

public class OrderStatus {

    private String orderId;
    private List<String> statusList;

    public OrderStatus() {
        statusList = new ArrayList<>();
    }

    public OrderStatus(String orderId, List<String> statusList) {
        this.orderId = orderId;
        this.statusList = statusList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }
}
