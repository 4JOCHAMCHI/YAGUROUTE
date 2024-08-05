package org.teamtuna.yaguroute.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.teamtuna.yaguroute.common.ResponseMessage;
import org.teamtuna.yaguroute.dto.TicketDTO;
import org.teamtuna.yaguroute.service.TicketService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/{memberId}/{gameId}/{seatId}")
    public ResponseEntity<ResponseMessage> bookTicket(@PathVariable("memberId") int memberId, @PathVariable("gameId") int gameId, @PathVariable("seatId") int seatId) {
        TicketDTO booking = ticketService.bookTicket(memberId, gameId, seatId);

        Map<String, Object> result = new HashMap<>();
        result.put("booking", booking);

        return ResponseEntity.status(201).body(new ResponseMessage(201, "좌석 예매 성공", result));
    }
}

