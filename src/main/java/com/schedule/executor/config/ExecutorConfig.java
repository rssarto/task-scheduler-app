package com.schedule.executor.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Profile("taskScheduler")
@Configuration
public class ExecutorConfig {

    @Bean
    public ExecutorService scheduledExecutorService(){
        return Executors.newFixedThreadPool(8);
    }


}
