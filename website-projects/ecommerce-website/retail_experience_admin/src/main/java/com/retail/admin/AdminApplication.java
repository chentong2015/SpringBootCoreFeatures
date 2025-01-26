package com.retail.admin;

import com.retail.admin.monitor.MonitorTimerTask;
import com.retail.admin.networking.RetailExperienceAdmin;
import com.retail.admin.util.Md5Util;
import feign.Feign;
import feign.Request;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class AdminApplication {

    private static RetailExperienceAdmin admin;
    private static final String URL_ADMIN = "http://localhost:8080/admin";
    private static final Logger logger = LogManager.getLogger(AdminApplication.class.getName());

    public static void main(String[] args) {
        logger.info("## Admin Monitoring System ##");
        buildFeign();
        authenticateAdmin();
        TimerTask task = new MonitorTimerTask(admin);
        Timer timer = new Timer("Tong Admin Timer");
        timer.schedule(task, 1000L, 1000L);
    }

    private static void buildFeign() {
        Request.Options options = new Request.Options(3, TimeUnit.SECONDS, 40, TimeUnit.SECONDS, true);
        admin = Feign.builder()
                .client(new ApacheHttpClient())
                .options(options)
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger())
                .logLevel(feign.Logger.Level.BASIC)
                .target(RetailExperienceAdmin.class, URL_ADMIN);
    }

    private static void authenticateAdmin() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            logger.info("\nPlease input username: ");
            String username = scanner.nextLine();
            logger.info("\nPlease input password: ");
            String password = scanner.nextLine();
            String encryptedPass = Md5Util.encrypt(password);
            if (admin.login(username, encryptedPass)) {
                break;
            }
            logger.info("Admin authentication failed! please try again.");
        }
    }
}
