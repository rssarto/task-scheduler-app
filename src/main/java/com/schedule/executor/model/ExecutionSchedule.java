package com.schedule.executor.model;

import com.schedule.executor.enums.ExecutionType;
import com.schedule.executor.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Data
@Table(name = "EXECUTION_SCHEDULE")
@Entity
public class ExecutionSchedule {

    @Id
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private LocalDateTime nextExecutionDate;

    @Column(unique = true, nullable = false)
    private UUID targetId;

    @Column(nullable = false)
    private ExecutionType executionType;

    @Column(nullable = false)
    private Status status;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "executionSchedule")
    private Set<ExecutionHistory> historySet;

    @Column(nullable = false)
    private String targetClass;

}
