package com.retail.client.cache;

import base.model.request.OrderItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.client.model.component.*;
import com.retail.client.model.order.OrderInput;
import com.retail.client.networking.ApacheHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Should keep this LocalComponentCache class safely
 */
public class LocalComponentCache {

    private List<CPU> cpuList;
    private List<GPU> gpuList;
    private List<Keyboard> keyboardList;
    private List<Memory> memoryList;
    private List<Monitor> monitorList;
    private List<Mouse> mouseList;
    private List<Storage> storageList;

    public List<CPU> getCpuList() {
        return cpuList;
    }

    private CPU getCpuById(int id) {
        for (CPU cpu : cpuList) {
            if (cpu.getId() == id) return cpu;
        }
        return null;
    }

    public List<GPU> getGpuList() {
        return gpuList;
    }

    private GPU getGpuById(int id) {
        for (GPU gpu : gpuList) {
            if (gpu.getId() == id) return gpu;
        }
        return null;
    }

    public List<Keyboard> getKeyboardList() {
        return keyboardList;
    }

    private Keyboard getKeyboardById(int id) {
        for (Keyboard keyboard : keyboardList) {
            if (keyboard.getId() == id) return keyboard;
        }
        return null;
    }

    public List<Memory> getMemoryList() {
        return memoryList;
    }

    private Memory getMemoryById(int id) {
        for (Memory memory : memoryList) {
            if (memory.getId() == id) return memory;
        }
        return null;
    }

    public List<Monitor> getMonitorList() {
        return monitorList;
    }

    private Monitor getMonitorById(int id) {
        for (Monitor monitor : monitorList) {
            if (monitor.getId() == id) return monitor;
        }
        return null;
    }

    public List<Mouse> getMouseList() {
        return mouseList;
    }

    private Mouse getMouseById(int id) {
        for (Mouse mouse : mouseList) {
            if (mouse.getId() == id) return mouse;
        }
        return null;
    }

    public List<Storage> getStorageList() {
        return storageList;
    }

    private Storage getStorageById(int id) {
        for (Storage storage : storageList) {
            if (storage.getId() == id) return storage;
        }
        return null;
    }

    public synchronized void loadComponentsToCache() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String cpuComponents = ApacheHttpClient.listComponents("cpu");
        CPU[] cpus = objectMapper.readValue(cpuComponents, CPU[].class);
        cpuList = List.of(cpus);

        String gpuComponents = ApacheHttpClient.listComponents("gpu");
        GPU[] gpus = objectMapper.readValue(gpuComponents, GPU[].class);
        gpuList = List.of(gpus);

        String keyboardComponents = ApacheHttpClient.listComponents("keyboard");
        Keyboard[] keyboards = objectMapper.readValue(keyboardComponents, Keyboard[].class);
        keyboardList = List.of(keyboards);

        String memoryComponents = ApacheHttpClient.listComponents("memory");
        Memory[] memories = objectMapper.readValue(memoryComponents, Memory[].class);
        memoryList = List.of(memories);

        String monitorComponents = ApacheHttpClient.listComponents("monitor");
        Monitor[] monitors = objectMapper.readValue(monitorComponents, Monitor[].class);
        monitorList = List.of(monitors);

        String mouseComponents = ApacheHttpClient.listComponents("mouse");
        Mouse[] mouses = objectMapper.readValue(mouseComponents, Mouse[].class);
        mouseList = List.of(mouses);

        String storageComponents = ApacheHttpClient.listComponents("storage");
        Storage[] storages = objectMapper.readValue(storageComponents, Storage[].class);
        storageList = List.of(storages);
    }

    public synchronized List<OrderItem> getOrderItemsList(OrderInput orderInput) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (int index = 0; index < orderInput.getCategories().length; index++) {
            String category = orderInput.getCategories()[index];
            int id = Integer.parseInt(orderInput.getIds()[index]);
            String quantity = orderInput.getQuantities()[index];

            Component component = getComponentByCategoryAndId(category, id);
            if (component == null) continue;

            OrderItem item = new OrderItem();
            item.setId(index + 1);
            item.setCategory(component.getCategory());
            item.setName(component.getName());
            item.setBrand(component.getBrand());
            item.setDescription(component.getDescription());
            item.setPrice(component.getPrice());
            item.setQuantity(Integer.parseInt(quantity));
            orderItemList.add(item);
        }
        return orderItemList;
    }

    private Component getComponentByCategoryAndId(String category, int id) {
        switch (category) {
            case "cpu":
                return getCpuById(id);
            case "gpu":
                return getGpuById(id);
            case "monitor":
                return getMonitorById(id);
            case "keyboard":
                return getKeyboardById(id);
            case "mouse":
                return getMouseById(id);
            case "memory":
                return getMemoryById(id);
            case "storage":
                return getStorageById(id);
            default:
                return null;
        }
    }
}
