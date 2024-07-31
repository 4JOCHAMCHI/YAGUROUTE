package org.teamtuna.yaguroute.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GameDetailDTO {
    private String gameDate;
    private String gameTime;
    private String location;
    private String stadiumName;
    private String seatNum;
    private String homeTeamName;
    private String awayTeamName;
}