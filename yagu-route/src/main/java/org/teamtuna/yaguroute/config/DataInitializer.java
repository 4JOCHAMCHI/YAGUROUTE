package org.teamtuna.yaguroute.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.teamtuna.yaguroute.service.TeamService;

import java.util.HashMap;
import java.util.Map;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TeamService teamService;

    public DataInitializer(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public void run(String... args) throws Exception {
        Map<Integer, String> teamLogos = new HashMap<>();
        teamLogos.put(1, "https://storage.cloud.google.com/team_logo/%ED%95%9C%ED%99%94.png");
        teamLogos.put(2, "https://storage.cloud.google.com/team_logo/%EB%A1%AF%EB%8D%B0.png");
        teamLogos.put(3, "https://storage.cloud.google.com/team_logo/KT.png");
        teamLogos.put(4, "https://storage.cloud.google.com/team_logo/LG.png");
        teamLogos.put(5, "https://storage.cloud.google.com/team_logo/%EB%91%90%EC%82%B0.png");
        teamLogos.put(6, "https://storage.cloud.google.com/team_logo/NC.png");
        teamLogos.put(7, "https://storage.cloud.google.com/team_logo/SSG.png");
        teamLogos.put(8, "https://storage.cloud.google.com/team_logo/KIA.png");
        teamLogos.put(9, "https://storage.cloud.google.com/team_logo/%EC%82%BC%EC%84%B1.png");
        teamLogos.put(10, "https://storage.cloud.google.com/team_logo/%ED%82%A4%EC%9B%80.png");
        teamService.initializeTeamsWithLogos(teamLogos);
    }
}