package com.task.botvk.сonfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@Component
@PropertySource("vk.properties")
@ConfigurationProperties("vk")
public class VkProperties {
    @NotBlank
    private String accessToken;

    @NotBlank
    private Double v;

    @NotBlank
    private String secret;

    @NotBlank
    private String confirmation;
}
