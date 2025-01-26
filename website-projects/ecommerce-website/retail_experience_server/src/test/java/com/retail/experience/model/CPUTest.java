package com.retail.experience.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CPUTest {

    @Test
    void set_id() {
        CPU cpu = new CPU();
        cpu.setId(10L);
        Assertions.assertEquals(10L, cpu.getId());
    }
}