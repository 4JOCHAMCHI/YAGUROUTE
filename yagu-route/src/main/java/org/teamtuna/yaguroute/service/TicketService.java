package org.teamtuna.yaguroute.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.teamtuna.yaguroute.aggregate.Ticket;
import org.teamtuna.yaguroute.aggregate.Member;
import org.teamtuna.yaguroute.aggregate.GameSeat;
import org.teamtuna.yaguroute.dto.TicketDTO;
import org.teamtuna.yaguroute.repository.GameSeatRepository;
import org.teamtuna.yaguroute.repository.TicketRepository;


@Service
@RequiredArgsConstructor
public class TicketService {

    private final MemberService memberService;
    private final GameSeatService gameSeatService;
    private final GameSeatRepository gameSeatRepository;
    private final TicketRepository ticketRepository;

    public Ticket converToTicket(TicketDTO ticketDTO) {
        return ticketRepository.findById(ticketDTO.getTicketId()).orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
    }

    @Transactional
    public TicketDTO bookTicket(int memberId, int gameId, int seatId) {
        if (!gameSeatService.isSeatOccupied(gameId, seatId)) {
            Member member = memberService.getMemberById(memberId).orElseThrow(() -> new EntityNotFoundException("Member Not Found"));
            GameSeat gameSeat = gameSeatService.convertToGameSeat(gameSeatService.getTicketByGameIdAndSeatId(gameId, seatId));

            Ticket ticket = new Ticket(0, null, gameSeat.getGameSeatPrice(), member, gameSeat);
            TicketDTO result = new TicketDTO(ticketRepository.save(ticket));

            gameSeatService.occupySeat(gameId, seatId);

            return result;
        }

        throw new RuntimeException("예매 불가");
    }
}
