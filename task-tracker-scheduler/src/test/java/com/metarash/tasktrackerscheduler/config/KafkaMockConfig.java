package com.metarash.tasktrackerscheduler.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.mock;

@TestConfiguration
@Profile("ci")
public class KafkaMockConfig {
    @Bean
    public KafkaTemplate<?, ?> kafkaTemplate() {
        return mock(KafkaTemplate.class);
    }
}
