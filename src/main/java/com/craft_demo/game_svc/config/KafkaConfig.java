package com.craft_demo.game_svc.config;

import com.craft_demo.game_svc.constants.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic topic() {
        return new NewTopic(Constants.publishedTopic, 1, (short) 1);
    }
}
