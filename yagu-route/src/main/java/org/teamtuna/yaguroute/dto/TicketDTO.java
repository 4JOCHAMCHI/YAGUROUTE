package org.teamtuna.yaguroute.dto;

import lombok.*;
import org.teamtuna.yaguroute.aggregate.Game;
import org.teamtuna.yaguroute.aggregate.Ticket;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TicketDTO {

    private int ticketId;
    private String seatNum;
    private int price;
    private boolean isSold;
    private int seatCol;
    private int seatRow;
    private Game game;

    public TicketDTO(Ticket ticket) {
        this.ticketId = ticket.getTicketId();
        this.seatNum = ticket.getSeatNum();
        this.isSold = ticket.isSold();
        this.seatCol = ticket.getSeatCol();
        this.seatRow = ticket.getSeatRow();
        this.game = ticket.getGame();
    }
}
