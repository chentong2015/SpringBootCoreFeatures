package base.model.response;

import java.util.ArrayList;
import java.util.List;

public class OrderResponse {

    private String orderId;
    private boolean orderStatus;
    private long timeSpent;
    private List<String> messages;

    public OrderResponse() {
        messages = new ArrayList<>();
    }

    public OrderResponse(String orderId, boolean orderStatus, List<String> messages) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.messages = messages;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public boolean getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent;
    }

    @Override
    public String toString() {
        return "** Order Response ** \n"
                + "AdminStatus: " + orderStatus + "\n"
                + "Time spent: " + timeSpent + "\n"
                + "Message details: " + messages;
    }
}
