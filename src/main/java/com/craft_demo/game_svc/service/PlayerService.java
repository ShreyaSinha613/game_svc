package com.craft_demo.game_svc.service;

import com.craft_demo.game_svc.exception.DatabaseOperationException;
import com.craft_demo.game_svc.exception.KafkaPublishException;
import com.craft_demo.game_svc.model.Player;

import java.util.HashMap;
import java.util.List;

public interface PlayerService {
    Player getPlayerById(String id);
    List<Player> getAllPlayers();
    Player createPlayer(Player player) throws Exception;
    void updatePlayer(Player player) throws DatabaseOperationException;
    void updateScore(String playerId, HashMap<String, Long> scoreDetails) throws DatabaseOperationException, KafkaPublishException;
    void updateScoreFromConsumer(Player playerFromConsumer);
}
