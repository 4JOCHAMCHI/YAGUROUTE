package org.teamtuna.yaguroute.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.teamtuna.yaguroute.dto.BookingDTO;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @Column(name = "booking_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    @Column(name = "booking_date")
    private LocalDateTime bookingDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @PrePersist
    public void prePersist() {
        this.bookingDate = LocalDateTime.now();
    }

    public Booking(BookingDTO bookingDTO) {
        this.bookingDate = bookingDTO.getBookingDate();
        this.member = bookingDTO.getMember();
        this.ticket = bookingDTO.getTicket();
    }
}

