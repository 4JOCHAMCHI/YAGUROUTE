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
    private int memberId;
    private int ticketId;

    public BookingDTO(Booking booking) {
        this.bookingId = booking.getBookingId();
        this.bookingDate = booking.getBookingDate();
        this.memberId = booking.getMember().getMemberId();
        this.ticketId = booking.getTicket().getTicketId();
    }
}
