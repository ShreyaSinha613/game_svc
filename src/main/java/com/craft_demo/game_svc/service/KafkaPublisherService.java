package com.craft_demo.game_svc.service;

import com.craft_demo.game_svc.constants.Constants;
import com.craft_demo.game_svc.controller.PlayerController;
import com.craft_demo.game_svc.exception.KafkaPublishException;
import com.craft_demo.game_svc.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

@Service
public class KafkaPublisherService {

    Logger logger = LoggerFactory.getLogger(KafkaPublisherService.class);
    @Autowired
    private KafkaTemplate<String, Player> kafkaTemplate;

    public void sendMessageToKafkaTopic (Player player) {
        CompletableFuture<SendResult<String, Player>> future = kafkaTemplate.send(Constants.publishedTopic, player);
        future.whenComplete((res, ex)->{
            if(ex==null){
                logger.info("Message published successfully to topic " + Constants.publishedTopic);
            } else {
                logger.error("Unable to send message to topic " + Constants.publishedTopic);
                throw new KafkaPublishException("Unable to send message to topic " + Constants.publishedTopic);
            }
        });
    }
}
