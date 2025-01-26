package com.retail.experience.service.base;

import com.retail.experience.model.Mouse;
import com.retail.experience.repositories.MouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MouseService extends BaseComponentService<Mouse> {

    @Autowired
    public MouseService(MouseRepository repository) {
        setRepository(repository);
    }
}
