package org.teamtuna.yaguroute.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.teamtuna.yaguroute.dto.TeamRankDTO;
import org.teamtuna.yaguroute.service.TeamRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranking")
@Tag(name = "팀순위", description = "팀순위 관련 API")
public class TeamRankController {

    @Autowired
    private TeamRankService teamRankService;

    @Operation(summary = "팀순위 전체 조회", description = "모든 팀순위를 조회합니다.")
    @GetMapping("/all")
    public ResponseEntity<List<TeamRankDTO>> getAllTeamRanks() {
        List<TeamRankDTO> teamRanks = teamRankService.getAllTeamRanks();
        return ResponseEntity.ok(teamRanks);
    }
}