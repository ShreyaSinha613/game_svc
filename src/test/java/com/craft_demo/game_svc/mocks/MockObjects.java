package com.craft_demo.game_svc.mocks;

import com.craft_demo.game_svc.model.Player;

public class MockObjects {
    public static Player mockCreatePlayer(boolean isRequestBody){
        Player player = Player.builder()
                .name("Arthur")
                .age(23)
                .location("USA")
                .build();
        if(isRequestBody){
            return player;
        }
        player.setId("12345");
        return player;
    }
}
