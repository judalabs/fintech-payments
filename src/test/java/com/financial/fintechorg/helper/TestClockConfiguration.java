package com.financial.fintechorg.helper;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TestClockConfiguration {

    @Bean
    @Primary
    public Clock fixedClock() {
        Instant fixedInstant = Instant.parse("2023-08-21T12:00:00Z");
        return Clock.fixed(fixedInstant, ZoneId.systemDefault());
    }
}
