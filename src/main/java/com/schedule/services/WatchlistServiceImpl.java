package com.schedule.services;

import com.schedule.executor.enums.ExecutionType;
import com.schedule.executor.enums.Status;
import com.schedule.executor.model.ExecutionSchedule;
import com.schedule.executor.service.ExecutionScheduleService;
import com.schedule.executor.service.TaskProducer;
import com.schedule.model.Watchlist;
import com.schedule.repositories.WatchlistRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class WatchlistServiceImpl implements WatchlistService, TaskProducer {

    private final WatchlistRepository watchlistRepository;
    private final ExecutionScheduleService executionScheduleService;

    @Transactional
    @Override
    public Watchlist create(Watchlist watchlist) {
        final ExecutionSchedule executionSchedule = new ExecutionSchedule();
        executionSchedule.setNextExecutionDate(this.calculateFirstExecutionDate(watchlist));
        executionSchedule.setExecutionType(ExecutionType.WATCHLIST);
        executionSchedule.setTargetId(watchlist.getId());
        executionSchedule.setStatus(Status.WAITING);
        executionSchedule.setTargetClass(this.getClass().getName());
        final ExecutionSchedule savedExecutionSchedule = executionScheduleService.create(executionSchedule);

        watchlist.setExecutionSchedule(savedExecutionSchedule);
        return this.watchlistRepository.save(watchlist);
    }

    private LocalDateTime calculateFirstExecutionDate(final Watchlist watchlist){
        return LocalDateTime.now().plusSeconds(watchlist.getExecutionInterval() * 10);
    }

    @Override
    public Runnable produce(UUID sourceId) {
        return () -> log.info("executing scheduleWatchlist");
    }
}
