package com.retail.experience.service.base;

import com.retail.experience.model.GPU;
import com.retail.experience.repositories.GpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GpuService extends BaseComponentService<GPU> {

    @Autowired
    public GpuService(GpuRepository repository) {
        setRepository(repository);
    }
}
