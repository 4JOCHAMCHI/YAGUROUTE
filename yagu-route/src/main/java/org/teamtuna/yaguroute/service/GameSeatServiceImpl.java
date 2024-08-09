package org.teamtuna.yaguroute.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.teamtuna.yaguroute.aggregate.GameSeat;
import org.teamtuna.yaguroute.aggregate.Seat;
import org.teamtuna.yaguroute.dto.GameSeatDTO;
import org.teamtuna.yaguroute.dto.SeatDTO;
import org.teamtuna.yaguroute.repository.GameSeatRepository;
import org.teamtuna.yaguroute.repository.SeatRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameSeatServiceImpl implements GameSeatService{

    private final GameSeatRepository gameSeatRepository;
    private final SeatRepository seatRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private static final String SET_KEY = "seats:occupied";

    public boolean isSeatOccupied(int gameId, int seatId) {
        Boolean isOccupied = redisTemplate.opsForSet().isMember(SET_KEY.concat(String.valueOf(gameId)), seatId);

        // 캐시에 존재하지 않으면 DB 조회
        if (isOccupied == null || !isOccupied) {
            try {
                isOccupied = getGameSeatByGameIdAndSeatId(gameId, seatId).isOccupied();
            } catch (EntityNotFoundException e) {
                isOccupied = false;
            }
        }

        return isOccupied;
    }

    public GameSeatDTO occupySeat(int gameId, int seatId) {
        redisTemplate.opsForSet().add(SET_KEY.concat(String.valueOf(gameId)), seatId);

        GameSeat gameSeat = convertToGameSeat(getGameSeatByGameIdAndSeatId(gameId, seatId));
        gameSeat.setOccupied(true);

        return new GameSeatDTO(gameSeatRepository.save(gameSeat));
    }

    public GameSeat convertToGameSeat(GameSeatDTO gameSeatDTO) {
        return gameSeatRepository.findById(gameSeatDTO.getGameSeatId()).orElseThrow(() -> new EntityNotFoundException("GameSeat not found"));
    }

    public List<GameSeatDTO> getAllSeats(int gameId) {
        return gameSeatRepository.findByGame_GameId(gameId).stream().map(GameSeatDTO::new).collect(Collectors.toList());
    }

    public Seat convertToSeat(SeatDTO seatDTO) {
        return seatRepository.findById(seatDTO.getSeatId()).orElseThrow(() -> new EntityNotFoundException("Seat not found"));
    }

    public SeatDTO getSeatById(int seatId) {
        return new SeatDTO(seatRepository.findById(seatId).orElseThrow(() -> new EntityNotFoundException("Seat not found")));
    }

    public GameSeatDTO getGameSeatByGameIdAndSeatId(int gameId, int seatId) {
        return new GameSeatDTO(gameSeatRepository.findByGame_GameIdAndSeat_SeatId(gameId, seatId).orElseThrow(()-> new EntityNotFoundException("Ticket not found")));
    }

    public List<GameSeatDTO> getAvailableSeats(int gameId) {
        return gameSeatRepository.findByGame_GameIdAndOccupiedFalse(gameId).stream().map(GameSeatDTO::new).collect(Collectors.toList());
    }
}
