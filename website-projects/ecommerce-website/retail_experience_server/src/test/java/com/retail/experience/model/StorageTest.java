package com.retail.experience.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StorageTest {

    @Test
    void set_id() {
        Storage storage = new Storage();
        storage.setId(10L);
        Assertions.assertEquals(10L, storage.getId());
    }
}