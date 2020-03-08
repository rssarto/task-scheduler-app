package com.schedule.executor.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;

@AllArgsConstructor
@Service
public class SchedulerService {

    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public void scheduleTask(final Runnable task, final Instant startTime){
        threadPoolTaskScheduler.schedule(task, startTime);
    }



}
