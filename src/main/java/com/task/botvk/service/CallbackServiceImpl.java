package com.task.botvk.service;

import com.task.botvk.dto.CallbackDto;
import com.task.botvk.dto.SendMessagesDto;
import com.task.botvk.entity.NewMessage;
import com.task.botvk.сonfig.VkProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallbackServiceImpl implements CallbackService {
    private final VkProperties vkApiProperties;

    private final MessageSenderService messageSenderService;

    @Override
    public String handleCallback(CallbackDto callbackDto) {
        validateSecret(callbackDto);
        switch (Objects.requireNonNull(callbackDto.getType())) {
            case confirmation: {
                log.info("Отправляю сообщение для подключения к сторонему серверу");
                return vkApiProperties.getConfirmation();
            }
            case message_new: {
                log.info("Получил новое сообщение, начинаю обработку для отправления ответа");
                NewMessage newMessage = parseNewMessage(callbackDto);
                handleNewMessage(newMessage);
                return "ok";
            }
            default: {
                log.error("Сервер поддерживает только 'message_new' тип");
                throw new UnsupportedOperationException("Сервер поддерживает только 'message_new' тип");
            }
        }
    }

    private void validateSecret(CallbackDto callbackDto) {
        log.info("Проверка секретного ключа у данных из контроллера");
        if (!vkApiProperties.getSecret().equals(callbackDto.getSecret())) {
            log.error("Не верный секретный ключ");
            throw new InvalidParameterException("Не верный секретный ключ");
        }
        log.info("Проверка прошла");
    }

    private void handleNewMessage(NewMessage newMessage) {
        log.info("Создаём ответное сообщение от сервера");
        SendMessagesDto dto = SendMessagesDto.builder()
                .userId(newMessage.getFromId())
                .peerId(newMessage.getPeerId())
                .message("Вы сказали: ".concat(newMessage.getText()))
                .groupId(newMessage.getGroupId())
                .build();
        messageSenderService.send(dto);
    }

    private static NewMessage parseNewMessage(CallbackDto callbackDto) {
        log.info("Парсируем в сообщение входные данные");
        Map<String, Object> maps = callbackDto.getObject();
        Map<String, Object> map = (Map<String, Object>) maps.get("message");
        return NewMessage.builder()
                .id(Long.parseLong(String.valueOf(map.get("id"))))
                .date(Long.parseLong(String.valueOf(map.get("date"))))
                .peerId(Long.parseLong(String.valueOf(map.get("peer_id"))))
                .fromId(Long.parseLong(String.valueOf(map.get("from_id"))))
                .text(String.valueOf(map.get("text")))
                .groupId(callbackDto.getGroupId())
                .build();
    }
}
