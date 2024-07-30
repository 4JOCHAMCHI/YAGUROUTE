package org.teamtuna.yaguroute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.teamtuna.yaguroute.aggregate.Game;
import org.teamtuna.yaguroute.dto.GameDetailDTO;

import java.util.Date;
import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer> {
    List<Game> findByGameDate(Date date);
    List<Game> findByHomeTeam_TeamIdOrAwayTeam_TeamId(int homeTeamId, int awayTeamId);

    @Query("SELECT g FROM Game g JOIN g.homeTeam t WHERE t.stadium = :stadium")
    List<Game> findByStadium(@Param("stadium") String stadium);

    @Query("SELECT new org.teamtuna.yaguroute.dto.GameDetailDTO(g.gameId, g.gameDate, g.gameTime, g.sellable, g.homeTeam.teamId, g.awayTeam.teamId, g.homeTeam.stadium, g.homeTeam.location) FROM Game g WHERE g.gameId = :gameId")
    GameDetailDTO findGameDetailById(@Param("gameId") int gameId);
}
