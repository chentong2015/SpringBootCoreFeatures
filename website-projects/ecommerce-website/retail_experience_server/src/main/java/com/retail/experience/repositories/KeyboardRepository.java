package com.retail.experience.repositories;

import com.retail.experience.model.Keyboard;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface KeyboardRepository extends BaseJpaRepository<Keyboard> {

    Keyboard findByName(String name);

    Keyboard findByComponentId(String componentId);

    @Modifying
    @Transactional
    @Query(value = "update t_keyboard set quantity = quantity - ?2 where id = ?1", nativeQuery = true)
    void reduceStock(long id, int quantity);

    @Modifying
    @Transactional
    @Query(value = "update t_keyboard set quantity = quantity + ?2 where id = ?1", nativeQuery = true)
    void restoreStock(long id, int quantity);
}
