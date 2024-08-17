package org.teamtuna.yaguroute.service;
import org.teamtuna.yaguroute.dto.*;


import java.sql.Date;
import java.util.List;

public interface GameService {
    List<GameDTO> getAllGames();
    List<GameDTO> getGamesByDate(Date date);
    List<GameDTO> getGamesByTeam(int teamId);
    GameDTO getGameById(int gameId);
    List<GameStadiumDTO> getGamesByStadium(int stadiumId);
    GameDetailDTO getGameDetailsByGameSeatId(int gameSeatId);
    List<GameSummaryDTO> getAllGamesWithSummary();
    GameTeamDTO getTeamsByGameId(int gameId);
}
