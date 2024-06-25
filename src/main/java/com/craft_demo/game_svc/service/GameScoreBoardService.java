package com.craft_demo.game_svc.service;

import com.craft_demo.game_svc.exception.ScoreBoardInitializationException;
import com.craft_demo.game_svc.model.GameScoreBoard;
import com.craft_demo.game_svc.model.Player;

import java.util.List;

public interface GameScoreBoardService {
    void createNewGameScoreBoard(GameScoreBoard gameScoreBoard) throws ScoreBoardInitializationException;

    List<Player> getTopScorersInGame() throws ScoreBoardInitializationException;
}
