package com.schedule.executor.service;

import java.util.concurrent.ExecutorService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Profile("taskScheduler")
@AllArgsConstructor
@Service
public class ScheduleExecutorService {

    private final ExecutorService         executorService;

    public void runTask(final Runnable task) {
        executorService.submit(task);
    }


}
