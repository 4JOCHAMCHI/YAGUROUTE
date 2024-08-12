package org.teamtuna.yaguroute.service;

import org.springframework.web.multipart.MultipartFile;
import org.teamtuna.yaguroute.aggregate.Team;
import org.teamtuna.yaguroute.dto.TeamDTO;

import java.io.IOException;
import java.util.Map;

public interface TeamService {
    TeamDTO saveTeamLogo(MultipartFile file, int teamId) throws IOException;
    TeamDTO getTeamByLogoPath(String logoPath);
    String getTeamLogoByTeamId(int teamId);
    void initializeTeamsWithLogos(Map<Integer, String> teamLogos);
}
