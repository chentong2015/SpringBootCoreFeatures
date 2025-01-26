package com.retail.experience.service.base;

import com.retail.experience.ServerApplication;
import com.retail.experience.exception.RepositoryManipulationException;
import com.retail.experience.helper.ModelHelper;
import com.retail.experience.model.CPU;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class)
class BaseComponentServiceTest {

    @Autowired
    private CpuService cpuService;

    @Test
    void count_components() throws RepositoryManipulationException {
        long count = cpuService.countComponents();
        Assertions.assertTrue(count >= 0L);
    }

    @Test
    void get_component_by_id() throws RepositoryManipulationException {
        CPU cpu = cpuService.getComponentById(1L);
        Assertions.assertNotNull(cpu);
    }

    @Test
    void search_component_by_name() throws RepositoryManipulationException {
        String name = "Intel Core ii7-8550U";
        String componentId = "eb3416bd-5a09-46ad-a271-909e12e3adba";
        CPU cpu = cpuService.searchComponentByName(name);
        Assertions.assertEquals(componentId, cpu.getComponentId());
    }

    @Test
    void get_all_components() throws RepositoryManipulationException {
        List<CPU> cpuList = cpuService.getAllComponents();
        Assertions.assertTrue(cpuList.size() >= 1);
    }

    @Test
    void load_component_existing() throws RepositoryManipulationException {
        CPU cpu = ModelHelper.getCpu();
        CPU cpuAdded = cpuService.loadComponent(cpu);
        Assertions.assertEquals(cpu.getComponentId(), cpuAdded.getComponentId());
    }

    @Test
    void load_component_new() throws RepositoryManipulationException {
        CPU cpu = ModelHelper.getCpu();
        cpu.setComponentId(UUID.randomUUID().toString());
        CPU cpuAdded = cpuService.loadComponent(cpu);
        Assertions.assertEquals(cpu.getComponentId(), cpuAdded.getComponentId());
    }

    @Test
    void reduce_stock_by_id() throws RepositoryManipulationException {
        int oldQuantity = cpuService.getComponentById(1).getQuantity();
        cpuService.reduceStockById(1, 1);
        int newQuantity = cpuService.getComponentById(1).getQuantity();
        Assertions.assertTrue(oldQuantity == 0 || (oldQuantity - 1 == newQuantity));
    }

    @Test
    void reduce_stock_by_id_not_found() {
        Assertions.assertThrows(RepositoryManipulationException.class, () -> cpuService.reduceStockById(1000, 1));
    }

    @Test
    void restore_stock_by_id() throws RepositoryManipulationException {
        int oldQuantity = cpuService.getComponentById(1).getQuantity();
        cpuService.restoreStockById(1, 1);
        int newQuantity = cpuService.getComponentById(1).getQuantity();
        Assertions.assertEquals(oldQuantity + 1, newQuantity);
    }

    @Test
    void restore_stock_by_id_not_found() {
        Assertions.assertThrows(RepositoryManipulationException.class, () -> cpuService.restoreStockById(1000, 1));
    }
}