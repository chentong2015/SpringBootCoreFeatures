package com.retail.experience.repositories;

import com.retail.experience.model.Memory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MemoryRepository extends BaseJpaRepository<Memory> {

    Memory findByName(String name);

    Memory findByComponentId(String componentId);

    @Modifying
    @Transactional
    @Query(value = "update t_memory set quantity = quantity - ?2 where id = ?1", nativeQuery = true)
    void reduceStock(long id, int quantity);

    @Modifying
    @Transactional
    @Query(value = "update t_memory set quantity = quantity + ?2 where id = ?1", nativeQuery = true)
    void restoreStock(long id, int quantity);
}
