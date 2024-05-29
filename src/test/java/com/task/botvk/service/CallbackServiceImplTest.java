package com.task.botvk.service;

import com.task.botvk.ConfigByService;
import com.task.botvk.dto.CallbackDto;
import com.task.botvk.dto.enums.CallbackType;
import com.task.botvk.service.CallbackService;
import com.task.botvk.service.MessageSenderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ConfigByService.class, loader = AnnotationConfigContextLoader.class)
public class CallbackServiceImplTest {
    private final String SECRET = "testSecret";
    @Autowired
    private MessageSenderService senderService;
    @Autowired
    private CallbackService service;

    @Test
    public void handleCallbackNull() {
        assertThrows(NullPointerException.class, () -> {
            service.handleCallback(null);
        });
    }

    @Test
    public void handleCallbackEmpty() {
        Throwable thrown = assertThrows(InvalidParameterException.class, () -> {
            service.handleCallback(CallbackDto.builder().build());
        });

        assertNotNull(thrown.getMessage());
    }

    @Test
    public void handleCallbackWithIncorrectSecret() {
        Throwable thrown = assertThrows(InvalidParameterException.class, () -> {
            service.handleCallback(CallbackDto.builder().secret("").build());
        });

        assertNotNull(thrown.getMessage());
    }

    @Test
    public void handleCallbackWithTypeNull() {
        assertThrows(NullPointerException.class, () -> {
            service.handleCallback(CallbackDto.builder().type(null).secret(SECRET).build());
        });
    }

    @Test
    public void handleCallbackWithTypeConfirmation() {
        CallbackDto validDto = CallbackDto.builder()
                .secret(SECRET)
                .type(CallbackType.confirmation)
                .build();
        String str = service.handleCallback(validDto);

        assertEquals("confirmation", str);
    }

    @Test
    public void handleCallback() {
        Map<String, Object> objectMap = new HashMap<>();
        Map<String, Object> messageMap = new LinkedHashMap<>();
        messageMap.put("id", "0");
        messageMap.put("date", "1");
        messageMap.put("peer_id", "1");
        messageMap.put("from_id", "2");
        messageMap.put("text", "test");
        objectMap.put("message", messageMap);
        CallbackDto validDto = CallbackDto.builder()
                .eventId("2131")
                .groupId(0L)
                .secret(SECRET)
                .type(CallbackType.message_new)
                .object(objectMap)
                .build();
        String str = service.handleCallback(validDto);

        assertEquals("ok", str);
        verify(senderService, times(1)).send(any());
    }
}