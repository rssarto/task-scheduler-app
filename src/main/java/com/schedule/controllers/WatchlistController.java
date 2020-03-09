package com.schedule.controllers;

import java.util.UUID;

import com.schedule.model.Watchlist;
import com.schedule.services.WatchlistService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<Watchlist> create(@RequestBody final Watchlist watchlist) throws ClassNotFoundException {
        return new ResponseEntity<>(this.watchlistService.create(watchlist), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Watchlist> findById(@PathVariable final UUID id){
        return new ResponseEntity<>(this.watchlistService.findById(id), HttpStatus.OK);
    }

}
