package com.schedule.services;

import com.schedule.model.Watchlist;

import java.time.LocalDateTime;
import java.util.UUID;

public interface WatchlistService {
    Watchlist create(final Watchlist watchlist) throws ClassNotFoundException;

    Watchlist save(Watchlist watchlist);

    Watchlist findById(UUID id);

    LocalDateTime calculateFirstExecutionDate(final Watchlist watchlist);

    LocalDateTime calculateNextExecutionDate(final Watchlist watchlist);
}
