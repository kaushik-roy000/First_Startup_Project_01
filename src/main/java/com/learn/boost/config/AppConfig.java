package com.learn.boost.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

public class AppConfig {

    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
