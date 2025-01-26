package com.retail.experience.controller;

import base.model.monitor.AdminStatus;
import com.retail.experience.service.controller.AdminControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminControllerService adminService;

    @Autowired
    public AdminController(AdminControllerService adminService) {
        this.adminService = adminService;
    }

    @ResponseBody
    @GetMapping("/login/{username}/{password}")
    public boolean login(@PathVariable String username, @PathVariable String password) {
        return adminService.validLogin(username, password);
    }

    @GetMapping("/status")
    public AdminStatus getStatus() {
        return adminService.retrieveCurrentMonitorStatus();
    }
}
