package org.teamtuna.yaguroute.dto;

import lombok.*;
import org.teamtuna.yaguroute.aggregate.GameSeat;
import org.teamtuna.yaguroute.aggregate.Ticket;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TicketDTO {

    private int ticketId;
    private LocalDateTime ticketDate;
    private int ticketPrice;
    private int memberId;
    private int gameSeatId;

    public TicketDTO(Ticket ticket) {
        this.ticketId = ticket.getTicketId();
        this.ticketDate = ticket.getTicketDate();
        this.ticketPrice = ticket.getTicketPrice();
        this.memberId = ticket.getMember().getMemberId();
        this.gameSeatId = ticket.getGameSeat().getGameSeatId();
    }
}
