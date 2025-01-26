package com.retail.experience.factory;

import com.retail.experience.model.Component;
import com.retail.experience.model.Memory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class ComputerComponentFactoryTest {

    @Test
    void make_component() {
        Map<String, String> map = new HashMap<>();
        map.put("ID", "9ed8ff99-d494-4e79-98a7-5d96e61082a8-test");
        map.put("Category", "Memory");
        map.put("Name", "HyperX Fury Test");
        map.put("Brand", "Team");
        map.put("Price", "100.0");
        map.put("Quantity", "10");
        map.put("Interface", "DDR3");
        map.put("Size", "18GB");
        ComponentFactory factory = new ComputerComponentFactory();
        Component component = factory.makeComponent(map);
        Assertions.assertTrue(component instanceof Memory);
    }

    @Test
    void make_component_failed() {
        Map<String, String> map = new HashMap<>();
        map.put("ID", "XXX");
        map.put("Category", "XXX");
        ComponentFactory factory = new ComputerComponentFactory();
        Component component = factory.makeComponent(map);
        Assertions.assertNull(component);
    }
}