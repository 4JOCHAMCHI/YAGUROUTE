package org.teamtuna.yaguroute.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.teamtuna.yaguroute.aggregate.Ticket;
import org.teamtuna.yaguroute.dto.TicketDTO;
import org.teamtuna.yaguroute.repository.TicketRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private static final String SET_KEY = "seats:occupied";

    public boolean isSeatOccupied(int gameId, int seatNumber) {
        Boolean isOccupied = redisTemplate.opsForSet().isMember(SET_KEY.concat(String.valueOf(gameId)), seatNumber);

        // 캐시에 존재하지 않으면 DB 조회
        if (isOccupied == null || !isOccupied) {
            isOccupied = getTicketByGameIdAndSeatNumber(gameId, seatNumber).isSold();
            System.out.println("DB 조회: " + isOccupied);
        }

        System.out.println("캐시 조회:" + isOccupied);

        return isOccupied;
    }

    public boolean occupySeat(int gameId, int seatNumber, int ticketId) {
        redisTemplate.opsForSet().add(SET_KEY.concat(String.valueOf(gameId)), seatNumber);
        System.out.println("캐시에 저장");

        Ticket ticket = convertToTicket(getTicketById(ticketId));
        ticket.setSold(true);

        ticketRepository.save(ticket);

        return true;
    }

    public Ticket convertToTicket(TicketDTO ticketDTO) {
        return ticketRepository.findById(ticketDTO.getTicketId()).orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
    }

    public List<TicketDTO> getAllSeats(int gameId) {
        return ticketRepository.findByGame_GameId(gameId).stream().map(TicketDTO::new).collect(Collectors.toList());
    }

    public TicketDTO getTicketById(int ticketId) {
        // Redis에 조회된 좌석 정보 저장
        return new TicketDTO(ticketRepository.findById(ticketId).orElseThrow(() -> new EntityNotFoundException("Seat not found")));
    }

    public TicketDTO getTicketByGameIdAndSeatNumber(int gameId, int seatNumber) {
        return new TicketDTO(ticketRepository.findByGame_GameIdAndSeatNum(gameId, seatNumber).orElseThrow(()-> new EntityNotFoundException("Ticket not found")));
    }

    public List<TicketDTO> getAvailableSeats(int gameId) {
        return ticketRepository.findByGame_GameIdAndIsSoldFalse(gameId).stream().map(TicketDTO::new).collect(Collectors.toList());
    }
}
