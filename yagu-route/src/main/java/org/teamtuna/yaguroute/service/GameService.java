package org.teamtuna.yaguroute.service;
import org.teamtuna.yaguroute.dto.GameDTO;
import org.teamtuna.yaguroute.dto.GameDetailDTO;
import org.teamtuna.yaguroute.dto.GameStadiumDTO;
import org.teamtuna.yaguroute.dto.GameSummaryDTO;


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
}
