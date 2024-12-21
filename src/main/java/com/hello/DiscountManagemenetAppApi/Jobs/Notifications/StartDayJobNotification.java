package com.hello.DiscountManagemenetAppApi.Jobs.Notifications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class StartDayJobNotification implements JobExecutionListener {
    Logger logger= LoggerFactory.getLogger(StartDayJobNotification.class);
    private long startTime;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Start Day Job Started...");
        startTime = System.currentTimeMillis();
        JobExecutionListener.super.beforeJob(jobExecution);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("Start Day Job Completed.");
        long endTime = System.currentTimeMillis();
        double executionTimeSeconds = (endTime - startTime) / 1000.0;
        System.out.println("Total execution time: " + executionTimeSeconds + " seconds");
        JobExecutionListener.super.afterJob(jobExecution);
    }

}

