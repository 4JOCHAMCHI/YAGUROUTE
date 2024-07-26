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
@ToString(exclude = {"homeGames", "awayGames", "member"})
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

    @OneToOne(mappedBy = "team", fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "homeTeam", fetch = FetchType.LAZY)
    private List<Game> homeGames;

    @OneToMany(mappedBy = "awayTeam", fetch = FetchType.LAZY)
    private List<Game> awayGames;
}
