package com.retail.client;

import com.retail.client.monitor.BackgroundThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
        new Thread(new BackgroundThread()).start();
    }
}
