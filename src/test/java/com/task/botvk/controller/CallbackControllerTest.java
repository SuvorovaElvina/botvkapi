package com.task.botvk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.botvk.dto.CallbackDto;
import com.task.botvk.dto.enums.CallbackType;
import com.task.botvk.service.CallbackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CallbackController.class)
class CallbackControllerTest {
    @MockBean
    private CallbackService callbackService;
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mvc;

    private final CallbackDto callbackDto = CallbackDto.builder()
            .type(CallbackType.confirmation)
            .eventId("123")
            .secret("qweq")
            .build();

    @Test
    void handleCallback() throws Exception {
        when(callbackService.handleCallback(callbackDto)).thenReturn("ok");
        mvc.perform(post("/callback")
                        .content(mapper.writeValueAsString(callbackDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}