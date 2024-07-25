package org.teamtuna.yaguroute.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teamrank")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TeamRank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_rank_id")
    private int teamRankId;

    @Column(name = "team_rank")
    private int teamRank;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "team_id")
    private Team team;

    @Column(name = "games_behind")
    private double gamesBehind;
}