package org.springboot.async;

import org.springframework.stereotype.Component;

@Component
public class AsyncTaskExecutionEngine {

    private AsyncTaskService asyncTaskService;

    public AsyncTaskExecutionEngine(AsyncTaskService asyncTaskService) {
        this.asyncTaskService = asyncTaskService;
    }

    // TODO. 异步执行并发的任务Tasks
    //  超过线程池最大容量的Task将被放置到Queue队列中
    public void startImportTasks() throws InterruptedException {
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println("Start importing tasks");
        for (int i = 0; i < 50; i++) {
            this.asyncTaskService.doImport("task patch " + i);
        }
        System.out.println("End importing tasks");
    }
}
