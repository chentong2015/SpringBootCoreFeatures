package com.retail.experience.repositories;

import com.retail.experience.model.Mouse;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MouseRepository extends BaseJpaRepository<Mouse> {

    Mouse findByName(String name);

    Mouse findByComponentId(String componentId);

    @Modifying
    @Transactional
    @Query(value = "update t_mouse set quantity = quantity - ?2 where id = ?1", nativeQuery = true)
    void reduceStock(long id, int quantity);

    @Modifying
    @Transactional
    @Query(value = "update t_mouse set quantity = quantity + ?2 where id = ?1", nativeQuery = true)
    void restoreStock(long id, int quantity);
}
