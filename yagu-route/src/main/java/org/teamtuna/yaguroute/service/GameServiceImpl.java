package org.teamtuna.yaguroute.service;

import jakarta.transaction.Transactional;
import org.teamtuna.yaguroute.aggregate.Game;
import org.teamtuna.yaguroute.dto.GameDTO;
import org.teamtuna.yaguroute.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public List<GameDTO> getAllGames() {
        System.out.println("Invoking gameRepository.findAll()...");
        List<Game> gamesList = gameRepository.findAll();
        System.out.println("Total games fetched: " + gamesList.size());

        List<GameDTO> games = gamesList.stream().map(game -> {
            GameDTO gameDTO = new GameDTO(
                    game.getGameId(),
                    game.getGameDate(),
                    game.getGameTime(),
                    game.getHomeTeam().getTeamId(),
                    game.getAwayTeam().getTeamId()
            );
            System.out.println("GameDTO: " + gameDTO);
            return gameDTO;
        }).collect(Collectors.toList());

        games.forEach(System.out::println);
        return games;
    }

    @Override
    public List<GameDTO> getGamesByDate(Date date) {
        List<Game> gamesList = gameRepository.findByGameDate(date);
        return gamesList.stream().map(game -> new GameDTO(
                game.getGameId(),
                game.getGameDate(),
                game.getGameTime(),
                game.getHomeTeam().getTeamId(),
                game.getAwayTeam().getTeamId()
        )).collect(Collectors.toList());
    }

    @Override
    public List<GameDTO> getGamesByTeam(int teamId) {
        List<Game> gamesList = gameRepository.findByHomeTeam_TeamIdOrAwayTeam_TeamId(teamId, teamId);
        return gamesList.stream().map(game -> new GameDTO(
                game.getGameId(),
                game.getGameDate(),
                game.getGameTime(),
                game.getHomeTeam().getTeamId(),
                game.getAwayTeam().getTeamId()
        )).collect(Collectors.toList());
    }

    @Override
    public GameDTO getGameById(int gameId) {
        return gameRepository.findById(gameId).map(game ->
                new GameDTO(
                        game.getGameId(),
                        game.getGameDate(),
                        game.getGameTime(),
                        game.getHomeTeam().getTeamId(),
                        game.getAwayTeam().getTeamId()
                )
        ).orElse(null);
    }

    @Override
    public List<GameDTO> getGamesByStadium(String stadium) {
        List<Game> games = gameRepository.findByStadium(stadium);
        return games.stream().map(game -> new GameDTO(
                game.getGameId(),
                game.getGameDate(),
                game.getGameTime(),
                game.getHomeTeam().getTeamId(),
                game.getAwayTeam().getTeamId()
        )).collect(Collectors.toList());
    }


}
