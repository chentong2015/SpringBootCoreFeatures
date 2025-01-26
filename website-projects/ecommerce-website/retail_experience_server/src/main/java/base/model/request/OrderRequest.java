package base.model.request;

import java.util.ArrayList;
import java.util.List;

public class OrderRequest {

    private String orderId;
    private List<Item> itemList;

    public OrderRequest() {
        itemList = new ArrayList<>();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public boolean hasCompletedAllItems() {
        if (itemList.isEmpty()) return false;
        for (Item item : itemList) {
            if (!item.isHasCompleted()) {
                return false;
            }
        }
        return true;
    }

    public List<String> recordItemsStatus() {
        List<String> messages = new ArrayList<>();
        for (Item item : itemList) {
            messages.add(item.toString());
        }
        return messages;
    }
}
