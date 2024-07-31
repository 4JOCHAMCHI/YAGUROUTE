package org.teamtuna.yaguroute.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamtuna.yaguroute.aggregate.Game;
import org.teamtuna.yaguroute.aggregate.GameSeat;
import org.teamtuna.yaguroute.dto.GameDTO;
import org.teamtuna.yaguroute.dto.GameDetailDTO;
import org.teamtuna.yaguroute.dto.GameStadiumDTO;
import org.teamtuna.yaguroute.repository.GameRepository;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<GameDTO> getAllGames() {
        List<Game> games = gameRepository.findAll();
        return games.stream()
                .map(game -> modelMapper.map(game, GameDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GameDTO> getGamesByDate(Date date) {
        List<Game> games = gameRepository.findByGameDate(date);
        return games.stream()
                .map(game -> modelMapper.map(game, GameDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GameDTO> getGamesByTeam(int teamId) {
        List<Game> games = gameRepository.findByHomeTeam_TeamIdOrAwayTeam_TeamId(teamId, teamId);
        return games.stream()
                .map(game -> modelMapper.map(game, GameDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public GameDTO getGameById(int gameId) {
        Game game = gameRepository.findById(gameId).orElse(null);
        return modelMapper.map(game, GameDTO.class);
    }

    @Override
    @Transactional
    public List<GameStadiumDTO> getGamesByStadium(int stadiumId) {
        return gameRepository.findByStadiumId(stadiumId).stream()
                .map(game -> {
                    GameStadiumDTO dto = modelMapper.map(game, GameStadiumDTO.class);
                    dto.setHomeTeamId(game.getHomeTeam().getTeamId());
                    dto.setHomeTeamName(game.getHomeTeam().getTeamName());
                    dto.setAwayTeamId(game.getAwayTeam().getTeamId());
                    dto.setAwayTeamName(game.getAwayTeam().getTeamName());
                    dto.setStadiumName(game.getHomeTeam().getStadium().getStadiumName());
                    dto.setStadiumLocation(game.getHomeTeam().getStadium().getLocation());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GameDetailDTO getGameDetailsByGameSeatId(int gameSeatId) {
        GameSeat gameSeat = gameRepository.findGameSeatDetailsById(gameSeatId);
        GameDetailDTO gameDetailDTO = modelMapper.map(gameSeat.getGame(), GameDetailDTO.class);
        gameDetailDTO.setHomeTeamName(gameSeat.getGame().getHomeTeam().getTeamName());
        gameDetailDTO.setAwayTeamName(gameSeat.getGame().getAwayTeam().getTeamName());
        gameDetailDTO.setStadiumName(gameSeat.getGame().getHomeTeam().getStadium().getStadiumName());
        gameDetailDTO.setLocation(gameSeat.getGame().getHomeTeam().getStadium().getLocation());
        gameDetailDTO.setSeatNum(String.valueOf(gameSeat.getSeat().getSeatNum()));
        return gameDetailDTO;
    }


}
