package org.teamtuna.yaguroute.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.jdbc.Sql;
import org.teamtuna.yaguroute.dto.GameDTO;
import org.teamtuna.yaguroute.dto.GameDetailDTO;
import org.teamtuna.yaguroute.dto.GameStadiumDTO;
import org.teamtuna.yaguroute.repository.GameRepository;
import org.teamtuna.yaguroute.repository.GameSeatRepository;
import org.teamtuna.yaguroute.repository.SeatRepository;
import org.teamtuna.yaguroute.repository.TeamRepository;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class GameServiceImplTest {

    @Autowired
    private GameServiceImpl gameService;


    @Test
    void testGetAllGames() {
        // When
        List<GameDTO> games = gameService.getAllGames();

        // Then
        assertNotNull(games);
        assertEquals(60, games.size()); //
    }

    @Test
    void testGetGamesByDate() {
        // Given
        java.sql.Date gameDate = java.sql.Date.valueOf("2024-08-07");

        // When
        List<GameDTO> games = gameService.getGamesByDate(gameDate);

        // Then
        assertNotNull(games);
        assertEquals(5, games.size());
    }

    @Test
    void testGetGamesByTeam() {
        // Given
        int teamId = 1;

        // When
        List<GameDTO> games = gameService.getGamesByTeam(teamId);

        // Then
        assertNotNull(games);
        assertEquals(12, games.size());
    }

    @Test
    void testGetGameById() {
        // Given
        int gameId = 1;

        // When
        GameDTO result = gameService.getGameById(gameId);

        // Then
        assertNotNull(result);
    }

    @Test
    void testGetGamesByStadium() {
        // Given
        int stadiumId = 1;

        // When
        List<GameStadiumDTO> games = gameService.getGamesByStadium(stadiumId);

        // Then
        assertNotNull(games);
        assertEquals(12, games.size());
    }

    @Test
    void testGetGameDetailsByGameSeatId() {
        // Given
        int gameSeatId = 1;

        // When
        GameDetailDTO gameDetailDTO = gameService.getGameDetailsByGameSeatId(gameSeatId);

        // Then
        assertNotNull(gameDetailDTO);
        System.out.print(gameDetailDTO);
        assertEquals("한화", gameDetailDTO.getHomeTeamName());
        assertEquals("롯데", gameDetailDTO.getAwayTeamName());
        assertEquals("이글스파크", gameDetailDTO.getStadiumName());
        assertEquals("대전", gameDetailDTO.getLocation());
        assertEquals("1", gameDetailDTO.getSeatNum());
    }
}
