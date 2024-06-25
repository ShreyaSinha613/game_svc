package com.craft_demo.game_svc.service;

import com.craft_demo.game_svc.mocks.MockObjects;
import com.craft_demo.game_svc.model.Player;
import com.craft_demo.game_svc.service.serviceImpl.CacheServiceImpl;
import com.craft_demo.game_svc.service.serviceImpl.PlayerServiceImpl;
import com.craft_demo.game_svc.service.serviceImpl.ScoreIngestionServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@DisplayName("Score Ingestion Service")
public class ScoreIngestionServiceTest {
    @Mock
    PlayerService playerService;

    @Mock
    CacheService<Player> cacheService;

    @InjectMocks
    ScoreIngestionServiceImpl scoreIngestionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @SneakyThrows
    @Test
    void updatePlayerScore() {
        doNothing().when(playerService).updateScoreFromConsumer(MockObjects.mockCreatePlayer(false));
        doNothing().when(cacheService).checkAndAddNewHighPlayerScore(MockObjects.mockCreatePlayer(false));
        scoreIngestionService.updateScore(MockObjects.mockCreatePlayer(false));
    }
}
