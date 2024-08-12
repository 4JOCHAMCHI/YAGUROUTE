package org.teamtuna.yaguroute.aggregate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "seat")
public class Seat {

    @Id
    @Column(name = "seat_id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int seatId;

    @Column(name = "seat_num")
    private int seatNum;

    @Column(name = "seat_col")
    private int seatCol;

    @Column(name = "seat_row")
    private int seatRow;

    @Column(name = "seat_status")
    private boolean seatStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;

    @OneToMany(mappedBy = "seat")
    @JsonIgnore
    @ToString.Exclude
    private List<GameSeat> gameSeats;
}
