package com.retail.experience.factory;

import com.retail.experience.model.*;

import java.util.Map;

public class ComputerComponentFactory implements ComponentFactory {

    private static final String ID = "ID";
    private static final String CATEGORY = "Category";
    private static final String NAME = "Name";
    private static final String BRAND = "Brand";
    private static final String PRICE = "Price";
    private static final String QUANTITY = "Quantity";
    private static final String PRODUCT_LINE = "Product Line";
    private static final String NUMBER_OF_CORES = "Number Of Cores";
    private static final String PROCESSOR_CLOCK_SPEED = "Processor Clock Speed";
    private static final String GRAPHIC_CLOCK_SPEED = "Graphic Clock Speed";
    private static final String DIMENSION = "Dimension";
    private static final String INTERFACE = "Interface";
    private static final String RESOLUTION = "Resolution";
    private static final String COLOR = "Color";
    private static final String SIZE = "Size";

    @Override
    public Component makeComponent(Map<String, String> items) {
        String category = items.get(CATEGORY);
        switch (category) {
            case "CPU":
                return makeCPU(items);
            case "Monitor":
                return makeMonitor(items);
            case "Keyboard":
                return makeKeyboard(items);
            case "Mouse":
                return makeMouse(items);
            case "GPU":
                return makeGpu(items);
            case "Memory":
                return makeMemory(items);
            case "Storage":
                return makeStorage(items);
            default:
                return null;
        }
    }

    private CPU makeCPU(Map<String, String> items) {
        CPU cpu = new CPU();
        setCommonFields(cpu, items);
        cpu.setProductLine(items.get(PRODUCT_LINE));
        cpu.setNumberOfCores(Integer.parseInt(items.get(NUMBER_OF_CORES)));
        cpu.setProcessorClockSpeed(items.get(PROCESSOR_CLOCK_SPEED));
        cpu.setGraphicClockSpeed(items.get(GRAPHIC_CLOCK_SPEED));
        return cpu;
    }

    private <T extends Component> void setCommonFields(T component, Map<String, String> items) {
        component.setComponentId(items.get(ID));
        component.setCategory(items.get(CATEGORY));
        component.setName(items.get(NAME));
        component.setBrand(items.get(BRAND));
        component.setPrice(Double.parseDouble(items.get(PRICE)));
        component.setQuantity(Integer.parseInt(items.get(QUANTITY)));
    }

    private GPU makeGpu(Map<String, String> items) {
        GPU gpu = new GPU();
        setCommonFields(gpu, items);
        gpu.setProductLine(items.get(PRODUCT_LINE));
        gpu.setNumberOfCores(Integer.parseInt(items.get(NUMBER_OF_CORES)));
        gpu.setProcessorClockSpeed(items.get(PROCESSOR_CLOCK_SPEED));
        gpu.setGraphicClockSpeed(items.get(GRAPHIC_CLOCK_SPEED));
        return gpu;
    }

    private Keyboard makeKeyboard(Map<String, String> items) {
        Keyboard keyboard = new Keyboard();
        setCommonFields(keyboard, items);
        keyboard.setDimension(items.get(DIMENSION));
        keyboard.setColor(items.get(COLOR));
        return keyboard;
    }

    private Mouse makeMouse(Map<String, String> items) {
        Mouse mouse = new Mouse();
        setCommonFields(mouse, items);
        mouse.setDimension(items.get(DIMENSION));
        mouse.setColor(items.get(COLOR));
        return mouse;
    }

    private Memory makeMemory(Map<String, String> items) {
        Memory memory = new Memory();
        setCommonFields(memory, items);
        memory.setInterfaze(items.get(INTERFACE));
        memory.setSize(items.get(SIZE));
        return memory;
    }

    private Monitor makeMonitor(Map<String, String> items) {
        Monitor monitor = new Monitor();
        setCommonFields(monitor, items);
        monitor.setDimension(items.get(DIMENSION));
        monitor.setResolution(items.get(RESOLUTION));
        monitor.setColor(items.get(COLOR));
        return monitor;
    }

    private Storage makeStorage(Map<String, String> items) {
        Storage storage = new Storage();
        setCommonFields(storage, items);
        storage.setDimension(items.get(DIMENSION));
        storage.setInterfaze(items.get(INTERFACE));
        storage.setSize(items.get(SIZE));
        return storage;
    }
}
