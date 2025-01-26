package com.retail.client.model.order;

public class OrderInput {

    private final String orderId;
    private final String username;
    private final String address;
    private String[] categories;
    private String[] ids;
    private String[] quantities;

    public OrderInput(String orderId, String username, String address) {
        this.orderId = orderId;
        this.username = username;
        this.address = address;
    }

    /**
     * @param checkboxInput: cpu_4,gpu_84,keyboard_63,memory_90
     */
    public void setCategoriesAndIds(String checkboxInput) {
        String[] array = checkboxInput.split(",");
        categories = new String[array.length];
        ids = new String[array.length];
        for (int index = 0; index < array.length; index++) {
            categories[index] = array[index].split("_")[0];
            ids[index] = array[index].split("_")[1];
        }
    }

    /**
     * @param selectInput: 2,3,1,5
     */
    public void setQuantities(String selectInput) {
        quantities = selectInput.split(",");
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUsername() {
        return username;
    }

    public String getAddress() {
        return address;
    }

    public String[] getCategories() {
        return categories;
    }

    public String[] getIds() {
        return ids;
    }

    public String[] getQuantities() {
        return quantities;
    }
}
