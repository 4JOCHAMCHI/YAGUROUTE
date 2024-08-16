package org.teamtuna.yaguroute.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.teamtuna.yaguroute.aggregate.Sellable;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameSummaryDTO {
    private int gameId;
    private Date gameDate;
    private Time gameTime;
    private Sellable sellable;
    private String homeTeamName;
    private String awayTeamName;
    private String stadiumName; // 구장 이름
}
