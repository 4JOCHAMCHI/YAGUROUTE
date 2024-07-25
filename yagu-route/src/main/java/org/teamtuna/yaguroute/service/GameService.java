package org.teamtuna.yaguroute.service;

import org.springframework.stereotype.Service;
import org.teamtuna.yaguroute.dto.GameDTO;

import java.sql.Date;
import java.util.List;

public interface GameService {
    List<GameDTO> getAllGames();
    List<GameDTO> getGamesByDate(Date date);
    List<GameDTO> getGamesByTeam(int teamId);
    GameDTO getGameById(int gameId);
    List<GameDTO> getGamesByStadium(String stadium);
}
