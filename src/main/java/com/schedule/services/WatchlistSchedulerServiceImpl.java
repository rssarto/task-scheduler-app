package com.schedule.services;

import com.schedule.executor.service.SchedulerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@Slf4j
@AllArgsConstructor
public class WatchlistSchedulerServiceImpl implements WatchlistSchedulerService {

    private final WatchlistService watchlistService;
    private final SchedulerService schedulerService;

//    @Scheduled(cron = "*/5 * * * * *")
//    @Override
//    public void scheduleWatchlist() {
//        //WAITING
//        //SCHEDULED
//        //RUNNING
//        //EXECUTION_HISTORY -> RESULT (FAILED, SUCCESS)
//        //WAITING (RESCHEDULED)
//
//        this.schedulerService.scheduleTask(() -> {log.info("executing scheduleWatchlist");}, ZonedDateTime.now().plusSeconds(30).toInstant());
//    }
}
