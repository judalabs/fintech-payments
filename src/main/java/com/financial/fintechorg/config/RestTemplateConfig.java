package com.financial.fintechorg.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Value("${app.timeout.limit}")
    private int timeoutInMillis;

    @Bean
    @Primary
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Qualifier("timeout")
    public RestTemplate restTemplateTimeout() {
        RestTemplate restTemplate = restTemplate();
        ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(timeoutInMillis);
        ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(timeoutInMillis);

        return restTemplate;
    }
}
