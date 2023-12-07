package com.craft_demo.game_svc.controller;

import com.craft_demo.game_svc.exception.DatabaseOperationException;
import com.craft_demo.game_svc.mocks.MockObjects;
import com.craft_demo.game_svc.model.response.BaseResponse;
import com.craft_demo.game_svc.service.PlayerService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PlayerControllerTest {
    @Mock
    PlayerService playerService;

    @InjectMocks
    PlayerController playerController;

    private MockHttpServletRequest mockRequest;

    @SneakyThrows
    @Test
    void checkReadiness() {
        MockitoAnnotations.initMocks(this);
        String response = playerController.checkReadiness();
        assertEquals("It's up and ready", response);
    }

    @SneakyThrows
    @Test
    void createPlayer() {
        mockRequest = new MockHttpServletRequest();
        mockRequest.setContextPath(null);
        ServletRequestAttributes attributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attributes);

        MockitoAnnotations.initMocks(this);
        when(playerService.createPlayer(Mockito.any())).thenReturn(MockObjects.mockCreatePlayer(false));
        ResponseEntity<?> response = playerController.createPlayer(MockObjects.mockCreatePlayer(true));
        assertEquals( "201 CREATED", response.getStatusCode().toString());
    }

    @SneakyThrows
    @Test
    void updatePlayer() {
        MockitoAnnotations.initMocks(this);
        doNothing().doThrow(new DatabaseOperationException("Could not save the player details"))
                .when(playerService).updatePlayer(Mockito.any());
        BaseResponse response = playerController.updatePlayerDetails(MockObjects.mockCreatePlayer(false));
        assertEquals("200 OK", response.getStatusCode().toString());
    }

    @SneakyThrows
    @Test
    void updatePlayerScore() {
        MockitoAnnotations.initMocks(this);
        HashMap<String, Long> hashMap = new HashMap<>();
        hashMap.put("Arthur", 1234L);
        doNothing().doThrow(new DatabaseOperationException("Could not save the player score"))
                .when(playerService).updateScore(Mockito.any(),Mockito.any());
        BaseResponse response = playerController.updateCurrentScore("12345", hashMap);
        assertEquals("200 OK", response.getStatusCode().toString());
    }
}