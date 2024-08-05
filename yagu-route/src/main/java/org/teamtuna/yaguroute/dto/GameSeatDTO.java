package org.teamtuna.yaguroute.dto;

import lombok.*;
import org.teamtuna.yaguroute.aggregate.GameSeat;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GameSeatDTO {

    private int gameSeatId;
    private boolean occupied;
    private int gameSeatPrice;
    private int gameId;
    private int seatId;

    public GameSeatDTO(GameSeat gameSeat) {
        this.gameSeatId = gameSeat.getGameSeatId();
        this.occupied = gameSeat.isOccupied();
        this.gameSeatPrice = gameSeat.getGameSeatPrice();
        this.gameId = gameSeat.getGame().getGameId();
        this.seatId = gameSeat.getSeat().getSeatId();
    }
}
