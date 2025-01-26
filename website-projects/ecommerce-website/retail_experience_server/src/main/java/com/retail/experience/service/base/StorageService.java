package com.retail.experience.service.base;

import com.retail.experience.model.Storage;
import com.retail.experience.repositories.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageService extends BaseComponentService<Storage> {

    @Autowired
    public StorageService(StorageRepository repository) {
        setRepository(repository);
    }
}
