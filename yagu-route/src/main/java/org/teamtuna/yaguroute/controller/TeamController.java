package org.teamtuna.yaguroute.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.teamtuna.yaguroute.dto.TeamDTO;
import org.teamtuna.yaguroute.service.TeamService;

import java.io.IOException;

@RestController
@RequestMapping("/teams")
@Tag(name = "팀", description = "팀 관련 API")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Operation(summary = "팀 로고 업로드", description = "팀 ID로 팀 로고를 업로드합니다.")
    @PostMapping("/uploadLogo/{teamId}")
    public ResponseEntity<TeamDTO> uploadTeamLogo(@RequestParam("file") MultipartFile file, @PathVariable int teamId) {
        try {
            TeamDTO team = teamService.saveTeamLogo(file, teamId);
            return ResponseEntity.ok(team);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "로고 경로로 팀 조회", description = "로고 경로를 이용하여 팀 정보를 조회합니다.")
    @GetMapping("/logo")
    public ResponseEntity<TeamDTO> getTeamByLogoPath(@RequestParam("path") String path) {
        TeamDTO team = teamService.getTeamByLogoPath(path);
        if (team != null) {
            return ResponseEntity.ok(team);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "팀 ID로 팀 로고 경로 조회", description = "팀 ID를 이용하여 팀 로고 경로를 조회합니다.")
    @GetMapping("/logo/{teamId}")
    public ResponseEntity<String> getTeamLogoByTeamId(@PathVariable int teamId) {
        try {
            String logoPath = teamService.getTeamLogoByTeamId(teamId);
            return ResponseEntity.ok(logoPath);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}