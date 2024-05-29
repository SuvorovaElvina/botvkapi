package com.task.botvk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class BotVkApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotVkApplication.class, args);
    }

}
