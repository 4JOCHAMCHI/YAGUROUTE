package org.teamtuna.yaguroute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teamtuna.yaguroute.aggregate.Seat;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
}
