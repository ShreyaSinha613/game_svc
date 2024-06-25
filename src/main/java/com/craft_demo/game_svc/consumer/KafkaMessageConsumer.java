package com.craft_demo.game_svc.consumer;

import com.craft_demo.game_svc.constants.Constants;
import com.craft_demo.game_svc.model.Player;
import com.craft_demo.game_svc.service.ScoreIngestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageConsumer {
    Logger logger = LoggerFactory.getLogger(KafkaMessageConsumer.class);

    @Autowired
    private ScoreIngestionService scoreIngestionService;

    @KafkaListener(topics = Constants.publishedTopic, groupId = Constants.groupId)
    public void consumeMessage(Player player) {
        logger.info("Consumer consumed the message");
        try {
            scoreIngestionService.updateScore(player);
        } catch (Exception e) {
            logger.error("Failed to consume the message " + e.getMessage());
        }
    }
}
