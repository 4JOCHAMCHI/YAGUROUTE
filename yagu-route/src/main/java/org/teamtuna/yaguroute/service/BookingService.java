package org.teamtuna.yaguroute.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.teamtuna.yaguroute.aggregate.Booking;
import org.teamtuna.yaguroute.aggregate.Member;
import org.teamtuna.yaguroute.aggregate.Ticket;
import org.teamtuna.yaguroute.dto.BookingDTO;
import org.teamtuna.yaguroute.repository.BookingRepository;


@Service
@RequiredArgsConstructor
public class BookingService {

    private final MemberService memberService;
    private final TicketService ticketService;
    private final BookingRepository bookingRepository;

    public Booking converToBooking(BookingDTO bookingDTO) {
        return bookingRepository.findById(bookingDTO.getBookingId()).orElseThrow(() -> new EntityNotFoundException("Booking not found"));
    }

    @Transactional
    public BookingDTO addBooking(int memberId, int gameId, int seatNumber) {
        if (!ticketService.isSeatOccupied(gameId, seatNumber)) {
            int ticketId = ticketService.getTicketByGameIdAndSeatNumber(gameId, seatNumber).getTicketId();

            Member member = memberService.getMemberById(memberId).orElseThrow(() -> new EntityNotFoundException("Member Not Found"));
            Ticket ticket = ticketService.convertToTicket(ticketService.getTicketById(ticketId));

            Booking booking = new Booking(0, null, member, ticket);
            BookingDTO result = new BookingDTO(bookingRepository.save(booking));

            ticketService.occupySeat(gameId, seatNumber, ticketId);

            return result;
        }

        throw new RuntimeException("예매 불가");
    }
}
