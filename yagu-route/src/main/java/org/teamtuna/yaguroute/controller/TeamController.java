package org.teamtuna.yaguroute.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.teamtuna.yaguroute.dto.TeamDTO;
import org.teamtuna.yaguroute.service.TeamService;

import java.io.IOException;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/uploadLogo/{teamId}")
    public ResponseEntity<TeamDTO> uploadTeamLogo(@RequestParam("file") MultipartFile file, @PathVariable int teamId) {
        try {
            TeamDTO team = teamService.saveTeamLogo(file, teamId);
            return ResponseEntity.ok(team);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/logo")
    public ResponseEntity<TeamDTO> getTeamByLogoPath(@RequestParam("path") String path) {
        TeamDTO team = teamService.getTeamByLogoPath(path);
        if (team != null) {
            return ResponseEntity.ok(team);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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
