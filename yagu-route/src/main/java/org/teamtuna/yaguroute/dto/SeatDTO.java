package org.teamtuna.yaguroute.dto;

import lombok.*;
import org.teamtuna.yaguroute.aggregate.Seat;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SeatDTO {

    private int seatId;
    private int seatNum;
    private int seatCol;
    private int seatRow;
    private boolean seatStatus;
    private int stadiumId;

    public SeatDTO(Seat seat)
    {
        this.seatId = seat.getSeatId();
        this.seatNum = seat.getSeatNum();
        this.seatCol = seat.getSeatCol();
        this.seatRow = seat.getSeatRow();
        this.seatStatus = seat.isSeatStatus();
        this.stadiumId = seat.getStadium().getStadiumId();
    }
}
