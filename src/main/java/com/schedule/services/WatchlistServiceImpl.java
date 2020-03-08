package com.schedule.services;

import com.schedule.executor.enums.ExecutionType;
import com.schedule.executor.enums.Status;
import com.schedule.executor.model.ExecutionHistory;
import com.schedule.executor.model.ExecutionSchedule;
import com.schedule.executor.service.ExecutionScheduleService;
import com.schedule.executor.service.TaskProducer;
import com.schedule.model.Watchlist;
import com.schedule.repositories.WatchlistRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class WatchlistServiceImpl implements WatchlistService, TaskProducer<Watchlist> {

    private final WatchlistRepository watchlistRepository;
    private final ExecutionScheduleService executionScheduleService;

    @Transactional
    @Override
    public Watchlist create(Watchlist watchlist) {
        watchlist.setId(UUID.randomUUID());
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

    @Transactional(propagation = Propagation.NESTED)
    @Override
    public Watchlist save(Watchlist watchlist) {
        Watchlist savedWatchlist = null;
        try {
            watchlist.setExecutionSchedule(this.executionScheduleService.save(watchlist.getExecutionSchedule()));
            savedWatchlist = this.watchlistRepository.save(watchlist);
        } catch (Exception ex) {
            log.error("error while saving watchlist", ex);
        }
        return savedWatchlist;
    }

    @Override
    public Watchlist findById(UUID id) {
        final Optional<Watchlist> optionalWatchlist = this.watchlistRepository.findById(id);
        final Watchlist watchlist = optionalWatchlist.orElseThrow(() -> new EntityNotFoundException(String.format("No watchlist found with id: %s", id)));
        return watchlist;
    }

    @Override
    public LocalDateTime calculateFirstExecutionDate(final Watchlist watchlist) {
        return LocalDateTime.now().plusSeconds(watchlist.getExecutionInterval() * 10);
    }

    @Override
    public LocalDateTime calculateNextExecutionDate(final Watchlist watchlist) {
        return watchlist.getExecutionSchedule().getNextExecutionDate().plusMinutes(1);
    }

    @Override
    public Runnable produce(UUID sourceId) {
        final RunWatchlistOperation runWatchlistOperation = new RunWatchlistOperation(this, this.findById(sourceId));
        return runWatchlistOperation;
    }

    @Slf4j
    @AllArgsConstructor
    public static class RunWatchlistOperation implements Runnable {
        private WatchlistService watchlistService;
        private Watchlist watchlist;

        @Override
        public void run() {
            StopWatch stopWatch = new StopWatch(RunWatchlistOperation.class.getSimpleName());
            stopWatch.start(String.format("STARTING watchlist: %s - %s", watchlist.getId(), watchlist.getName()));
            this.watchlist.getExecutionSchedule().setStatus(Status.RUNNING);
            this.watchlistService.save(watchlist);

            final ExecutionHistory executionHistory = new ExecutionHistory();
            try {
                log.info("EXECUTING watchlist: {} - {}", watchlist.getId(), watchlist.getName());
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException ex) {
                }
                executionHistory.setStatus(Status.SUCCESS);
            } catch (Exception ex) {
                executionHistory.setStatus(Status.FAIL);
            } finally {
                stopWatch.stop();
                log.info("END watchlist {} - {} result: {}", watchlist.getId(), watchlist.getName(), executionHistory.getStatus());
                executionHistory.setDuration(stopWatch.getTotalTimeMillis());
                executionHistory.setExecutionDate(LocalDateTime.now());
                this.watchlist.getExecutionSchedule().addExecutionHistory(executionHistory);
                this.watchlist.getExecutionSchedule().setNextExecutionDate(this.watchlistService.calculateNextExecutionDate(watchlist));
                this.watchlist.getExecutionSchedule().setStatus(Status.WAITING);
                this.watchlistService.save(watchlist);
            }
        }
    }
}
