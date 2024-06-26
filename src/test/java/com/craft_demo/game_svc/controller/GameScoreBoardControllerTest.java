package com.craft_demo.game_svc.controller;

import com.craft_demo.game_svc.exception.ScoreBoardInitializationException;
import com.craft_demo.game_svc.mocks.MockObjects;
import com.craft_demo.game_svc.service.serviceImpl.GameScoreBoardServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GameScoreBoardControllerTest {
    @Mock
    GameScoreBoardServiceImpl gameLeaderBoardService;

    @InjectMocks
    GameScoreBoardController gameScoreBoardController;

    private MockHttpServletRequest mockRequest;

    @SneakyThrows
    @Test
    void createGameScoreBoard() {
        mockRequest = new MockHttpServletRequest();
        mockRequest.setContextPath(null);
        ServletRequestAttributes attributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attributes);

        MockitoAnnotations.initMocks(this);
        doNothing().when(gameLeaderBoardService).createNewGameScoreBoard(Mockito.any());
        ResponseEntity<?> baseResponse =  gameScoreBoardController.createGameScoreBoard(MockObjects.mockGameScoreBoard());
        assertEquals("200 OK", baseResponse.getStatusCode().toString());
    }

    @SneakyThrows
    @Test
    void createGameScoreBoardThrowsError() {
        mockRequest = new MockHttpServletRequest();
        mockRequest.setContextPath(null);
        ServletRequestAttributes attributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attributes);

        MockitoAnnotations.initMocks(this);
        doThrow(new ScoreBoardInitializationException("Failed to create score board")).when(gameLeaderBoardService).createNewGameScoreBoard(Mockito.any());
        Throwable err = assertThrows(ResponseStatusException.class, ()->{
            gameScoreBoardController.createGameScoreBoard(MockObjects.mockGameScoreBoard());
        });
        assertEquals("500 INTERNAL_SERVER_ERROR \"Failed to create score board\"", err.getMessage());
    }

    @SneakyThrows
    @Test
    void getTopScorersList() {
        MockitoAnnotations.initMocks(this);
        when(gameLeaderBoardService.getTopScorersInGame()).thenReturn(MockObjects.mockPlayerList());
        ResponseEntity<?> response = gameScoreBoardController.getTopScorers();
        assertEquals("200 OK", response.getStatusCode().toString());
    }

    @SneakyThrows
    @Test
    void getTopScorersListThrowsError() {
        MockitoAnnotations.initMocks(this);
        doThrow(new ScoreBoardInitializationException("Score board is not yet initialised")).when(gameLeaderBoardService).getTopScorersInGame();
        ResponseEntity<?> response = gameScoreBoardController.getTopScorers();
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
