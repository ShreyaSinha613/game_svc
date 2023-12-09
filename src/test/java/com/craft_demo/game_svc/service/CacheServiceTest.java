package com.craft_demo.game_svc.service;

import com.craft_demo.game_svc.constants.Constants;
import com.craft_demo.game_svc.exception.CacheException;
import com.craft_demo.game_svc.exception.DatabaseOperationException;
import com.craft_demo.game_svc.mocks.MockObjects;
import com.craft_demo.game_svc.model.Player;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Cache Service")
public class CacheServiceTest {
    @InjectMocks
    CacheService cacheService;

    @SneakyThrows
    @Test
    void getInitialScoreBoardData() {
        List<Player> players = MockObjects.mockPlayerList();
        players.add(Player.builder().id("78908").name("Gill").location("India").currentScore(24L).topScore(26L).age(24).build());
        cacheService.getInitialScoreBoardData(Constants.scoreBoardSize, players);
        assertEquals(MockObjects.getReversedList(), cacheService.getTopScorers());

        cacheService.checkAndAddNewHighPlayerScore(MockObjects.mockNewScoredPlayer());
        List<Player> mockPlayers = MockObjects.getReversedList();
        mockPlayers.set(4, MockObjects.mockNewScoredPlayer());
        assertEquals(mockPlayers, cacheService.getTopScorers());

        Player newPlayer = Player.builder()
                .id("90345")
                .name("Sam")
                .age(25)
                .currentScore(36L)
                .topScore(80L)
                .location("USA")
                .build();
        cacheService.checkAndAddNewHighPlayerScore(newPlayer);
        assertEquals(MockObjects.addedPlayerToList(newPlayer), cacheService.getTopScorers());

        Throwable err = assertThrows(CacheException.class, ()->{
            cacheService.getInitialScoreBoardData(5, null);
        });
        assertEquals("Failed to initialise the board", err.getMessage());
    }
}
