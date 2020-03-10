package com.schedule;

import java.lang.management.ManagementFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class TaskSchedulerApplication {
    //TODO localized messages
    //TODO Swagger

    public static void main(String[] args) {
        SpringApplication.run(TaskSchedulerApplication.class, args);
        log.info("ENVIRONMENT INFO: {}", ManagementFactory.getRuntimeMXBean().getName());
    }

}
