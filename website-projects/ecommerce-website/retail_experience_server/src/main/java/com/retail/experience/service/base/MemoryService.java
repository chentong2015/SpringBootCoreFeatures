package com.retail.experience.service.base;

import com.retail.experience.model.Memory;
import com.retail.experience.repositories.MemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemoryService extends BaseComponentService<Memory> {

    @Autowired
    public MemoryService(MemoryRepository repository) {
        setRepository(repository);
    }
}
