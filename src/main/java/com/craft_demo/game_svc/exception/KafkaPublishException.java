package com.craft_demo.game_svc.exception;

public class KafkaPublishException extends RuntimeException{
    public KafkaPublishException(String message) {
        super(message);
    }
}
