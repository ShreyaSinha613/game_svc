package com.craft_demo.game_svc.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message, Exception originalException) {
        super(message, originalException);
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
