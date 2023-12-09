package com.craft_demo.game_svc.mocks;

import com.craft_demo.game_svc.model.GameScoreBoard;
import com.craft_demo.game_svc.model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MockObjects {
    public static Player mockCreatePlayer(boolean isRequestBody){
        Player player = Player.builder()
                .name("Arthur")
                .age(23)
                .location("USA")
                .currentScore(24L)
                .topScore(24L)
                .build();
        if(isRequestBody){
            return player;
        }
        player.setId("12345");
        return player;
    }

    public static Player mockUpdatePlayer(){
        return Player.builder()
                .name("Arthur")
                .age(24)
                .location("USA")
                .id("12340")
                .currentScore(24L)
                .topScore(24L)
                .build();
    }

    public static Player mockNewScoredPlayer(){
        return Player.builder()
                .name("Gill")
                .age(24)
                .location("India")
                .id("78908")
                .topScore(27L)
                .currentScore(23L)
                .build();
    }

    public static GameScoreBoard mockGameScoreBoard(){
        return GameScoreBoard.builder().leaderBoardSize(null).leaderBoardName(null).id(null).build();
    }

    public static List<Player> mockPlayerList() {
        Player player1 = Player.builder()
                .id("12345")
                .name("Arthur")
                .age(23)
                .location("USA")
                .currentScore(23L)
                .topScore(25L)
                .build();
        Player player2 = Player.builder()
                .id("12346")
                .name("Max")
                .age(23)
                .location("USA")
                .currentScore(25L)
                .topScore(28L)
                .build();
        Player player3 = Player.builder()
                .id("12347")
                .name("Matt")
                .age(23)
                .location("USA")
                .currentScore(20L)
                .topScore(39L)
                .build();
        Player player4 = Player.builder()
                .id("12348")
                .name("George")
                .age(23)
                .location("USA")
                .currentScore(33L)
                .topScore(59L)
                .build();
        Player player5 = Player.builder()
                .id("12349")
                .name("Hannah")
                .age(23)
                .location("USA")
                .currentScore(50L)
                .topScore(64L)
                .build();
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);
        return players;
    }

    public static List<Player> getReversedList(){
        List<Player> players = mockPlayerList();
        Player player = Player.builder()
                .id("78908")
                .name("Gill")
                .location("India")
                .currentScore(24L)
                .topScore(26L)
                .age(24)
                .build();
        players.set(0, player);
        Collections.reverse(players);
        return players;
    }

    public static List<Player> addedPlayerToList(Player player1){
        Player player2 = Player.builder()
                .id("12346")
                .name("Max")
                .age(23)
                .location("USA")
                .currentScore(25L)
                .topScore(28L)
                .build();
        Player player3 = Player.builder()
                .id("12347")
                .name("Matt")
                .age(23)
                .location("USA")
                .currentScore(20L)
                .topScore(39L)
                .build();
        Player player4 = Player.builder()
                .id("12348")
                .name("George")
                .age(23)
                .location("USA")
                .currentScore(33L)
                .topScore(59L)
                .build();
        Player player5 = Player.builder()
                .id("12349")
                .name("Hannah")
                .age(23)
                .location("USA")
                .currentScore(50L)
                .topScore(64L)
                .build();
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player5);
        players.add(player4);
        players.add(player3);
        players.add(player2);
        return players;
    }
}
