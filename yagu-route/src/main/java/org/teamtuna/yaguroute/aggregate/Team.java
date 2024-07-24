package org.teamtuna.yaguroute.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "team")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private int teamId;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "logo")
    private String logo;

    @Column(name = "stadium")
    private String stadium;

    @Column(name = "location")
    private String location;
}