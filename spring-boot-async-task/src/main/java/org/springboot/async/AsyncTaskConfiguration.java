package org.springboot.async;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncTaskConfiguration {

    // TODO. 统一定义线程池来执行异步的操作
    // 线程池中线程能够被复用，用于异步执行并发的Task任务
    // 线程池的参数通过属性动态设置，决定系统执行的性能优劣
    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    // 通过Properties属性配置线程池相关参数
    @Bean
    @Qualifier("import")
    @ConfigurationProperties(prefix = "import.thread-pool")
    public ThreadPoolTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // Throw an Exception when trying to queue additional import
        // so that we could forward error to end user, that there too many imports in progress
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }
}
