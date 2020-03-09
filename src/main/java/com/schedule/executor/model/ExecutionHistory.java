package com.schedule.executor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.schedule.executor.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode
@Data
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

    @Column(nullable = false, name = "executiondate")
    private LocalDateTime executionDate;

}
