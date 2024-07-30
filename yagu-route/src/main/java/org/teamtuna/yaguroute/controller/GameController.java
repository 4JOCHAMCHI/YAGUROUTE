package org.teamtuna.yaguroute.controller;

import org.teamtuna.yaguroute.dto.GameDTO;
import org.teamtuna.yaguroute.dto.GameDetailDTO;
import org.teamtuna.yaguroute.dto.GameStadiumDTO;
import org.teamtuna.yaguroute.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public List<GameDTO> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/date")
    public List<GameDTO> getGamesByDate(@RequestParam String date) {
        Date sqlDate = Date.valueOf(date);
        return gameService.getGamesByDate(sqlDate);
    }

    @GetMapping("/team")
    public List<GameDTO> getGamesByTeam(@RequestParam int teamId) {
        return gameService.getGamesByTeam(teamId);
    }
    @GetMapping("/{gameId}")
    public GameDTO getGameById(@PathVariable int gameId) {
        return gameService.getGameById(gameId);
    }

    @GetMapping("/stadium/{stadiumId}")
    public List<GameStadiumDTO> getGamesByStadium(@PathVariable int stadiumId) {
        return gameService.getGamesByStadium(stadiumId);
    }

    @GetMapping("/details/{gameSeatId}")
    public GameDetailDTO getGameDetailsByGameSeatId(@PathVariable("gameSeatId") int gameSeatId) {
        return gameService.getGameDetailsByGameSeatId(gameSeatId);
    }
}
