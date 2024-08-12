package org.teamtuna.yaguroute.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TeamRankDTO {
    private int teamRankId;
    private int teamRank;
    private int teamId;
    private double gamesBehind;
}