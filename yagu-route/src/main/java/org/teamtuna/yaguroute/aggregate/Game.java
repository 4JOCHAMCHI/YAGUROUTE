package org.teamtuna.yaguroute.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.sql.Time;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;

    @Column(name = "sellable")
    @Enumerated(EnumType.STRING)
    private Sellable sellable;

    @OneToOne(mappedBy = "game")
    private Ticket ticket;
}

