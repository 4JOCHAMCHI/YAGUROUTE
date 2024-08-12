package org.teamtuna.yaguroute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teamtuna.yaguroute.aggregate.Team;


public interface TeamRepository extends JpaRepository<Team, Integer> {
    Team findByLogo(String logo);
}
