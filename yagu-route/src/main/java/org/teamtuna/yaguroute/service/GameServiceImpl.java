package org.teamtuna.yaguroute.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamtuna.yaguroute.aggregate.Game;
import org.teamtuna.yaguroute.dto.GameDTO;
import org.teamtuna.yaguroute.dto.GameDetailDTO;
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
    public List<GameDTO> getGamesByStadium(String stadium) {
        List<Game> games = gameRepository.findByStadium(stadium);
        return games.stream()
                .map(game -> modelMapper.map(game, GameDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public GameDetailDTO getGameDetailById(int gameId) {
        GameDetailDTO gameDetails = gameRepository.findGameDetailById(gameId);
        return gameDetails;
    }
}
