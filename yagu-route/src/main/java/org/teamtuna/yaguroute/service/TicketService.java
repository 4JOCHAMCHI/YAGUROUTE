package org.teamtuna.yaguroute.service;

import org.teamtuna.yaguroute.dto.TicketDTO;

public interface TicketService {

    TicketDTO bookTicket(int memberId, int gameId, int seatId);
    void getMailInfo(int memberId, int ticketId);
}
