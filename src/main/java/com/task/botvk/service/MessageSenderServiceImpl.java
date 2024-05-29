package com.task.botvk.service;

import com.task.botvk.dto.ErrorResultMessagesDto;
import com.task.botvk.dto.ResultMessagesDto;
import com.task.botvk.dto.SendMessagesDto;
import com.task.botvk.entity.VkUriCreator;
import com.task.botvk.exception.MessageSenderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageSenderServiceImpl implements MessageSenderService<SendMessagesDto> {
    private final int TEXT_MAX_LENGTH = 2000;
    private final RestTemplate restTemplate;
    private final VkUriCreator vkUriCreator;

    @Override
    public void send(SendMessagesDto message) {
        List<SendMessagesDto> messages = parseIfBigMessage(message);
        messages.forEach(this::sendInternal);
    }

    private List<SendMessagesDto> parseIfBigMessage(SendMessagesDto dto) {
        log.info("Проверяем нужно ли отправлять сообщение в два подхода");
        String originalMessage = dto.getMessage();
        int capacity = originalMessage.length() / TEXT_MAX_LENGTH + 1;
        List<SendMessagesDto> result = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            int beginIndex = i * TEXT_MAX_LENGTH;
            int endIndex = Math.min((i + 1) * TEXT_MAX_LENGTH, originalMessage.length());
            result.add(copyWithNewMessage(dto, originalMessage.substring(beginIndex, endIndex)));
        }
        return result;
    }

    private SendMessagesDto copyWithNewMessage(SendMessagesDto dto, String message) {
        log.info("Создаём сообщение для стороннего сервера");
        return SendMessagesDto.builder()
                .userId(dto.getUserId())
                .peerId(dto.getPeerId())
                .domain(dto.getDomain())
                .chatId(dto.getChatId())
                .userIds(dto.getUserIds())
                .message(message)
                .lat(dto.getLat())
                .longField(dto.getLongField())
                .attachment(dto.getAttachment())
                .replyTo(dto.getReplyTo())
                .forwardMessages(dto.getForwardMessages())
                .stickerId(dto.getStickerId())
                .groupId(dto.getGroupId())
                .keyboard(dto.getKeyboard())
                .payload(dto.getPayload())
                .dontParseLinks(dto.getDontParseLinks())
                .build();
    }

    private void sendInternal(SendMessagesDto message) {
        message.setRandomId((long) message.hashCode());
        URI uri = vkUriCreator.createUri(message);
        log.info("Отправляем сообщение стороннему серверу по созданному url");
        ResponseEntity<ResultMessagesDto> responseEntity = restTemplate.postForEntity(uri, null, ResultMessagesDto.class);
        validateResponse(responseEntity);
    }

    private void validateResponse(ResponseEntity<ResultMessagesDto> responseEntity) {
        log.info("Проверяем ответ от стороннего сервера");
        ErrorResultMessagesDto error = Objects.requireNonNull(responseEntity.getBody()).getError();
        if (error != null && error.getErrorCode() != null) {
            log.error("Ошибка {}", error.getErrorMsg());
            throw new MessageSenderException(error.getErrorMsg());
        }
        log.info("Ответ без ошибок");
    }
}
