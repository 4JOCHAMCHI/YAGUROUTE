package org.teamtuna.yaguroute.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MailDTO {

    String memberEmail;
    String memberName;
    String ticketDate;
    String gameDate;
    String gameTime;
    String location;
    String stadiumName;
    String seatNum;
    String homeTeamName;
    String awayTeamName;
}
