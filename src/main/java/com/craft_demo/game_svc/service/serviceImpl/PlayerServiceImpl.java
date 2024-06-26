package com.craft_demo.game_svc.service.serviceImpl;

import com.craft_demo.game_svc.exception.DatabaseOperationException;
import com.craft_demo.game_svc.exception.EntityNotFoundException;
import com.craft_demo.game_svc.exception.KafkaPublishException;
import com.craft_demo.game_svc.model.Player;
import com.craft_demo.game_svc.repository.PlayerRepository;
import com.craft_demo.game_svc.service.KafkaPublisherService;
import com.craft_demo.game_svc.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    KafkaPublisherService<Player> kafkaPublisher;

    /**
     * @param id
     * @return player with param id from player collection in database
     */
    public Player getPlayerById(String id) {
        Optional<Player> player = playerRepository.findById(id);
        if(player.isPresent()) {
            return player.get();
        } else {
            throw new EntityNotFoundException("Player with id " + id + " not found");
        }
    }

    /**
     * @return all the players
     */
    public  List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    /**
     * @param player
     * @return new player saved in the database
     */
    public Player createPlayer(Player player) throws Exception {
        if(Objects.isNull(player.getName())){
            throw new Exception("Player name is mandatory");
        }
        player.setCurrentScore(0L);
        player.setTopScore(0L);
        player.setCreatedAt(Instant.now());
        return playerRepository.save(player);
    }

    /**
     * @param player
     * update player details
     * @throws DatabaseOperationException
     */
    public void updatePlayer(Player player) throws DatabaseOperationException {
        try {
            player.setLastUpdatedAt(Instant.now());
            playerRepository.save(player);
        } catch(Exception e) {
            throw new DatabaseOperationException("Could not save the player details");
        }
    }

    /**
     * @param playerId
     * @param scoreDetails
     * current score of the player is updated
     * top score of player is also changed if the current score is greater than top score
     * publish the score to the kafka topic to update the leaderboard
     * @throws DatabaseOperationException
     */
    public void updateScore(String playerId, HashMap<String, Long> scoreDetails) throws DatabaseOperationException, KafkaPublishException {
        try {
            Player player = getPlayerById(playerId);
            player.setCurrentScore(scoreDetails.get("currentScore"));
            if (scoreDetails.get("currentScore") >= player.getTopScore()) {
                player.setTopScore(scoreDetails.get("currentScore"));
                player.setLastUpdatedAt(Instant.now());
            }
            kafkaPublisher.publishMessageToKafkaTopic(player);
        } catch (KafkaPublishException e) {
            throw new KafkaPublishException(e.getMessage());
        } catch (Exception e) {
            throw new DatabaseOperationException("Could not update the player score");
        }
    }

    public void updateScoreFromConsumer(Player playerFromConsumer) {
        Player player = getPlayerById(playerFromConsumer.getId());
        player.setCurrentScore(playerFromConsumer.getCurrentScore());
        player.setLastUpdatedAt(Instant.now());
        if (playerFromConsumer.getCurrentScore() >= player.getTopScore()) {
            player.setTopScore(playerFromConsumer.getCurrentScore());
        }
        player.setLastUpdatedAt(Instant.now());
        playerRepository.save(player);
    }
}
