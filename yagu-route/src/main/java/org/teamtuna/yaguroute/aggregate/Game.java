package org.teamtuna.yaguroute.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.sql.Date;

@Entity
@Table(name = "game")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private int gameId;

    @Column(name = "game_date")
    private Date gameDate;

    @Column(name = "game_time")
    private Time gameTime;

    @ManyToOne
    @JoinColumn(name = "home_team_id", referencedColumnName = "team_id")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id", referencedColumnName = "team_id")
    private Team awayTeam;
}