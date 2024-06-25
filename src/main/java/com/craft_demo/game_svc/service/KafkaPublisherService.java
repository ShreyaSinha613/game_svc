package com.craft_demo.game_svc.service;

import com.craft_demo.game_svc.exception.KafkaPublishException;

public interface KafkaPublisherService<T> {
    public void publishMessageToKafkaTopic(T message) throws KafkaPublishException;
}
