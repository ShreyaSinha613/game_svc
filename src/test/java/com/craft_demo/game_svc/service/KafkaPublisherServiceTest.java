package com.craft_demo.game_svc.service;

import com.craft_demo.game_svc.constants.Constants;
import com.craft_demo.game_svc.exception.EntityNotFoundException;
import com.craft_demo.game_svc.exception.KafkaPublishException;
import com.craft_demo.game_svc.model.Player;
import com.craft_demo.game_svc.service.serviceImpl.KafkaPublisherServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@DisplayName("Kafka Publisher Service")
public class KafkaPublisherServiceTest {
    @Mock
    private KafkaTemplate<String, Player> kafkaTemplate;

    @InjectMocks
    private KafkaPublisherServiceImpl kafkaPublisherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @SneakyThrows
    @Test
    void publishMessageToKafkaTopic_successfulPublish() {
        // Arrange
        Player player = new Player();
        CompletableFuture<SendResult<String, Player>> future = new CompletableFuture<>();
        future.complete(mock(SendResult.class));
        when(kafkaTemplate.send(anyString(), any(Player.class))).thenReturn(future);
        assertDoesNotThrow(() -> kafkaPublisherService.publishMessageToKafkaTopic(player));
        verify(kafkaTemplate, times(1)).send(anyString(), eq(player));
    }

}
