package com.retail.client.model.component;

public class GPU implements Component {

    private Long id;
    private String componentId;
    private String category;
    private String name;
    private String brand;
    private double price;
    private int quantity;
    private String productLine;
    private int numberOfCores;
    private String processorClockSpeed;
    private String graphicClockSpeed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    @Override
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public int getNumberOfCores() {
        return numberOfCores;
    }

    public void setNumberOfCores(int numberOfCores) {
        this.numberOfCores = numberOfCores;
    }

    public String getProcessorClockSpeed() {
        return processorClockSpeed;
    }

    public void setProcessorClockSpeed(String processorClockSpeed) {
        this.processorClockSpeed = processorClockSpeed;
    }

    public String getGraphicClockSpeed() {
        return graphicClockSpeed;
    }

    public void setGraphicClockSpeed(String graphicClockSpeed) {
        this.graphicClockSpeed = graphicClockSpeed;
    }

    @Override
    public String getDescription() {
        return numberOfCores + " cores, " + processorClockSpeed + ", " + graphicClockSpeed;
    }

    @Override
    public String toString() {
        return "GPU{" +
                "id=" + id +
                ", componentId='" + componentId + '\'' +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", productLine='" + productLine + '\'' +
                ", numberOfCores=" + numberOfCores +
                ", processorClockSpeed='" + processorClockSpeed + '\'' +
                ", graphicClockSpeed='" + graphicClockSpeed + '\'' +
                '}';
    }
}
