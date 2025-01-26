package com.retail.experience.model;

public interface Component {

    Long getId();

    String getComponentId();

    void setComponentId(String componentId);

    void setCategory(String category);

    void setName(String name);

    void setBrand(String brand);

    void setPrice(double price);

    int getQuantity();

    void setQuantity(int quantity);
}
