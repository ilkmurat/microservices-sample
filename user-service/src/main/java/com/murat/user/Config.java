package com.murat.user;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplateHandler;

public class Config {

    private static final String baseUrl = "http://localhost:8086/registration";

  /**  @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        UriTemplateHandler uriTemplateHandler = new RootUriTemplateHandler(baseUrl);
        return builder
                .uriTemplateHandler(uriTemplateHandler)
                .build();
    }
*/}
