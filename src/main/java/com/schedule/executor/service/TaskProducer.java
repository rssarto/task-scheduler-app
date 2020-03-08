package com.schedule.executor.service;

import java.util.UUID;

public interface TaskProducer<T> {

    Runnable produce(final UUID sourceId);

}
