package com.task.botvk;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.botvk.entity.VkUriCreator;
import com.task.botvk.сonfig.VkProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigByUri {
    @Bean
    public VkProperties vkApiConfigurationProperties() {
        VkProperties properties = new VkProperties();
        properties.setAccessToken("test");
        properties.setV(5.236);
        return properties;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    @Bean
    public VkUriCreator vkUriCreator(VkProperties properties,
                                     ObjectMapper mapper) {
        return new VkUriCreator(properties, mapper);
    }
}
