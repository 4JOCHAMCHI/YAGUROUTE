package org.teamtuna.yaguroute.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "예매", description = "예매 처리 API")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/{memberId}/{gameId}/{seatId}")
    @Operation(summary = "예매", description = "좌석을 예매합니다.")
    public ResponseEntity<ResponseMessage> bookTicket(@PathVariable("memberId") int memberId, @PathVariable("gameId") int gameId, @PathVariable("seatId") int seatId) {
        TicketDTO booking = ticketService.bookTicket(memberId, gameId, seatId);

        Map<String, Object> result = new HashMap<>();
        result.put("booking", booking);

        return ResponseEntity.status(201).body(new ResponseMessage(201, "좌석 예매 성공", result));
    }
}

