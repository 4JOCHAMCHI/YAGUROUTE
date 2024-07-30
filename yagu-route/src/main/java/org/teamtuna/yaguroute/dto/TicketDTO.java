package org.teamtuna.yaguroute.dto;

import lombok.*;
import org.teamtuna.yaguroute.aggregate.Ticket;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TicketDTO {

    private int ticketId;
    private int seatNum;
    private int price;
    private boolean isSold;
    private int seatCol;
    private int seatRow;
    private int gameId;

    public TicketDTO(Ticket ticket) {
        this.ticketId = ticket.getTicketId();
        this.seatNum = ticket.getSeatNum();
        this.isSold = ticket.isSold();
        this.seatCol = ticket.getSeatCol();
        this.seatRow = ticket.getSeatRow();
        this.gameId = ticket.getGame().getGameId();
    }
}
