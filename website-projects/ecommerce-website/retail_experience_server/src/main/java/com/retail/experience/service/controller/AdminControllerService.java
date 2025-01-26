package com.retail.experience.service.controller;

import base.model.monitor.AdminStatus;
import com.retail.experience.monitor.ServerMonitorSystem;
import org.springframework.stereotype.Service;

@Service
public class AdminControllerService {

    private static final String USERNAME = "root";
    private static final String ENCRYPTED_PASS = "21232f297a57a5a743894a0e4a801fc3";

    /**
     * Should valid this information in repository with database
     *
     * @param username:      username string
     * @param encryptedPass: the password encrypted from client side
     * @return: ture if it's valid
     */
    public boolean validLogin(String username, String encryptedPass) {
        return username.equals(USERNAME) && encryptedPass.equals(ENCRYPTED_PASS);
    }

    public AdminStatus retrieveCurrentMonitorStatus() {
        return ServerMonitorSystem.reportStatus();
    }
}
