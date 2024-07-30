package org.teamtuna.yaguroute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teamtuna.yaguroute.aggregate.Ticket;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByGame_GameId(int gameId);
    List<Ticket> findByGame_GameIdAndIsSoldFalse(int gameId);
    Optional<Ticket> findByGame_GameIdAndSeatNum(int gameId, int seatNum);
}
