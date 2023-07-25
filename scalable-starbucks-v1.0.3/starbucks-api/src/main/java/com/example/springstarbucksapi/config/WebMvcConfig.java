package com.example.springstarbucksapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public Queue starbucksQueue() {
        return new Queue("starbucks-queue");
    }
}
