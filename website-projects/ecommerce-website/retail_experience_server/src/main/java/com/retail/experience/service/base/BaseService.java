package com.retail.experience.service.base;

import com.retail.experience.exception.RepositoryManipulationException;

public interface BaseService {

    void reduceStockById(long id, int quantity) throws RepositoryManipulationException;

    void restoreStockById(long id, int quantity) throws RepositoryManipulationException;
}
