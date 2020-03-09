package com.schedule.base;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.schedule.controllers.WatchlistController;
import com.schedule.model.Watchlist;
import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("taskScheduler")
@Slf4j
public abstract class WatchlistMvcTest extends WebMvcTest {

    protected ResultActions callFindByIdApi(final UUID id) throws Exception {
        log.info("calling find api by id: {}", id);
        return this.mockMvc.perform(get(WatchlistController.URI_PREFIX + "/" + id.toString()))
                    .andExpect(mvcResult -> {
                        log.info("response from Find Watchlist By Id Api: {}", mvcResult.getResponse().getContentAsString());
                    });
    }

    protected ResultActions callCreateApi(final Watchlist watchlist) throws Exception {
        final String payload = this.objectMapper.writeValueAsString(watchlist);
        log.info("watchlist payload: {}", payload);
        return this.mockMvc.perform(post(WatchlistController.URI_PREFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(mvcResult -> {
                    log.info("response from Create Watchlist Api: {}", mvcResult.getResponse().getContentAsString());
                });
    }

    protected Watchlist deserializeResponse(final MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return this.deserializeWatchlist(mvcResult.getResponse().getContentAsString());
    }

    protected Watchlist deserializeWatchlist(String source) throws JsonProcessingException {
        return this.objectMapper.readValue(source, Watchlist.class);
    }

}
