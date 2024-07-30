package org.teamtuna.yaguroute.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.teamtuna.yaguroute.common.ResponseMessage;
import org.teamtuna.yaguroute.dto.TicketDTO;
import org.teamtuna.yaguroute.service.TicketService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/all/{gameId}")
    public ResponseEntity<ResponseMessage> getAllSeats(@PathVariable("gameId") int gameId) {
        List<TicketDTO> seatDTOList = ticketService.getAllSeats(gameId);

        Map<String, Object> result = new HashMap<>();
        result.put("allSeats", seatDTOList);

        return ResponseEntity.ok().body(new ResponseMessage(200, "전체 좌석 조회 성공", result));
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<ResponseMessage> getTicketById(@PathVariable("ticketId") int ticketId) {
        TicketDTO ticket = ticketService.getTicketById(ticketId);

        Map<String, Object> result = new HashMap<>();
        result.put("ticket", ticket);

        return ResponseEntity.ok().body(new ResponseMessage(200, "좌석 상세 조회 성공", result));
    }

    @GetMapping("/available/{gameId}")
    public ResponseEntity<ResponseMessage> getAvailableSeats(@PathVariable("gameId") int gameId) {
        List<TicketDTO> seatList = ticketService.getAvailableSeats(gameId);

        Map<String, Object> result = new HashMap<>();
        result.put("seats", seatList);

        return ResponseEntity.ok().body(new ResponseMessage(200, "예매 가능 좌석 조회", result));
    }
}
