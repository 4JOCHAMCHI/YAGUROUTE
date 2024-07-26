package org.teamtuna.yaguroute.controller;

import org.teamtuna.yaguroute.dto.GameDTO;
import org.teamtuna.yaguroute.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public List<GameDTO> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/date/{date}")
    public List<GameDTO> getGamesByDate(@PathVariable String date) {
        Date sqlDate = Date.valueOf(date); // Convert String to java.sql.Date
        return gameService.getGamesByDate(sqlDate);
    }

    @GetMapping("/team/{teamId}")
    public List<GameDTO> getGamesByTeam(@PathVariable int teamId) {
        return gameService.getGamesByTeam(teamId);
    }

    @GetMapping("/{gameId}")
    public GameDTO getGameById(@PathVariable int gameId) {
        return gameService.getGameById(gameId);
    }

    @GetMapping("/stadium/{stadium}")
    public List<GameDTO> getGamesByStadium(@PathVariable String stadium) {
        return gameService.getGamesByStadium(stadium);
    }
}
