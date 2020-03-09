package com.schedule.executor.service;

import com.schedule.executor.enums.Status;
import com.schedule.executor.model.ExecutionSchedule;
import com.schedule.executor.repositories.ExecutionScheduleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PessimisticLockException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Profile("taskScheduler")
@AllArgsConstructor
@Slf4j
@Service
public class ExecutionScheduleServiceImpl implements ExecutionScheduleService {

    private final ExecutionScheduleRepository executionScheduleRepository;
    private final ApplicationContext applicationContext;
    private final SchedulerService schedulerService;

    @Transactional
    @Override
    public ExecutionSchedule create(ExecutionSchedule executionSchedule) {
        return this.executionScheduleRepository.save(executionSchedule);
    }

    @Override
    public List<ExecutionSchedule> findNextExecutions() {
        return this.executionScheduleRepository.findByStatusInAndNextExecutionDateLessThanEqual(Arrays.asList(Status.WAITING), LocalDateTime.now());
    }

    @Transactional
    @Override
    public ExecutionSchedule save(ExecutionSchedule executionSchedule) {
        return this.executionScheduleRepository.save(executionSchedule);
    }

    private void scheduleTask() {
        log.info("starting executeScheduledTasks...");
        final List<ExecutionSchedule> nextExecutions = this.findNextExecutions();
        if (Objects.nonNull(nextExecutions) && !nextExecutions.isEmpty()) {
            nextExecutions.forEach(executionSchedule -> {
                try {
                    final ExecutionSchedule executionScheduleForUpdate = executionScheduleRepository.findByIdForUpdate(executionSchedule.getId());
                    executionScheduleForUpdate.setStatus(Status.SCHEDULED);
                    Class<?> producerClazz = Class.forName(executionScheduleForUpdate.getTargetClass());
                    final TaskProducer taskProducer = (TaskProducer) this.applicationContext.getBean(producerClazz);
                    final Runnable task = taskProducer.produce(executionScheduleForUpdate.getTargetId());
                    this.schedulerService.scheduleTask(task, ZonedDateTime.of(executionScheduleForUpdate.getNextExecutionDate(), ZoneId.systemDefault()).toInstant());
                } catch (PessimisticLockException | ClassNotFoundException ex) {
                    log.error("error when trying to acquire lock for task: {}", executionSchedule.getId());
                }
            });
        }
    }

    @Transactional
    @Scheduled(cron = "*/5 * * * * *")
    public void executeScheduledTasks() {
        this.scheduleTask();
    }
}
