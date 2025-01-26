package com.retail.experience.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MonitorTest {

    @Test
    void set_id() {
        Monitor monitor = new Monitor();
        monitor.setId(10L);
        Assertions.assertEquals(10L, monitor.getId());
    }
}