package com.craft_demo.game_svc.model.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class BaseResponse extends ResponseEntity<Map<String, String>> {
    public BaseResponse(Map<String, String> body, HttpStatus status) {
        super(body, status);
    }
}
