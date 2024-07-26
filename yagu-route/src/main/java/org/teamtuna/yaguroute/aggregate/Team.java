package org.teamtuna.yaguroute.aggregate;

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
@Table(name = "team")
public class Team {

    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teamId;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "logo")
    private String logo;

    @Column(name = "stadium")
    private String stadium;

    @Column(name = "location")
    private String location;

    @Column(name = "seat_cnt")
    private int seatCnt;

    @OneToMany(mappedBy = "homeTeam", fetch = FetchType.LAZY)
    private List<Game> homeGames;

    @OneToMany(mappedBy = "awayTeam", fetch = FetchType.LAZY)
    private List<Game> awayGames;
}
