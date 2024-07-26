package org.teamtuna.yaguroute.dto;

import lombok.*;
import org.teamtuna.yaguroute.aggregate.Booking;
import org.teamtuna.yaguroute.aggregate.Member;
import org.teamtuna.yaguroute.aggregate.Ticket;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookingDTO {

    private int bookingId;
    private LocalDateTime bookingDate;
    private Member member;
    private Ticket ticket;

    public BookingDTO(Booking booking) {
        this.bookingId = booking.getBookingId();
        this.bookingDate = booking.getBookingDate();
        this.member = booking.getMember();
        this.ticket = booking.getTicket();
    }
}
