package com.craft_demo.game_svc.utils;

import com.craft_demo.game_svc.model.response.BaseResponse;
import com.google.common.collect.ImmutableMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class RestUtils {
    public static ResponseEntity<Object> createCreateBaseResponse (String id) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(ImmutableMap.of("id", id)).toUri();
        return ResponseEntity.created(location).body(ImmutableMap.of("status", "success", "id", id));
    }

    public static BaseResponse createSuccessBaseResponse () {
        return new BaseResponse(ImmutableMap.of("status", String.valueOf(HttpStatus.OK.value())), HttpStatus.OK);
    }
}
