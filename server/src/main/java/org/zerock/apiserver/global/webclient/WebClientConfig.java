package org.zerock.apiserver.global.webclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {


    @Bean(name = "discordWebClient")
    public WebClient discordWebClient() {
        return WebClient.builder()
                .defaultHeader("Content-Type", "application/json")
                .build();
    }




}
