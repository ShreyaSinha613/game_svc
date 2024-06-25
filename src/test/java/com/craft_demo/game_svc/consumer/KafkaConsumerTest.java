package com.craft_demo.game_svc.consumer;

import com.craft_demo.game_svc.exception.CacheException;
import com.craft_demo.game_svc.mocks.MockObjects;
import com.craft_demo.game_svc.service.ScoreIngestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Kafka Consumer Service")
public class KafkaConsumerTest {
    @Mock
    private ScoreIngestionService scoreIngestionService;

    @InjectMocks
    KafkaMessageConsumer kafkaMessageConsumer;

    @Test
    void consumeMessageTest() throws CacheException {
        kafkaMessageConsumer.consumeMessage(MockObjects.mockUpdatePlayer());
        verify(scoreIngestionService, times(1)).updateScore(MockObjects.mockUpdatePlayer());
    }

    @Test
    void consumeMessageError() throws CacheException {
        doThrow(new RuntimeException("Update failed")).when(scoreIngestionService).updateScore(MockObjects.mockUpdatePlayer());
        kafkaMessageConsumer.consumeMessage(MockObjects.mockUpdatePlayer());
        verify(scoreIngestionService, times(1)).updateScore(MockObjects.mockUpdatePlayer());
    }
}
