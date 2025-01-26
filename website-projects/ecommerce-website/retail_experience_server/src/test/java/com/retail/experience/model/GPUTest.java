package com.retail.experience.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GPUTest {

    @Test
    void set_id() {
        GPU gpu = new GPU();
        gpu.setId(10L);
        Assertions.assertEquals(10L, gpu.getId());
    }
}