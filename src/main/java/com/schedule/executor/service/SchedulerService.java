package com.schedule.executor.service;

import lombok.AllArgsConstructor;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Profile("taskScheduler")
@AllArgsConstructor
@Service
public class SchedulerService {

    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public void scheduleTask(final Runnable task, final Instant startTime) {
        threadPoolTaskScheduler.schedule(task, startTime);
    }


}
