package org.teamtuna.yaguroute.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TeamDTO {
    private int teamId;
    private String teamName;
    private String logo;
    private String stadium;
    private String location;
}
