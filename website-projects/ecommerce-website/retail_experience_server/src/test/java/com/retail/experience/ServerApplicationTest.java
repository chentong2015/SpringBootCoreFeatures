package com.retail.experience;

import com.retail.experience.config.ApplicationContextProvider;
import com.retail.experience.exception.RepositoryManipulationException;
import com.retail.experience.service.base.CpuService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
class ServerApplicationTest {

    @Test
    void main() throws RepositoryManipulationException {
        ServerApplication.main(new String[]{});
        CpuService cpuService = ApplicationContextProvider.getBean(CpuService.class);
        Assertions.assertTrue(cpuService.getAllComponents().size() > 0);
    }
}