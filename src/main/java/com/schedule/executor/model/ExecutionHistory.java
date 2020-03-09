package com.schedule.executor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.schedule.executor.enums.Status;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode
@Getter
@Table(name = "EXECUTION_HISTORY")
@Entity
public class ExecutionHistory {

    @Id
    private UUID id = UUID.randomUUID();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private Long duration;

    @JsonBackReference
    @JoinColumn(nullable = true, name = "execution_schedule_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ExecutionSchedule executionSchedule;

    @Column(nullable = false, name = "start_date")
    private LocalDateTime startDate;

    @Column(nullable = false, name = "end_date")
    private LocalDateTime endDate;

    public void setId(UUID id) {
        this.id = id;
    }

    private void setStatus(Status status) {
        this.status = status;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public void setExecutionSchedule(ExecutionSchedule executionSchedule) {
        this.executionSchedule = executionSchedule;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void applySuccessStatus(){
        this.setStatus(Status.SUCCESS);
    }

    public void applyFailStatus(){
        this.setStatus(Status.FAIL);
    }
}
