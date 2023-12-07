package com.craft_demo.game_svc.service;


import com.craft_demo.game_svc.repository.PlayerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Player Service")
public class PlayerServiceTest {
    @Mock
    PlayerRepository playerRepository;

    @InjectMocks
    PlayerService playerService;


}
