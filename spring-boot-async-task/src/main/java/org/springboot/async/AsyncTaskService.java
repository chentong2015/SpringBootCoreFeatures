package org.springboot.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncTaskService {

    // 这里的任务将交个线程池中线程来执行
    @Async("import")
    public void asyncTask() throws InterruptedException {
        System.out.println("Run AsyncTask by: " + Thread.currentThread().getName());
        Thread.sleep(5000);
        System.out.println("Finish AsyncTask by: " + Thread.currentThread().getName());
    }
}
