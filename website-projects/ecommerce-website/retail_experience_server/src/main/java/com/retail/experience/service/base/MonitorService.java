package com.retail.experience.service.base;

import com.retail.experience.model.Monitor;
import com.retail.experience.repositories.MonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitorService extends BaseComponentService<Monitor> {

    @Autowired
    public MonitorService(MonitorRepository repository) {
        setRepository(repository);
    }
}
