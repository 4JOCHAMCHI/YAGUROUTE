package org.teamtuna.yaguroute.controller;

import org.teamtuna.yaguroute.dto.TeamRankDTO;
import org.teamtuna.yaguroute.service.TeamRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rankings")
public class TeamRankController {

    @Autowired
    private TeamRankService teamRankService;

    @GetMapping("/all")
    public ResponseEntity<List<TeamRankDTO>> getAllTeamRanks() {
        List<TeamRankDTO> teamRanks = teamRankService.getAllTeamRanks();
        return ResponseEntity.ok(teamRanks);
    }
}