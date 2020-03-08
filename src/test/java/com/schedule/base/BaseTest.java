package com.schedule.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public abstract class BaseTest {

    protected ObjectMapper objectMapper = new ObjectMapper();

}
