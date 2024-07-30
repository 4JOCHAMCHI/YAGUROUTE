package org.teamtuna.yaguroute.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "gameseat")
public class GameSeat {

    @Id
    @Column(name = "game_seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameSeatId;

    @Column(name = "occupied")
    private boolean occupied;

    @Column(name = "game_seat_price")
    private int gameSeatPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}

