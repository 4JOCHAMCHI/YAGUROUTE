package org.teamtuna.yaguroute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.teamtuna.yaguroute.aggregate.Game;

import java.util.Date;
import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer> {
    List<Game> findByGameDate(Date date);
    List<Game> findByHomeTeam_TeamIdOrAwayTeam_TeamId(int homeTeamId, int awayTeamId);

    @Query("SELECT g FROM Game g JOIN g.homeTeam t WHERE t.stadium = :stadium")
    List<Game> findByStadium(@Param("stadium") String stadium);
}
