package org.teamtuna.yaguroute.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.teamtuna.yaguroute.YaguRouteApplication;
import org.teamtuna.yaguroute.aggregate.GameSeat;
import org.teamtuna.yaguroute.aggregate.Seat;
import org.teamtuna.yaguroute.dto.GameSeatDTO;
import org.teamtuna.yaguroute.dto.SeatDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = YaguRouteApplication.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GameSeatServiceTest {

    @Autowired
    private GameSeatService gameSeatService;

    @Test
    @DisplayName("GameSeat DTO에서 엔티티로 변환")
    void convertToGameSeat() {
        GameSeatDTO gameSeatDTO = new GameSeatDTO();
        gameSeatDTO.setGameSeatId(1);
        gameSeatDTO.setGameSeatPrice(10000);

        GameSeat gameSeat = gameSeatService.convertToGameSeat(gameSeatDTO);

        assertNotNull(gameSeat);
        assertEquals(1, gameSeat.getGameSeatId());
        assertEquals(10000, gameSeat.getGameSeatPrice());
    }

    @Test
    @DisplayName("Seat DTO에서 엔티티로 변환")
    void convertToSeat() {
        SeatDTO seatDTO = new SeatDTO();
        seatDTO.setSeatId(1);
        seatDTO.setSeatNum(1);

        Seat seat = gameSeatService.convertToSeat(seatDTO);

        assertNotNull(seat);
        assertEquals(1, seat.getSeatId());
        assertEquals(1, seat.getSeatNum());
    }

    @Test
    @DisplayName("Redis 좌석 점유 상태 조회")
    void isSeatOccupied() {
    }

    @Test
    @DisplayName("Redis 좌석 점유 상태 저장")
    void occupySeat() {
    }

    @Test
    @DisplayName("경기별 전체 좌석 조회")
    void getAllSeats() {
        List<GameSeatDTO> gameSeats = gameSeatService.getAllSeats(1);

        assertEquals(3, gameSeats.size());
    }

    @Test
    @DisplayName("좌석 상세 조회")
    void getSeatById() {
        SeatDTO seat = gameSeatService.getSeatById(1);

        assertEquals(1, seat.getSeatId());
        assertEquals(1, seat.getSeatNum());
    }

    @Test
    @DisplayName("경기 좌석 조회")
    void getGameSeatByGameIdAndSeatId() {
        GameSeatDTO gameSeat = gameSeatService.getGameSeatByGameIdAndSeatId(1, 1);

        assertEquals(1, gameSeat.getGameId());
        assertEquals(1, gameSeat.getSeatId());
    }

    @Test
    @DisplayName("예매 가능 좌석 조회")
    void getAvailableSeats() {
        List<GameSeatDTO> gameSeats = gameSeatService.getAvailableSeats(1);

        assertEquals(3, gameSeats.size());
    }
}