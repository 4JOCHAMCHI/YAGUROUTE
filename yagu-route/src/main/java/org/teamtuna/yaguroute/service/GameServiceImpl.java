package org.teamtuna.yaguroute.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.teamtuna.yaguroute.aggregate.Game;
import org.teamtuna.yaguroute.aggregate.GameSeat;
import org.teamtuna.yaguroute.aggregate.Seat;
import org.teamtuna.yaguroute.aggregate.Sellable;
import org.teamtuna.yaguroute.dto.*;
import org.teamtuna.yaguroute.repository.GameRepository;
import org.teamtuna.yaguroute.repository.GameSeatRepository;
import org.teamtuna.yaguroute.repository.SeatRepository;
import org.teamtuna.yaguroute.repository.TicketRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameSeatRepository gameSeatRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ModelMapper modelMapper;

    private LocalDate lastDate;

    @Autowired
    private SeatRepository seatRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void onApplicationReady() {
        initializeGameSeats();
    }

    @Transactional
    public void initializeGameSeats() {
        generateGameSeatsForSellableGames();
    }

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
        GameSeat gameSeat = gameSeatRepository.findById(gameSeatId).orElseThrow(() -> new RuntimeException("GameSeat not found"));
        GameDetailDTO gameDetailDTO = modelMapper.map(gameSeat.getGame(), GameDetailDTO.class);
        gameDetailDTO.setHomeTeamName(gameSeat.getGame().getHomeTeam().getTeamName());
        gameDetailDTO.setAwayTeamName(gameSeat.getGame().getAwayTeam().getTeamName());
        gameDetailDTO.setStadiumName(gameSeat.getGame().getHomeTeam().getStadium().getStadiumName());
        gameDetailDTO.setLocation(gameSeat.getGame().getHomeTeam().getStadium().getLocation());
        gameDetailDTO.setSeatNum(String.valueOf(gameSeat.getSeat().getSeatNum()));
        return gameDetailDTO;
    }

    @Transactional
    public void generateGameSeatsForSellableGames() {
        LocalDate today = LocalDate.now();
        if (lastDate == null || !lastDate.equals(today)) {
            List<Game> sellableGames = gameRepository.findBySellable(Sellable.S);

            for (Game game : sellableGames) {
                int stadiumId = game.getHomeTeam().getStadium().getStadiumId();
                // Explicitly load home team and stadium to avoid LazyInitializationException
                game.getHomeTeam().getStadium().getStadiumName();
                List<Seat> seats = seatRepository.findByStadium_StadiumId(stadiumId);
                int seatcnt = 0;

                for (Seat seat : seats) {
                    if (seatcnt >= 200) break;
                    if (!gameSeatRepository.existsByGameAndSeat(game, seat)) {
                        GameSeat gameSeat = new GameSeat(false, seat.getStadium().getSeatPrice(), game, seat);
                        gameSeatRepository.save(gameSeat);
                        seatcnt++;
                    }
                }
            }
            lastDate = today;
        }
    }

    @Transactional
    @Scheduled(cron = "0 15 9 * * ?")
    public void removePastGamesAndSeats() {
        LocalDate today = LocalDate.now();
        List<Game> pastGames = gameRepository.findByGameDateBefore(today);

        for (Game game : pastGames) {
            List<GameSeat> gameSeats = gameSeatRepository.findByGame_GameId(game.getGameId());

            // game_seat_id를 참조하는 레코드 삭제
            for (GameSeat gameSeat : gameSeats) {
                ticketRepository.deleteByGameSeat(gameSeat);
            }

            // gameSeat 레코드 삭제
            gameSeatRepository.deleteByGame(game);

            // game 레코드 삭제
            gameRepository.delete(game);
        }
    }

    @Transactional
    public void updateGameSeatsForNewGame(List<Game> newGames){
        for(Game game : newGames) {
            if(!gameSeatRepository.existsByGame(game)){
                int stadiumId = game.getHomeTeam().getStadium().getStadiumId();
                List<Seat> seats = seatRepository.findByStadium_StadiumId(stadiumId);
                int seatCnt = 0;

                for(Seat seat : seats) {
                    if(seatCnt >= 200) break;
                    GameSeat gameSeat = new GameSeat(false, seat.getStadium().getSeatPrice(), game, seat);
                    gameSeatRepository.save(gameSeat);
                    seatCnt++;
                }
            }
        }
    }

    @Transactional
    @Scheduled(cron = "0 20 9 * * ?")
    public void updateNewGameSchedules() {
        List<Game> newGames = fetchNewGames();
        updateGameSeatsForNewGame(newGames);
    }

    private List<Game> fetchNewGames() {
        LocalDate today = LocalDate.now();
        return gameRepository.findByGameDateAfter(today);
    }

    @Override
    public List<GameSummaryDTO> getAllGamesWithSummary() {
        return gameRepository.findAllGamesWithSummary();
    }

    @Override
    @Transactional
    public GameTeamDTO getTeamsByGameId(int gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));
        GameTeamDTO gameTeamsDTO = new GameTeamDTO();
        gameTeamsDTO.setHomeTeamName(game.getHomeTeam().getTeamName());
        gameTeamsDTO.setAwayTeamName(game.getAwayTeam().getTeamName());
        return gameTeamsDTO;
    }
}
