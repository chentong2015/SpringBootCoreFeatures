package com.retail.experience.model;

public class MockComponent implements Component {

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public String getComponentId() {
        return null;
    }

    @Override
    public void setComponentId(String componentId) {
    }

    @Override
    public void setCategory(String category) {
    }

    @Override
    public void setName(String name) {
    }

    @Override
    public void setBrand(String brand) {
    }

    @Override
    public void setPrice(double price) {
    }

    @Override
    public int getQuantity() {
        return 0;
    }

    @Override
    public void setQuantity(int quantity) {
    }
}
