package com.craft_demo.game_svc.service.serviceImpl;

import com.craft_demo.game_svc.exception.CacheException;
import com.craft_demo.game_svc.model.Player;
import com.craft_demo.game_svc.service.CacheService;
import com.craft_demo.game_svc.service.PlayerService;
import com.craft_demo.game_svc.service.ScoreIngestionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreIngestionServiceImpl implements ScoreIngestionService {
    @Autowired
    PlayerService playerService;

    @Autowired
    CacheService<Player> cacheService;

    @Override
    @Transactional
    public void updateScore(Player player) throws CacheException {
        playerService.updateScoreFromConsumer(player);
        cacheService.checkAndAddNewHighPlayerScore(player);
    }
}
