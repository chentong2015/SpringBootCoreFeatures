package com.retail.admin.monitor;

import base.model.monitor.AdminStatus;
import com.retail.admin.networking.RetailExperienceAdmin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.TimerTask;

public class MonitorTimerTask extends TimerTask {

    private AdminStatus oldAdminStatus;
    private final RetailExperienceAdmin admin;
    private final Logger logger = LogManager.getLogger(MonitorTimerTask.class.getName());

    public MonitorTimerTask(RetailExperienceAdmin admin) {
        this.admin = admin;
    }

    @Override
    public void run() {
        AdminStatus adminStatus = admin.getStatus();
        if (shouldRefreshStatus(adminStatus)) {
            oldAdminStatus = adminStatus;
            logger.info("Refresh new adminStatus: \n {}", adminStatus);
        }
    }

    private boolean shouldRefreshStatus(AdminStatus newAdminStatus) {
        if (oldAdminStatus == null) return true;
        return (newAdminStatus.getNumberOfConnectedClients() != oldAdminStatus.getNumberOfConnectedClients())
                || (newAdminStatus.getNumberOfOrdersWaiting() != oldAdminStatus.getNumberOfOrdersWaiting())
                || (newAdminStatus.getNumberOfOrdersProcessing() != oldAdminStatus.getNumberOfOrdersProcessing())
                || (newAdminStatus.getNumberOfOrdersDone() != oldAdminStatus.getNumberOfOrdersDone());
    }
}
