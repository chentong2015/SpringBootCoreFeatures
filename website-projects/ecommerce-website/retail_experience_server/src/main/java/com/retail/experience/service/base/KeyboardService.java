package com.retail.experience.service.base;

import com.retail.experience.model.Keyboard;
import com.retail.experience.repositories.KeyboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyboardService extends BaseComponentService<Keyboard> {

    @Autowired
    public KeyboardService(KeyboardRepository repository) {
        setRepository(repository);
    }
}
