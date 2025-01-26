package com.retail.experience.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity(name = "t_cpu")
public class CPU implements Component {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String componentId;

    @NotNull
    private String category;

    @NotNull
    private String name;

    private String brand;

    private double price;

    private int quantity;

    private String productLine;

    private int numberOfCores;

    private String processorClockSpeed;

    private String graphicClockSpeed;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getComponentId() {
        return componentId;
    }

    @Override
    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
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
}
