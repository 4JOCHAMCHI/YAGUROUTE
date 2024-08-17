package org.teamtuna.yaguroute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.teamtuna.yaguroute.aggregate.Seat;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
    @Query("SELECT s FROM Seat s WHERE s.stadium.stadiumId = (" +
            "SELECT t.stadium.stadiumId FROM Team t WHERE t.teamId = (" +
            "SELECT g.homeTeam.teamId FROM Game g WHERE g.gameId = :gameId))")
    List<Seat> findAllSeatsByGameId(int gameId);
    List<Seat> findByStadium_StadiumId(int stadiumId);
}
