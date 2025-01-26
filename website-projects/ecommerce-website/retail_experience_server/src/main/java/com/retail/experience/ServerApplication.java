package com.retail.experience;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

    private static final Logger logger = LogManager.getLogger(ServerApplication.class.getName());

    public static void main(String[] args) {
        logger.info("Start application");
        SpringApplication.run(ServerApplication.class, args);
    }
}
