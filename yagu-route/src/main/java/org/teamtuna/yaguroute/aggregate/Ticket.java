package org.teamtuna.yaguroute.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @Column(name = "ticket_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;

    @Column(name = "ticket_date")
    private LocalDateTime ticketDate;

    @Column(name = "ticket_price")
    private int ticketPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_seat_id")
    private GameSeat gameSeat;

    @PrePersist
    public void prePersist() {
        this.ticketDate = LocalDateTime.now();
    }
}

