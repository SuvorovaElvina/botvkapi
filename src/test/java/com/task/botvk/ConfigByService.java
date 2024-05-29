package com.task.botvk;

import com.task.botvk.dto.SendMessagesDto;
import com.task.botvk.service.CallbackService;
import com.task.botvk.service.CallbackServiceImpl;
import com.task.botvk.service.MessageSenderService;
import com.task.botvk.сonfig.VkProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class ConfigByService {
    @Bean
    public VkProperties properties() {
        VkProperties properties = new VkProperties();
        properties.setSecret("testSecret");
        properties.setConfirmation("confirmation");
        return properties;
    }

    @Bean
    public MessageSenderService messageSenderService() {
        return mock(MessageSenderService.class);
    }

    @Bean
    public CallbackService callbackService(VkProperties properties,
                                           MessageSenderService<SendMessagesDto> messageSenderService) {
        return new CallbackServiceImpl(properties, messageSenderService);
    }
}
