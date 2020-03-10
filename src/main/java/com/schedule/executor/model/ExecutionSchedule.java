package com.schedule.executor.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.schedule.executor.enums.Status;
import lombok.Getter;

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
    private Status status;

    @OrderBy(value = "startDate DESC")
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "executionSchedule")
    private Set<ExecutionHistory> historySet;

    @Column(nullable = false)
    private String targetClass;

    private ExecutionSchedule() {
        this.historySet = new HashSet<>();
    }

    public static ExecutionSchedule createExecutionSchedule(final LocalDateTime nextExecutionDate,
                                                            final UUID targetId,
                                                            final Status status,
                                                            final String targetClass) throws ClassNotFoundException {
        Objects.requireNonNull(nextExecutionDate, "The argument nextExecutionDate cannot be null");
        Objects.requireNonNull(targetId, "The argument targetId cannot be null");
        Objects.requireNonNull(status, "The argument status cannot be null");
        Objects.requireNonNull(targetClass, "The argument targetClass cannot be null");
        Class.forName(targetClass);

        final ExecutionSchedule executionSchedule = new ExecutionSchedule();
        executionSchedule.setNextExecutionDate(nextExecutionDate);
        executionSchedule.setTargetId(targetId);
        executionSchedule.setStatus(status);
        executionSchedule.setTargetClass(targetClass);
        return executionSchedule;
    }

    public void addExecutionHistory(ExecutionHistory executionHistory) {
        this.historySet.add(executionHistory);
        executionHistory.setExecutionSchedule(this);
    }

    public void removeExecutionHistory(ExecutionHistory executionHistory) {
        this.historySet.remove(executionHistory);
        executionHistory.setExecutionSchedule(null);
    }

    private void setId(UUID id) {
        this.id = id;
    }

    public void setNextExecutionDate(LocalDateTime nextExecutionDate) {
        this.nextExecutionDate = nextExecutionDate;
    }

    private void setTargetId(UUID targetId) {
        this.targetId = targetId;
    }

    private void setStatus(Status status) {
        this.status = status;
    }

    private void setHistorySet(Set<ExecutionHistory> historySet) {
        this.historySet = historySet;
    }

    private void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public void applyRunningStatus(){
        this.setStatus(Status.RUNNING);
    }

    public void applyWaitingStatus(){
        this.setStatus(Status.WAITING);
    }

    public void applySentToExecutionStatus(){
        this.setStatus(Status.SENT_TO_EXECUTION);
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


