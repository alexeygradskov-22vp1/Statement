package ru.gav.creditbank.statement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${base-urls.deal}")
    private String dealBaseUrl;

    @Bean(name = "dealWebClient")
    public WebClient dealWebClient() {
        return WebClient.builder().baseUrl(dealBaseUrl).build();
    }
}
