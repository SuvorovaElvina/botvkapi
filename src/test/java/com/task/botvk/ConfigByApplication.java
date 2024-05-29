package com.task.botvk;

import com.task.botvk.сonfig.VkProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigByApplication {
    @Bean
    public VkProperties vkApiConfigurationProperties() {
        VkProperties properties = new VkProperties();
        properties.setConfirmation("confirmation");
        properties.setAccessToken("test");
        properties.setSecret("testSecret");
        properties.setV(5.236);
        return properties;
    }
}
