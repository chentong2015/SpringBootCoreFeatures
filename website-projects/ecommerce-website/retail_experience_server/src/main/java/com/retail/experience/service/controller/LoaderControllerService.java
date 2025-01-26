package com.retail.experience.service.controller;

import com.retail.experience.exception.ApplicationProcessException;
import com.retail.experience.exception.ServiceMappingException;
import com.retail.experience.model.*;
import com.retail.experience.service.base.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoaderControllerService {

    private final CpuService cpuService;
    private final GpuService gpuService;
    private final KeyboardService keyboardService;
    private final MemoryService memoryService;
    private final MonitorService monitorService;
    private final MouseService mouseService;
    private final StorageService storageService;
    private final Logger logger = LogManager.getLogger(LoaderControllerService.class.getName());

    @Autowired
    public LoaderControllerService(CpuService cpuService, GpuService gpuService,
                                   KeyboardService keyboardService, MemoryService memoryService,
                                   MonitorService monitorService, MouseService mouseService,
                                   StorageService storageService) {
        this.cpuService = cpuService;
        this.gpuService = gpuService;
        this.keyboardService = keyboardService;
        this.memoryService = memoryService;
        this.monitorService = monitorService;
        this.mouseService = mouseService;
        this.storageService = storageService;
    }

    public boolean loadComponents(List<Component> components) throws ApplicationProcessException {
        try {
            for (Component component : components) {
                loadSingleComponent(component);
            }
            return true;
        } catch (ApplicationProcessException exception) {
            logger.info("Load component to database failed, need to check component type.");
            throw exception;
        }
    }

    public void loadSingleComponent(Component component) throws ApplicationProcessException {
        if (component instanceof CPU) {
            cpuService.loadComponent((CPU) component);
        } else if (component instanceof GPU) {
            gpuService.loadComponent((GPU) component);
        } else if (component instanceof Keyboard) {
            keyboardService.loadComponent((Keyboard) component);
        } else if (component instanceof Memory) {
            memoryService.loadComponent((Memory) component);
        } else if (component instanceof Monitor) {
            monitorService.loadComponent((Monitor) component);
        } else if (component instanceof Mouse) {
            mouseService.loadComponent((Mouse) component);
        } else if (component instanceof Storage) {
            storageService.loadComponent((Storage) component);
        } else {
            throw new ServiceMappingException("Can not load the component type:" + component);
        }
    }
}
