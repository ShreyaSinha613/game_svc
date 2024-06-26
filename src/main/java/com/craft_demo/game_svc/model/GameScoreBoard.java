package com.craft_demo.game_svc.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameScoreBoard {
    private String id;
    private Integer leaderBoardSize;
}
