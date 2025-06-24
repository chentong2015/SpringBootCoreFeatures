package org.springboot.async;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class SpringBootAsyncTaskApplication {

    @Autowired
    @Qualifier("import")
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAsyncTaskApplication.class, args);
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println(this.threadPoolTaskExecutor.getPoolSize());
        System.out.println(this.threadPoolTaskExecutor.getCorePoolSize());
        System.out.println(this.threadPoolTaskExecutor.getMaxPoolSize());
        System.out.println(this.threadPoolTaskExecutor.getActiveCount());
        System.out.println(this.threadPoolTaskExecutor.getQueueCapacity());
    }
}
