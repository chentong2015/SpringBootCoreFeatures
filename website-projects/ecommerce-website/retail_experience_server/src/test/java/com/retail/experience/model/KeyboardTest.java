package com.retail.experience.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class KeyboardTest {

    @Test
    void set_id() {
        Keyboard keyboard = new Keyboard();
        keyboard.setId(10L);
        Assertions.assertEquals(10L, keyboard.getId());
    }
}