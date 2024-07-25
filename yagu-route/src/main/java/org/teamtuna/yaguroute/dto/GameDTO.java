package org.teamtuna.yaguroute.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

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
    private int homeTeamId;
    private int awayTeamId;
}
