package com.craft_demo.game_svc.service.serviceImpl;

import com.craft_demo.game_svc.exception.CacheException;
import com.craft_demo.game_svc.model.Player;
import com.craft_demo.game_svc.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CacheServiceImpl implements CacheService<Player> {
    Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);

    PriorityQueue<Player> topPlayers;
    Map<String, Player> recordedPlayers;

    Integer assignedSize;
    public void getInitialScoreBoardData(Integer sizeOfBoard, List<Player> players) throws CacheException {
        try {
            // Create a min-heap with a size of sizeOfBoard and a custom comparator for player scores
            topPlayers = new PriorityQueue<Player>(sizeOfBoard, Comparator.comparingLong(Player::getTopScore));
            recordedPlayers = new HashMap<String, Player>();
            assignedSize = sizeOfBoard;
            // Iterate through all players
            for (Player player : players) {
                // Add player to the heap if it's not full or has a higher score than the minimum score in the heap
                if(topPlayers.size()<sizeOfBoard) {
                    topPlayers.add(player);
                    recordedPlayers.put(player.getId(), player);
                } else if (player.getTopScore() > topPlayers.peek().getTopScore()) {
                    topPlayers.offer(player);
                    if (topPlayers.size() > sizeOfBoard) {
                        Player lowScore = topPlayers.poll(); // Remove the player with the lowest score
                        recordedPlayers.remove(lowScore.getId());
                        recordedPlayers.put(player.getId(), player);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Failed to initialise the score board");
            throw new CacheException("Failed to initialise the board");
        }
    }

    public List<Player> getTopScorers() {
        List<Player> scores = new ArrayList<>(topPlayers);
        scores.sort(Comparator.comparing(Player::getTopScore));
        Collections.reverse(scores);
        return scores;
    }

    public void checkAndAddNewHighPlayerScore(Player player) throws CacheException {
        try{
            if(recordedPlayers.containsKey(player.getId())
                    && recordedPlayers.get(player.getId()).getTopScore()<player.getTopScore()) {
                Player existingRecord = recordedPlayers.get(player.getId());
                topPlayers.remove(existingRecord);
                recordedPlayers.remove(player.getId());
                topPlayers.offer(player);
                recordedPlayers.put(player.getId(), player);
                return;
            }
            if(topPlayers.size()<assignedSize){
                topPlayers.add(player);
                recordedPlayers.put(player.getId(), player);
            } else if(!topPlayers.isEmpty() && topPlayers.peek().getTopScore()<player.getTopScore()) {
                Player lowScored = topPlayers.poll();
                recordedPlayers.remove(lowScored.getId());
                recordedPlayers.put(player.getId(), player);
                topPlayers.remove(lowScored);
                topPlayers.add(player);
            }
        } catch (Exception e) {
            logger.error("Failed to check the player's updated score");
            throw new CacheException("Failed to check the player's updated score");
        }
    }
}
