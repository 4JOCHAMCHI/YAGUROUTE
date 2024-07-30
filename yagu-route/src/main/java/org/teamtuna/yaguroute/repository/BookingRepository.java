package org.teamtuna.yaguroute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teamtuna.yaguroute.aggregate.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
