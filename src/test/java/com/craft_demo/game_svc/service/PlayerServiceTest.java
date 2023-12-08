package com.craft_demo.game_svc.service;


import com.craft_demo.game_svc.constants.Constants;
import com.craft_demo.game_svc.exception.EntityNotFoundException;
import com.craft_demo.game_svc.exception.KafkaPublishException;
import com.craft_demo.game_svc.exception.ScoreBoardInitializationException;
import com.craft_demo.game_svc.mocks.MockObjects;
import com.craft_demo.game_svc.model.Player;
import com.craft_demo.game_svc.repository.PlayerRepository;
import com.google.common.collect.ImmutableList;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@DisplayName("Player Service")
public class PlayerServiceTest{
    @Mock
    PlayerRepository playerRepository;

    @Mock
    KafkaPublisherService kafkaPublisherService;

    @InjectMocks
    PlayerService playerService;

    @SneakyThrows
    @Test
    void getPlayerById() {
        when(playerRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(MockObjects.mockCreatePlayer(false)));
        playerService.getPlayerById("12345");
        assertEquals("12345", MockObjects.mockCreatePlayer(false).getId());
    }

    @SneakyThrows
    @Test
    void getPlayerByIdThrowsError() {
        when(playerRepository.findById(Mockito.anyString())).thenThrow(new EntityNotFoundException("Player with id 12356 not found"));
        Throwable error = assertThrows(EntityNotFoundException.class, ()->
        playerService.getPlayerById("12356"));
        assertEquals("Player with id 12356 not found", error.getMessage());
    }

    @SneakyThrows
    @Test
    void getAllPlayers() {
        when(playerRepository.findAll()).thenReturn(ImmutableList.of(MockObjects.mockCreatePlayer(false)));
        List<Player> players = playerService.getAllPlayers();
        assertEquals(1, players.size());
    }

    @SneakyThrows
    @Test
    void createPlayer() {
        when(playerRepository.save(Mockito.any())).thenReturn(MockObjects.mockCreatePlayer(false));
        Player player = playerService.createPlayer(MockObjects.mockCreatePlayer(true));
        assertEquals("12345", player.getId());
    }

    @SneakyThrows
    @Test
    void updatePlayer() {
        when(playerRepository.save(Mockito.any())).thenReturn(MockObjects.mockUpdatePlayer());
        playerService.updatePlayer(MockObjects.mockUpdatePlayer());
    }

    @SneakyThrows
    @Test
    void updatePlayerScore() {
        when(playerRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(MockObjects.mockCreatePlayer(false)));
        HashMap<String, Long> score = new HashMap<>();
        score.put("currentScore", 67L);
        playerService.updateScore("12345", score);
        Player player = MockObjects.mockCreatePlayer(false);
        player.setTopScore(67L);
        player.setCurrentScore(67L);
        verify(kafkaPublisherService, times(1)).sendMessageToKafkaTopic(player);
    }

    @SneakyThrows
    @Test
    void updatePlayerScoreThrowsError() {
        HashMap<String, Long> score = new HashMap<>();
        score.put("currentScore", 67L);
        when(playerRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(MockObjects.mockCreatePlayer(false)));
        doThrow(new KafkaPublishException("Unable to send message to topic \"" + Constants.publishedTopic))
                .when(kafkaPublisherService).sendMessageToKafkaTopic(Mockito.any());
        Throwable er = assertThrows(KafkaPublishException.class, ()->{
            playerService.updateScore("12345", score);
        });
        assertEquals("Unable to send message to topic \"" + Constants.publishedTopic, er.getMessage());
    }
}
