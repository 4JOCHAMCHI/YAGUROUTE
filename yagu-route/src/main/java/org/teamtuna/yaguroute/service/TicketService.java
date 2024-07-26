package org.teamtuna.yaguroute.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamtuna.yaguroute.dto.TicketDTO;
import org.teamtuna.yaguroute.repository.TicketRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public List<TicketDTO> findAllSeats(int gameId) {
        return ticketRepository.findByGame_GameId(gameId).stream().map(TicketDTO::new).collect(Collectors.toList());
    }

    public TicketDTO findSeatById(int ticketId) {
        // Redis에 조회된 좌석 정보 저장
        return new TicketDTO(ticketRepository.findById(ticketId).orElseThrow(() -> new EntityNotFoundException("Seat not found")));
    }

    public List<TicketDTO> findAvailableSeat(int gameId) {
        return ticketRepository.findByGame_GameIdAndIsSoldFalse(gameId).stream().map(TicketDTO::new).collect(Collectors.toList());
    }
}
