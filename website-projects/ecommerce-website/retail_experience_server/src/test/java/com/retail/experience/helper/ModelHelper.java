package com.retail.experience.helper;

import com.retail.experience.model.*;

public class ModelHelper {

    private ModelHelper() {
    }

    public static CPU getCpu() {
        CPU cpu = new CPU();
        cpu.setComponentId("70d0c37e-634e-4a68-8862-0ba44f216f3b");
        cpu.setCategory("CPU");
        cpu.setName("Intel Core i7-8809G");
        cpu.setBrand("Intel");
        cpu.setProductLine("Core i7");
        cpu.setNumberOfCores(4);
        cpu.setProcessorClockSpeed("3.10 GHz");
        cpu.setGraphicClockSpeed("1.20 GHz");
        cpu.setPrice(150);
        cpu.setQuantity(25);
        return cpu;
    }

    public static GPU getGpu() {
        GPU gpu = new GPU();
        gpu.setComponentId("2147f9c7-09ab-4df2-8e66-96e71e6c9a3d");
        gpu.setCategory("GPU");
        gpu.setName("GeForce G100");
        gpu.setBrand("NVIDIA");
        gpu.setProductLine("GeForce G100");
        gpu.setNumberOfCores(4);
        gpu.setProcessorClockSpeed("567 MHZ");
        gpu.setGraphicClockSpeed("1400 MHZ");
        gpu.setPrice(250);
        gpu.setQuantity(15);
        return gpu;
    }

    public static Keyboard getKeyboard() {
        Keyboard keyboard = new Keyboard();
        keyboard.setComponentId("45568a36-66e0-4c49-93dc-e061543d2f43");
        keyboard.setCategory("Keyboard");
        keyboard.setName("Microsoft Arc");
        keyboard.setBrand("Microsoft");
        keyboard.setDimension("2.6W x 6.5L x 6.5D");
        keyboard.setColor("White");
        keyboard.setPrice(50);
        keyboard.setQuantity(10);
        return keyboard;
    }

    public static Memory getMemory() {
        Memory memory = new Memory();
        memory.setComponentId("710c3100-4a24-4f9a-947a-710948dea837");
        memory.setCategory("Memory");
        memory.setName("Kingston HyperX Impact DDR4");
        memory.setBrand("Kingston");
        memory.setInterfaze("DDR4");
        memory.setSize("8GB");
        memory.setPrice(95);
        memory.setQuantity(10);
        return memory;
    }

    public static Monitor getMonitor() {
        Monitor monitor = new Monitor();
        monitor.setComponentId("97222f34-2e84-48a3-a165-97962cdc8c95");
        monitor.setCategory("Monitor");
        monitor.setName("Asus VP229HA");
        monitor.setBrand("Asus");
        monitor.setDimension("31.5");
        monitor.setResolution("1920 x 1080");
        monitor.setColor("Black");
        monitor.setPrice(130);
        monitor.setQuantity(20);
        return monitor;
    }

    public static Mouse getMouse() {
        Mouse mouse = new Mouse();
        mouse.setComponentId("7503ee11-1911-4cb1-89e5-e9c0e04e05ac");
        mouse.setCategory("Mouse");
        mouse.setName("Basic Optical Mouse");
        mouse.setBrand("Microsoft");
        mouse.setDimension("2.2W  x 1.5L x 4.4D");
        mouse.setColor("White");
        mouse.setPrice(25);
        mouse.setQuantity(50);
        return mouse;
    }

    public static Storage getStorage() {
        Storage storage = new Storage();
        storage.setComponentId("7149a2bf-b80a-42d8-beb3-c5ad9edca259");
        storage.setCategory("Storage");
        storage.setName("SAMSUNG 860 EVO");
        storage.setBrand("Samsung");
        storage.setDimension("2.5");
        storage.setInterfaze("SSD");
        storage.setSize("1TB");
        storage.setPrice(289);
        storage.setQuantity(10);
        return storage;
    }
}
