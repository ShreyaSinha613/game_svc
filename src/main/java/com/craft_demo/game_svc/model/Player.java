package com.craft_demo.game_svc.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@With
@Builder
@Document(collection = "player")
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @Id
    @TextIndexed
    private String id;
    private String name;
    private String location;
    private Integer age;
    private Long currentScore;
    private Long topScore;
    private Instant lastUpdatedAt;
    private Instant createdAt;
}
