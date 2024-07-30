package org.teamtuna.yaguroute.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.teamtuna.yaguroute.common.ResponseMessage;
import org.teamtuna.yaguroute.dto.BookingDTO;
import org.teamtuna.yaguroute.service.BookingService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/{memberId}/{gameId}/{seatNumber}")
    public ResponseEntity<ResponseMessage> bookTicket(@PathVariable("memberId") int memberId, @PathVariable("gameId") int gameId, @PathVariable("seatNumber") int seatNumber) {
        BookingDTO booking = bookingService.addBooking(memberId, gameId, seatNumber);

        Map<String, Object> result = new HashMap<>();
        result.put("booking", booking);

        return ResponseEntity.status(201).body(new ResponseMessage(201, "좌석 예매 성공", result));
    }
}

