package com.schedule.model;

import com.schedule.enums.ScheduleType;
import com.schedule.executor.model.ExecutionSchedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "WATCHLIST")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Watchlist {

    @Id
    private UUID id = UUID.randomUUID();
    private String name;

    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType;
    private int executionInterval;

    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "execution_schedule_id", unique = true, nullable = false)
    private ExecutionSchedule executionSchedule;

}
