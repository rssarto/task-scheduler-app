package com.schedule.executor.service;

import java.util.UUID;

public interface TaskProducer {

    Runnable produce(final UUID sourceId);

}
