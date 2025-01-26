package com.retail.experience.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MemoryTest {

    @Test
    void set_id() {
        Memory memory = new Memory();
        memory.setId(10L);
        Assertions.assertEquals(10L, memory.getId());
    }
}