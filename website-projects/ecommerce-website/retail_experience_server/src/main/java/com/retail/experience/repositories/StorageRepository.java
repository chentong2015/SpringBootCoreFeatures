package com.retail.experience.repositories;

import com.retail.experience.model.Storage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface StorageRepository extends BaseJpaRepository<Storage> {

    Storage findByName(String name);

    Storage findByComponentId(String componentId);

    @Modifying
    @Transactional
    @Query(value = "update t_storage set quantity = quantity - ?2 where id = ?1", nativeQuery = true)
    void reduceStock(long id, int quantity);

    @Modifying
    @Transactional
    @Query(value = "update t_storage set quantity = quantity + ?2 where id = ?1", nativeQuery = true)
    void restoreStock(long id, int quantity);
}
