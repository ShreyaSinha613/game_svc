package com.craft_demo.game_svc.service;

import com.craft_demo.game_svc.exception.CacheException;
import com.craft_demo.game_svc.exception.DatabaseOperationException;
import com.craft_demo.game_svc.exception.ScoreBoardInitializationException;
import com.craft_demo.game_svc.mocks.MockObjects;
import com.craft_demo.game_svc.service.serviceImpl.CacheServiceImpl;
import com.craft_demo.game_svc.service.serviceImpl.GameScoreBoardServiceImpl;
import com.craft_demo.game_svc.service.serviceImpl.PlayerServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@DisplayName("Game Score Board Service")
public class GameScoreBoardServiceTest {
    @Mock
    CacheServiceImpl cacheService;

    @Mock
    PlayerServiceImpl playerService;

    @InjectMocks
    GameScoreBoardServiceImpl gameScoreBoardService;

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @SneakyThrows
    @Test
    void getTopScorersError() {
        setPrivateField(gameScoreBoardService, "isScoreBoardInitialised", false);
        Throwable err = assertThrows(ScoreBoardInitializationException.class, ()->{
            gameScoreBoardService.getTopScorersInGame();
        });
        assertEquals("Score board is not yet initialised", err.getMessage());
    }

    @SneakyThrows
    @Test
    void createGameScoreBoard() {
        when(playerService.getAllPlayers()).thenReturn(MockObjects.mockPlayerList());
        gameScoreBoardService.createNewGameScoreBoard(MockObjects.mockGameScoreBoard());
    }

    @SneakyThrows
    @Test
    void createGameScoreBoardThrowsError() {
        doThrow(new CacheException("Failed to create score board"))
                .when(cacheService).getInitialScoreBoardData(Mockito.any(),Mockito.any());
        Throwable er = assertThrows(ScoreBoardInitializationException.class, ()->{
            gameScoreBoardService.createNewGameScoreBoard(MockObjects.mockGameScoreBoard());
        });
        assertEquals("Failed to create score board", er.getMessage());
    }

    @SneakyThrows
    @Test
    void getTopScorers() {
        when(playerService.getAllPlayers()).thenReturn(MockObjects.mockPlayerList());
        gameScoreBoardService.createNewGameScoreBoard(MockObjects.mockGameScoreBoard());
        gameScoreBoardService.getTopScorersInGame();
    }
}
