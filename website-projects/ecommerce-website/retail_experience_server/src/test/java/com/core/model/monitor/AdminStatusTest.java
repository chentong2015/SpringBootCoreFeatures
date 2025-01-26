package com.core.model.monitor;

import base.model.monitor.AdminStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AdminStatusTest {

    @Test
    void test_to_string() {
        AdminStatus adminStatus = new AdminStatus();
        String result = adminStatus.toString();
        Assertions.assertTrue(result.contains("Monitor Information"));
    }
}