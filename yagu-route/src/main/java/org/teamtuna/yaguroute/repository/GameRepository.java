package org.teamtuna.yaguroute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.teamtuna.yaguroute.aggregate.Game;
import org.teamtuna.yaguroute.aggregate.GameSeat;
import org.teamtuna.yaguroute.dto.GameDetailDTO;

import java.util.Date;
import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer> {
    List<Game> findByGameDate(Date date);
    List<Game> findByHomeTeam_TeamIdOrAwayTeam_TeamId(int homeTeamId, int awayTeamId);

    @Query("SELECT g FROM Game g WHERE g.homeTeam.stadium.stadiumId = :stadiumId OR g.awayTeam.stadium.stadiumId = :stadiumId")
    List<Game> findByStadiumId(@Param("stadiumId") int stadiumId);

    @Query("SELECT gs FROM GameSeat gs " +
            "JOIN FETCH gs.game g " +
            "JOIN FETCH g.homeTeam ht " +
            "JOIN FETCH g.awayTeam at " +
            "JOIN FETCH ht.stadium s " +
            "JOIN FETCH gs.seat se " +
            "WHERE gs.gameSeatId = :gameSeatId")
    GameSeat findGameSeatDetailsById(@Param("gameSeatId") int gameSeatId);

}


