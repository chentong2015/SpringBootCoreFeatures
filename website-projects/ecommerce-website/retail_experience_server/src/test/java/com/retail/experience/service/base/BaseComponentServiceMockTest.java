package com.retail.experience.service.base;

import com.retail.experience.exception.RepositoryManipulationException;
import com.retail.experience.helper.ModelHelper;
import com.retail.experience.model.CPU;
import com.retail.experience.repositories.CpuRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class BaseComponentServiceMockTest {

    @Autowired
    private CpuService mockCpuService;

    @MockBean
    private CpuRepository cpuRepository;

    @Test
    void count_components_with_exception() throws RepositoryManipulationException {
        // Mock method throws unchecked exception
        Mockito.when(cpuRepository.count()).thenThrow(new RuntimeException(""));
        Assertions.assertThrows(RepositoryManipulationException.class, () -> mockCpuService.countComponents());
        Mockito.verify(cpuRepository).count();
    }

    @Test
    void get_component_with_exception() {
        Mockito.when(cpuRepository.getById(1L)).thenThrow(new RuntimeException(""));
        Assertions.assertThrows(RepositoryManipulationException.class, () -> mockCpuService.getComponentById(1L));
        Mockito.verify(cpuRepository).getById(1L);
    }

    @Test
    void search_component_by_name_with_exception() {
        String name = "Intel Core ii7-8550U";
        Mockito.when(cpuRepository.findByName(name)).thenThrow(new RuntimeException(""));
        Assertions.assertThrows(RepositoryManipulationException.class, () -> mockCpuService.searchComponentByName(name));
        Mockito.verify(cpuRepository).findByName(name);
    }

    @Test
    void get_all_components_with_exception() {
        Mockito.when(cpuRepository.findAll()).thenThrow(new RuntimeException(""));
        Assertions.assertThrows(RepositoryManipulationException.class, () -> mockCpuService.getAllComponents());
        Mockito.verify(cpuRepository).findAll();
    }

    @Test
    void load_component_with_exception() {
        CPU cpu = ModelHelper.getCpu();
        Mockito.when(cpuRepository.findByComponentId(cpu.getComponentId())).thenThrow(new RuntimeException(""));
        Assertions.assertThrows(RepositoryManipulationException.class, () -> mockCpuService.loadComponent(cpu));
        Mockito.verify(cpuRepository).findByComponentId(cpu.getComponentId());
    }

    @Test
    void reduce_stock_failed() {
        Mockito.when(cpuRepository.findById(0L)).thenThrow(new RuntimeException(""));
        Assertions.assertThrows(RepositoryManipulationException.class, () -> mockCpuService.reduceStockById(0L, 1));
        Mockito.verify(cpuRepository).findById(0L);
    }

    @Test
    void restore_stock_failed() {
        Mockito.when(cpuRepository.findById(0L)).thenThrow(new RuntimeException(""));
        Assertions.assertThrows(RepositoryManipulationException.class, () -> mockCpuService.restoreStockById(0L, 1));
        Mockito.verify(cpuRepository).findById(0L);
    }
}
