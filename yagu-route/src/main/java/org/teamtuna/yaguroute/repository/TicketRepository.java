package org.teamtuna.yaguroute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teamtuna.yaguroute.aggregate.GameSeat;
import org.teamtuna.yaguroute.aggregate.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    void deleteByGameSeat(GameSeat gameSeat);
}
