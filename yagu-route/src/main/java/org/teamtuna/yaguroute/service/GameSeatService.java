package org.teamtuna.yaguroute.service;

import org.teamtuna.yaguroute.aggregate.GameSeat;
import org.teamtuna.yaguroute.aggregate.Seat;
import org.teamtuna.yaguroute.dto.GameSeatDTO;
import org.teamtuna.yaguroute.dto.SeatDTO;

import java.util.List;

public interface GameSeatService {

    GameSeat convertToGameSeat(GameSeatDTO gameSeatDTO);
    Seat convertToSeat(SeatDTO seatDTO);
    boolean isSeatOccupied(int gameId, int seatId);
    GameSeatDTO occupySeat(int gameId, int seatId);
    List<GameSeatDTO> getAllSeats(int gameId);
    SeatDTO getSeatById(int seatId);
    GameSeatDTO getTicketByGameIdAndSeatId(int gameId, int seatId);
    List<GameSeatDTO> getAvailableSeats(int gameId);
}
