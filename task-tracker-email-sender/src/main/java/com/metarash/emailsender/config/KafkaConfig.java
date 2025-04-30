package com.metarash.emailsender.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic emailSendingTopic(@Value("${kafka.topics.email-sending.name}") String name,
                                      @Value("${kafka.topics.email-sending.partitions}") int partitions,
                                      @Value("${kafka.topics.email-sending.replicas}") short replicas
    ) {
        return new NewTopic(name, partitions, replicas);
    }

    @Bean
    public NewTopic allTasksTopic(@Value("${kafka.topics.all-tasks.name}") String name,
                                  @Value("${kafka.topics.all-tasks.partitions}") int partitions,
                                  @Value("${kafka.topics.all-tasks.replicas}") short replicas
    ) {
        return new NewTopic(name, partitions, replicas);
    }

    @Bean
    public NewTopic unfinishedTasksTopic(@Value("${kafka.topics.unfinished-tasks.name}") String name,
                                         @Value("${kafka.topics.unfinished-tasks.partitions}") int partitions,
                                         @Value("${kafka.topics.unfinished-tasks.replicas}") short replicas
    ) {
        return new NewTopic(name, partitions, replicas);
    }

    @Bean
    public NewTopic finishedTasksTopic(@Value("${kafka.topics.finished-tasks.name}") String name,
                                       @Value("${kafka.topics.finished-tasks.partitions}") int partitions,
                                       @Value("${kafka.topics.finished-tasks.replicas}") short replicas
    ) {
        return new NewTopic(name, partitions, replicas);
    }
}