package com.schedule.controllers;

import com.schedule.base.WebMvcTest;
import com.schedule.enums.ScheduleType;
import com.schedule.model.Watchlist;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class WatchlistControllerTest extends WebMvcTest {

    @Test
    public void createWatchlist() throws Exception {
        final Watchlist watchlist = new Watchlist();
        watchlist.setExecutionInterval(1);
        watchlist.setScheduleType(ScheduleType.DAILY);
        watchlist.setName("Daily Watchlist");

        final String payload = this.objectMapper.writeValueAsString(watchlist);
        log.info("watchlist payload: {}", payload);

        this.mockMvc.perform(post(WatchlistController.URI_PREFIX)
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
            .andExpect(status().isOk())
            .andExpect(mvcResult -> {
                log.info("watch list created: {}", mvcResult.getResponse().getContentAsString());
                assertThat(mvcResult.getResponse().getContentAsString()).isNotBlank();
            });
    }

}

