package com.schedule.executor.model;

import com.schedule.executor.enums.Status;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Table(name = "EXECUTION_HISTORY")
@Entity
public class ExecutionHistory {

    @Id
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private Long duration;

    @JoinColumn(nullable = true, name = "execution_schedule_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ExecutionSchedule executionSchedule;

}
