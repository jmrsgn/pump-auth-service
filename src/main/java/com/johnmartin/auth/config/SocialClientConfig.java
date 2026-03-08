package com.johnmartin.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class SocialClientConfig {

    @Bean
    public RestClient socialWebClient() {
        // return RestClient.builder().baseUrl(ApiConstants.PumpSocialService.URL).build();
        return RestClient.builder().baseUrl("http://localhost:8080").build();
    }
}
