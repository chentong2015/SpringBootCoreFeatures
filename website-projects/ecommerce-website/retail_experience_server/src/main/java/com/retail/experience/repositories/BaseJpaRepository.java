package com.retail.experience.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseJpaRepository<T> extends JpaRepository<T, Long> {

    T findByName(String name);

    T findByComponentId(String componentId);

    void reduceStock(long id, int quantity);

    void restoreStock(long id, int quantity);
}
