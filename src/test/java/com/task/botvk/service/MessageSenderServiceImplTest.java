package com.task.botvk.service;

import com.task.botvk.dto.ErrorResultMessagesDto;
import com.task.botvk.dto.ResultMessagesDto;
import com.task.botvk.dto.SendMessagesDto;
import com.task.botvk.entity.VkUriCreator;
import com.task.botvk.exception.MessageSenderException;
import com.task.botvk.service.MessageSenderService;
import com.task.botvk.service.MessageSenderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MessageSenderServiceImplTest {
    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final VkUriCreator uriCreator = mock(VkUriCreator.class);
    private final MessageSenderService service = new MessageSenderServiceImpl(restTemplate, uriCreator);
    private final URI uri = URI.create("https://api.vk.com/method/messages.send?" +
            "access_token=test&v=5.236&group_id=0&message=qwe&user_id=1");

    @Test
    void send() {
        SendMessagesDto messagesDto = SendMessagesDto.builder()
                .userId(1L)
                .peerId(1231L)
                .message("qwe")
                .groupId(0L).build();
        ResultMessagesDto resultMessagesDto = ResultMessagesDto.builder().build();

        when(uriCreator.createUri(any()))
                .thenReturn(uri);
        when(restTemplate.postForEntity(uri, null, ResultMessagesDto.class))
                .thenReturn(ResponseEntity.ok().body(resultMessagesDto));

        service.send(messagesDto);

        verify(uriCreator, times(1)).createUri(any());
        verify(restTemplate, times(1)).postForEntity(any(), any(), any());
    }

    @Test
    void sendErrorResponse() {
        SendMessagesDto messagesDto = SendMessagesDto.builder()
                .userId(1L)
                .peerId(1231L)
                .message("qwe")
                .groupId(0L).build();
        ErrorResultMessagesDto errorMessagesDto = ErrorResultMessagesDto.builder().errorCode(1L).errorMsg("123").build();
        ResultMessagesDto resultMessagesDto = ResultMessagesDto.builder().error(errorMessagesDto).build();

        when(uriCreator.createUri(any()))
                .thenReturn(uri);
        when(restTemplate.postForEntity(uri, null, ResultMessagesDto.class))
                .thenReturn(ResponseEntity.ok().body(resultMessagesDto));

        Throwable thrown = assertThrows(MessageSenderException.class, () -> {
            service.send(messagesDto);
        });

        assertNotNull(thrown.getMessage());
    }

    @Test
    void sendBigMessage() {
        SendMessagesDto messagesDto = SendMessagesDto.builder()
                .userId(1L)
                .peerId(1231L)
                .message("qwe" + "qwe".repeat(999))
                .groupId(0L).build();
        ResultMessagesDto resultMessagesDto = ResultMessagesDto.builder().build();

        when(uriCreator.createUri(any()))
                .thenReturn(uri);
        when(restTemplate.postForEntity(uri, null, ResultMessagesDto.class))
                .thenReturn(ResponseEntity.ok().body(resultMessagesDto));

        service.send(messagesDto);

        verify(uriCreator, times(2)).createUri(any());
        verify(restTemplate, times(2)).postForEntity(any(), any(), any());
    }
}