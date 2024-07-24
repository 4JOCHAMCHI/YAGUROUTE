package org.teamtuna.yaguroute.service;

import org.teamtuna.yaguroute.aggregate.TeamRank;
import org.teamtuna.yaguroute.dto.TeamRankDTO;
import org.teamtuna.yaguroute.repository.TeamRankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamRankServiceImpl implements TeamRankService {

    @Autowired
    private TeamRankRepository teamRankRepository;

    @Override
    public List<TeamRankDTO> getAllTeamRanks() {
        System.out.println("Invoking teamRankRepository.findAll()...");
        List<TeamRank> teamRankList = teamRankRepository.findAll();
        System.out.println("Total team ranks fetched: " + teamRankList.size());

        List<TeamRankDTO> teamRanks = teamRankList.stream().map(teamRank -> {
            TeamRankDTO teamRankDTO = new TeamRankDTO(
                    teamRank.getTeamRankId(),
                    teamRank.getTeamRank(),
                    teamRank.getTeam().getTeamId(),
                    teamRank.getGamesBehind()
            );
            System.out.println("TeamRankDTO: " + teamRankDTO);
            return teamRankDTO;
        }).collect(Collectors.toList());

        teamRanks.forEach(System.out::println);
        return teamRanks;
    }
}