package com.schedule.executor.model;

import com.schedule.executor.enums.ExecutionType;
import com.schedule.executor.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Table(name = "EXECUTION_SCHEDULE")
@Entity
public class ExecutionSchedule {

    @Id
    private UUID id = UUID.randomUUID();
    @Column(nullable = false)
    private LocalDateTime nextExecutionDate;
    @Column(unique = true, nullable = false)
    private UUID targetId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExecutionType executionType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "executionSchedule")
    private Set<ExecutionHistory> historySet;
    @Column(nullable = false)
    private String targetClass;

    public ExecutionSchedule() {
        this.historySet = new HashSet<>();
    }

    public void addExecutionHistory(ExecutionHistory executionHistory) {
        this.historySet.add(executionHistory);
        executionHistory.setExecutionSchedule(this);
    }

    public void removeExecutionHistory(ExecutionHistory executionHistory) {
        this.historySet.remove(executionHistory);
        executionHistory.setExecutionSchedule(null);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ExecutionSchedule rhs = (ExecutionSchedule) obj;
        return this.id.equals(rhs.id);
    }
}


