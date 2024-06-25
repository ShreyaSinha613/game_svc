package com.craft_demo.game_svc.controller;

import com.craft_demo.game_svc.exception.ScoreBoardInitializationException;
import com.craft_demo.game_svc.model.GameScoreBoard;
import com.craft_demo.game_svc.model.Player;
import com.craft_demo.game_svc.service.serviceImpl.GameScoreBoardServiceImpl;
import com.craft_demo.game_svc.utils.RestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/score-board")
public class GameScoreBoardController {
    Logger logger = LoggerFactory.getLogger(GameScoreBoardController.class);

    @Autowired
    GameScoreBoardServiceImpl gameLeaderBoardService;

    @PostMapping("/create")
    public ResponseEntity<?> createGameScoreBoard(@RequestBody GameScoreBoard gameScoreBoard) {
        try {
            gameLeaderBoardService.createNewGameScoreBoard(gameScoreBoard);
            return RestUtils.createSuccessBaseResponse();
        } catch(Exception e) {
            logger.error("Error in creating score-board for the game");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/top-scorers")
    public ResponseEntity<List<Player>> getTopScorers() {
        try {
            List<Player> topScorers = gameLeaderBoardService.getTopScorersInGame();
            return ResponseEntity.ok(topScorers);
        } catch (ScoreBoardInitializationException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No score board is present, please create one");
        } catch (Exception e) {
            logger.error("Couldn't fetch top scores - " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
