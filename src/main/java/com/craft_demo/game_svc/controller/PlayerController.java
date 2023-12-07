package com.craft_demo.game_svc.controller;

import com.craft_demo.game_svc.model.Player;
import com.craft_demo.game_svc.model.response.BaseResponse;
import com.craft_demo.game_svc.service.PlayerService;
import com.craft_demo.game_svc.utils.RestUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerController {
    Logger logger = LoggerFactory.getLogger(PlayerController.class);

    @Autowired
    PlayerService playerService;

    @GetMapping("/probes")
    public String checkReadiness(){
        return "It's up and ready";
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPlayer(@RequestBody Player player) {
        Player newPlayer = playerService.createPlayer(player);
        logger.info("New player created with id " + newPlayer.getId());
        return RestUtils.createCreateBaseResponse(newPlayer.getId());
    }

    @PutMapping("/{id}/update")
    public BaseResponse updatePlayerDetails(@RequestBody Player player) {
        try {
            playerService.updatePlayer(player);
            return RestUtils.createSuccessBaseResponse();
        } catch (Exception e) {
            logger.error("Failed to save the player details " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PatchMapping("/{id}/update-current-score")
    public BaseResponse updateCurrentScore (@PathVariable("id") String id, @RequestBody HashMap<String, Long> score) {
        try {
            //publish the score to the topic or in a flat file
            playerService.updateScore(id, score);
            return RestUtils.createSuccessBaseResponse();
        } catch(Exception e) {
            logger.error("Failed to save the current score of player " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
