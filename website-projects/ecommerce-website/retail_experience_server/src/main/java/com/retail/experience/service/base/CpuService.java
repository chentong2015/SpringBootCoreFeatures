package com.retail.experience.service.base;

import com.retail.experience.model.CPU;
import com.retail.experience.repositories.CpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CpuService extends BaseComponentService<CPU> {

    @Autowired
    public CpuService(CpuRepository repository) {
        setRepository(repository);
    }
}
