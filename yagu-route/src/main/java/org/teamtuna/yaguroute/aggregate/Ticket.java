package org.teamtuna.yaguroute.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.teamtuna.yaguroute.dto.TicketDTO;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @Column(name = "ticket_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;

    @Column(name = "seat_num")
    private String seatNum;

    @Column(name = "is_sold")
    private boolean isSold;

    @Column(name = "seat_col")
    private int seatCol;

    @Column(name = "seat_row")
    private int seatRow;

    @OneToOne
    @JoinColumn(name = "game_id")
    private Game game;

    public Ticket(TicketDTO ticketDTO) {
        this.seatNum = ticketDTO.getSeatNum();
        this.isSold = ticketDTO.isSold();
        this.seatCol = ticketDTO.getSeatCol();
        this.seatRow = ticketDTO.getSeatRow();
        this.game = ticketDTO.getGame();
    }
}

