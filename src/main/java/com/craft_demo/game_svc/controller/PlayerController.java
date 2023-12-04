package com.craft_demo.game_svc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlayerController {
    @GetMapping("/")
    public String checkReadiness(){
        return "It's up and ready";
    }
}
