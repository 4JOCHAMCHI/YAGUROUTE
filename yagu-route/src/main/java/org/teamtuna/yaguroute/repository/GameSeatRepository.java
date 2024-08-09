package org.teamtuna.yaguroute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teamtuna.yaguroute.aggregate.Game;
import org.teamtuna.yaguroute.aggregate.GameSeat;
import org.teamtuna.yaguroute.aggregate.Seat;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameSeatRepository extends JpaRepository<GameSeat, Integer> {
    List<GameSeat> findByGame_GameId(int gameId);
    List<GameSeat> findByGame_GameIdAndOccupiedFalse(int gameId);
    Optional<GameSeat> findByGame_GameIdAndSeat_SeatId(int gameId, int seatId);
    boolean existsByGame(Game game);
    void deleteByGame(Game game);
    boolean existsByGameAndSeat(Game game, Seat seat);

}
