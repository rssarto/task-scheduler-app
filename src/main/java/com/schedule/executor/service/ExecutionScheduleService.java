package com.schedule.executor.service;

import com.schedule.executor.model.ExecutionSchedule;

import java.util.List;

public interface ExecutionScheduleService {
    ExecutionSchedule create(final ExecutionSchedule executionSchedule);
    List<ExecutionSchedule> findNextExecutions();
}
