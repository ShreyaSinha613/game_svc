package com.craft_demo.game_svc.service;

import com.craft_demo.game_svc.constants.Constants;
import com.craft_demo.game_svc.controller.PlayerController;
import com.craft_demo.game_svc.exception.ScoreBoardInitializationException;
import com.craft_demo.game_svc.model.GameScoreBoard;
import com.craft_demo.game_svc.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GameLeaderBoardService {
    Logger logger = LoggerFactory.getLogger(GameLeaderBoardService.class);

    @Autowired
    PlayerService playerService;

    @Autowired
    CacheService cacheService;

    Boolean isScoreBoardInitialised = false;

    public void createNewGameScoreBoard(GameScoreBoard gameScoreBoard) throws ScoreBoardInitializationException {
        try{
            if(Objects.isNull(gameScoreBoard.getLeaderBoardSize())) {
                gameScoreBoard.setLeaderBoardSize(Constants.scoreBoardSize);
            }
            //for creating a cache, send all the player details
            List<Player> players = playerService.getAllPlayers();
            cacheService.getInitialScoreBoardData(gameScoreBoard.getLeaderBoardSize(), players);
            isScoreBoardInitialised = true;
        } catch (Exception e) {
            logger.error("Error in creating the score board");
            throw new ScoreBoardInitializationException("Failed to create leader board");
        }
    }

    public List<Player> getTopScorersInGame() throws ScoreBoardInitializationException {
        if(!isScoreBoardInitialised) {
            logger.error("Score board is not yet initialised");
            throw new ScoreBoardInitializationException("Score board is not yet initialised");
        }
        return cacheService.getTopScorers();
    }
}
