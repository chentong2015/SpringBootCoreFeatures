package com.retail.experience.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MouseTest {

    @Test
    void set_id() {
        Mouse mouse = new Mouse();
        mouse.setId(10L);
        Assertions.assertEquals(10L, mouse.getId());
    }
}