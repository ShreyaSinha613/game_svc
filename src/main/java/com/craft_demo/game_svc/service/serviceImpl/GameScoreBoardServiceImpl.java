package com.craft_demo.game_svc.service.serviceImpl;

import com.craft_demo.game_svc.constants.Constants;
import com.craft_demo.game_svc.exception.ScoreBoardInitializationException;
import com.craft_demo.game_svc.model.GameScoreBoard;
import com.craft_demo.game_svc.model.Player;
import com.craft_demo.game_svc.service.CacheService;
import com.craft_demo.game_svc.service.GameScoreBoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GameScoreBoardServiceImpl implements GameScoreBoardService {
    Logger logger = LoggerFactory.getLogger(GameScoreBoardServiceImpl.class);

    @Autowired
    PlayerServiceImpl playerService;

    @Autowired
    CacheService<Player> cache;

    Boolean isScoreBoardInitialised = false;

    public void createNewGameScoreBoard(GameScoreBoard gameScoreBoard) throws ScoreBoardInitializationException {
        try{
            if(Objects.isNull(gameScoreBoard.getLeaderBoardSize())) {
                gameScoreBoard.setLeaderBoardSize(Constants.scoreBoardSize);
            }
            //for creating a cache, send all the player details
            List<Player> players = playerService.getAllPlayers();
            cache.getInitialScoreBoardData(gameScoreBoard.getLeaderBoardSize(), players);
            isScoreBoardInitialised = true;
        } catch (Exception e) {
            logger.error("Error in creating the score board");
            throw new ScoreBoardInitializationException("Failed to create score board");
        }
    }

    public List<Player> getTopScorersInGame() throws ScoreBoardInitializationException {
        if(!isScoreBoardInitialised) {
            logger.error("Score board is not yet initialised");
            throw new ScoreBoardInitializationException("Score board is not yet initialised");
        }
        return cache.getTopScorers();
    }
}
