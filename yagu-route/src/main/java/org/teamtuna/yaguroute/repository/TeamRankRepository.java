package org.teamtuna.yaguroute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teamtuna.yaguroute.aggregate.TeamRank;

public interface TeamRankRepository extends JpaRepository<TeamRank, Integer> {
}
