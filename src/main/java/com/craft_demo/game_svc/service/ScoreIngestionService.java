package com.craft_demo.game_svc.service;

import com.craft_demo.game_svc.exception.CacheException;
import com.craft_demo.game_svc.model.Player;

public interface ScoreIngestionService {
    //Observer
    void updateScore(Player player) throws CacheException;
}
