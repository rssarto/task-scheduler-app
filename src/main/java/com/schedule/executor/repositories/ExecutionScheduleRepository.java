package com.schedule.executor.repositories;

import com.schedule.executor.enums.Status;
import com.schedule.executor.model.ExecutionSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ExecutionScheduleRepository extends JpaRepository<ExecutionSchedule, UUID> {

    List<ExecutionSchedule> findByStatusInAndNextExecutionDateLessThanEqual(final List<Status> statuses, final LocalDateTime executionDate);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select es from ExecutionSchedule es where es.id = :id")
    ExecutionSchedule findByIdForUpdate(UUID id);

}
