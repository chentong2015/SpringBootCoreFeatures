package com.retail.experience.service.controller;

import base.model.monitor.AdminStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AdminControllerServiceTest {

    private final AdminControllerService adminService = new AdminControllerService();

    @Test
    void valid_login_successfully() {
        String username = "root";
        String password = "21232f297a57a5a743894a0e4a801fc3";
        Assertions.assertTrue(adminService.validLogin(username, password));
    }

    @Test
    void valid_login_failed() {
        String username = "root";
        String password = "root";
        Assertions.assertFalse(adminService.validLogin(username, password));
    }

    @Test
    void retrieve_current_monitor_status() {
        AdminStatus adminStatus = adminService.retrieveCurrentMonitorStatus();
        Assertions.assertNotNull(adminStatus);
    }
}