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
@Table(name = "stadium")
public class Stadium {

    @Id
    @Column(name = "stadium_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stadiumId;

    @Column(name = "stadium_name")
    private String stadiumName;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "location")
    private String location;

    @Column(name = "seat_price")
    private int seatPrice;

    @OneToMany(mappedBy = "stadium")
    @JsonIgnore
    @ToString.Exclude
    private List<Team> teams;

    @OneToMany(mappedBy = "stadium")
    @JsonIgnore
    @ToString.Exclude
    private List<Seat> seats;
}
