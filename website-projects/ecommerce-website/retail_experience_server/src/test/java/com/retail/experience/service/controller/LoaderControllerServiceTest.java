package com.retail.experience.service.controller;

import com.retail.experience.ServerApplication;
import com.retail.experience.exception.ApplicationProcessException;
import com.retail.experience.exception.ServiceMappingException;
import com.retail.experience.helper.ModelHelper;
import com.retail.experience.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class)
class LoaderControllerServiceTest {

    @Autowired
    private LoaderControllerService loaderService;

    @Test
    void load_components() throws ApplicationProcessException {
        List<Component> components = new ArrayList<>();
        components.add(ModelHelper.getCpu());
        Assertions.assertTrue(loaderService.loadComponents(components));
    }

    @Test
    void load_components_with_wrong_category() {
        List<Component> components = new ArrayList<>();
        MockComponent otherComponent = new MockComponent();
        components.add(otherComponent);
        Assertions.assertThrows(ServiceMappingException.class, () -> loaderService.loadComponents(components));
    }

    @Test
    void load_component_cpu() {
        CPU cpu = ModelHelper.getCpu();
        Assertions.assertDoesNotThrow(() -> loaderService.loadSingleComponent(cpu));
    }

    @Test
    void load_component_gpu() {
        GPU gpu = ModelHelper.getGpu();
        Assertions.assertDoesNotThrow(() -> loaderService.loadSingleComponent(gpu));
    }

    @Test
    void load_component_keyboard() {
        Keyboard keyboard = ModelHelper.getKeyboard();
        Assertions.assertDoesNotThrow(() -> loaderService.loadSingleComponent(keyboard));
    }

    @Test
    void load_component_memory() {
        Memory memory = ModelHelper.getMemory();
        Assertions.assertDoesNotThrow(() -> loaderService.loadSingleComponent(memory));
    }

    @Test
    void load_component_monitor() {
        Monitor monitor = ModelHelper.getMonitor();
        Assertions.assertDoesNotThrow(() -> loaderService.loadSingleComponent(monitor));
    }

    @Test
    void load_component_mouse() {
        Mouse mouse = ModelHelper.getMouse();
        Assertions.assertDoesNotThrow(() -> loaderService.loadSingleComponent(mouse));
    }

    @Test
    void load_component_storage() {
        Storage storage = ModelHelper.getStorage();
        Assertions.assertDoesNotThrow(() -> loaderService.loadSingleComponent(storage));
    }
}