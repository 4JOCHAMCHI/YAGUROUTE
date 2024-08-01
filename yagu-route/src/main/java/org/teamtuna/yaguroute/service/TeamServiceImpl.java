package org.teamtuna.yaguroute.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.teamtuna.yaguroute.aggregate.Team;
import org.teamtuna.yaguroute.dto.TeamDTO;
import org.teamtuna.yaguroute.repository.TeamRepository;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private Storage storage;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    @Override
    public TeamDTO saveTeamLogo(MultipartFile file, int teamId) throws IOException {
        String fileName = file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, file.getInputStream());

        URL url = storage.signUrl(blobInfo, 365, TimeUnit.DAYS);  // 1년 동안 유효한 URL 생성

        Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team not found"));
        team = new Team(team.getTeamId(), team.getTeamName(), url.toString(), team.getStadium(), team.getHomeGames(), team.getAwayGames());
        teamRepository.save(team);

        return modelMapper.map(team, TeamDTO.class);
    }

    @Override
    public TeamDTO getTeamByLogoPath(String logoPath) {
        Team team = teamRepository.findByLogo(logoPath);
        return modelMapper.map(team, TeamDTO.class);
    }

    @Override
    public String getTeamLogoByTeamId(int teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team not found"));
        return team.getLogo();
    }

    @Override
    public void initializeTeamsWithLogos(Map<Integer, String> teamLogos) {
        for (Map.Entry<Integer, String> entry : teamLogos.entrySet()) {
            int teamId = entry.getKey();
            String logoUrl = entry.getValue();

            Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team not found"));
            team = new Team(team.getTeamId(), team.getTeamName(), logoUrl, team.getStadium(), team.getHomeGames(), team.getAwayGames());
            teamRepository.save(team);
        }
    }
}
