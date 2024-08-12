package org.teamtuna.yaguroute.dto;

import lombok.*;
import org.teamtuna.yaguroute.aggregate.Sellable;

import java.sql.Time;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GameDTO {
    private int gameId;
    private Date gameDate;
    private Time gameTime;
    private Sellable sellable;
    private int homeTeamId;
    private int awayTeamId;
}
