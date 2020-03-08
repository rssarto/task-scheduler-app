package com.schedule.controllers;

import com.schedule.model.Watchlist;
import com.schedule.services.WatchlistService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(WatchlistController.URI_PREFIX)
public class WatchlistController {

    public static final String URI_PREFIX = "/watchlist";
    private final WatchlistService watchlistService;

    @PostMapping
    public ResponseEntity<Watchlist> create(@RequestBody final Watchlist watchlist) {
        return new ResponseEntity<>(this.watchlistService.create(watchlist), HttpStatus.OK);
    }

}
