package org.teamtuna.yaguroute.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.teamtuna.yaguroute.dto.*;
import org.teamtuna.yaguroute.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/game")
@Tag(name = "경기일정", description = "경기일정 관련 API")
public class GameController {

    @Autowired
    private GameService gameService;

//    @Operation(summary = "경기일정 전체 조회", description = "모든 경기일정을 조회합니다.")
//    @GetMapping
//    public List<GameDTO> getAllGames() {
//        return gameService.getAllGames();
//    }

    @Operation(summary = "날짜별 경기일정 조회", description = "특정 날짜에 열리는 경기일정을 조회합니다.")
    @GetMapping("/date")
    public List<GameDTO> getGamesByDate(@RequestParam String date) {
        Date sqlDate = Date.valueOf(date);
        return gameService.getGamesByDate(sqlDate);
    }

    @Operation(summary = "팀별 경기일정 조회", description = "특정 팀의 경기일정을 조회합니다.")
    @GetMapping("/team")
    public List<GameDTO> getGamesByTeam(@RequestParam int teamId) {
        return gameService.getGamesByTeam(teamId);
    }

    @Operation(summary = "경기일정 상세조회", description = "특정 경기의 상세 정보를 조회합니다.")
    @GetMapping("/{gameId}")
    public GameDTO getGameById(@PathVariable int gameId) {
        return gameService.getGameById(gameId);
    }

    @Operation(summary = "구장별 경기일정 조회", description = "특정 구장에서 열리는 경기일정을 조회합니다.")
    @GetMapping("/stadium/{stadiumId}")
    public List<GameStadiumDTO> getGamesByStadium(@PathVariable int stadiumId) {
        return gameService.getGamesByStadium(stadiumId);
    }

    @Operation(summary = "메일발송용 내용 조회", description = "메일 발송에 필요한 경기 정보를 조회합니다.")
    @GetMapping("/details/{gameSeatId}")
    public GameDetailDTO getGameDetailsByGameSeatId(@PathVariable("gameSeatId") int gameSeatId) {
        return gameService.getGameDetailsByGameSeatId(gameSeatId);
    }

    @Operation(summary = "경기일정 전체 조회", description = "모든 경기일정을 조회합니다.")
    @GetMapping("/all")
    public List<GameSummaryDTO> getAllGames() {
        return gameService.getAllGamesWithSummary();
    }

    @Operation(summary = "팀 이름 조회", description = "특정 경기 ID를 통해 홈팀과 어웨이팀의 이름을 조회합니다.")
    @GetMapping("/teams/{gameId}")
    public GameTeamDTO getTeamsByGameId(@PathVariable int gameId) {
        return gameService.getTeamsByGameId(gameId);
    }
}
