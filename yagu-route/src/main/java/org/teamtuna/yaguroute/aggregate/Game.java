package org.teamtuna.yaguroute.aggregate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "game")
public class Game {

    @Id
    @Column(name = "game_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameId;

    @Column(name = "game_date")
    private Date gameDate;

    @Column(name = "game_time")
    private Time gameTime;

    @Column(name = "sellable")
    @Enumerated(EnumType.STRING)
    private Sellable sellable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;

    @OneToMany(mappedBy = "game")
    @JsonIgnore
    @ToString.Exclude
    private List<GameSeat> gameSeats;
}

