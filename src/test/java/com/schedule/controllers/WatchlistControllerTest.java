package com.schedule.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.schedule.base.WatchlistMvcTest;
import com.schedule.base.WebMvcTest;
import com.schedule.enums.ScheduleType;
import com.schedule.model.Watchlist;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WatchlistControllerTest extends WatchlistMvcTest {

    @Test
    public void createWatchlist() throws Exception {
        final Watchlist watchlist = newDailyWatchlist();
        this.callCreateApi(watchlist)
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    assertThat(mvcResult.getResponse().getContentAsString()).isNotBlank();
                });
    }

    @Test
    public void findWatchlistById() throws Exception {
        final Watchlist watchlist = newDailyWatchlist();
        final ResultActions resultActions = this.callCreateApi(watchlist)
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    assertThat(mvcResult.getResponse().getContentAsString()).isNotBlank();
                });
        final Watchlist createdWatchlist = this.deserializeResponse(resultActions.andReturn());
        assertThat(createdWatchlist).isNotNull();
        final ResultActions findByIdResponse = this.callFindByIdApi(createdWatchlist.getId())
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    assertThat(mvcResult.getResponse().getContentAsString()).isNotBlank();
                });
        final Watchlist foundWatchlist = this.deserializeResponse(findByIdResponse.andReturn());
        assertThat(createdWatchlist).isEqualTo(foundWatchlist);
    }

    private Watchlist newDailyWatchlist(){
        final Watchlist watchlist = new Watchlist();
        watchlist.setExecutionInterval(1);
        watchlist.setScheduleType(ScheduleType.DAILY);
        watchlist.setName("Daily Watchlist");
        return watchlist;
    }
}

