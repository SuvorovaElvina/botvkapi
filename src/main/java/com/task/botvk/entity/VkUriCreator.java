package com.task.botvk.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.botvk.dto.SendMessagesDto;
import com.task.botvk.exception.MessageSenderException;
import com.task.botvk.сonfig.VkProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class VkUriCreator {
    private final VkProperties vkProperties;
    private final ObjectMapper objectMapper;

    public URI createUri(SendMessagesDto dto) {
        try {
            log.info("Создаём url для обращения к стороннему серверу");
            MultiValueMap<String, String> map = objectMapper.convertValue(dto, LinkedMultiValueMap.class);
            return UriComponentsBuilder.fromHttpUrl("https://api.vk.com/method/messages.send")
                    .queryParam("access_token", vkProperties.getAccessToken())
                    .queryParam("v", vkProperties.getV())
                    .queryParams(map)
                    .build()
                    .toUri();
        } catch (ClassCastException e) {
            throw new MessageSenderException(e);
        }
    }
}
