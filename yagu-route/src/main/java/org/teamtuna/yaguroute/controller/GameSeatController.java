package org.teamtuna.yaguroute.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.teamtuna.yaguroute.common.ResponseMessage;
import org.teamtuna.yaguroute.dto.GameSeatDTO;
import org.teamtuna.yaguroute.dto.SeatDTO;
import org.teamtuna.yaguroute.service.GameSeatService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seat")
@RequiredArgsConstructor
@Tag(name = "경기별 좌석", description = "경기별 좌석 관련 API")
public class GameSeatController {

    private final GameSeatService gameSeatService;

    @GetMapping("/all/{gameId}")
    @Operation(summary = "경기별 좌석 전체 조회", description = "경기별 전체 좌석 목록을 조회합니다.")
    public ResponseEntity<ResponseMessage> getAllSeats(@PathVariable("gameId") int gameId) {
        List<SeatDTO> seatDTOList = gameSeatService.getAllSeats(gameId);

        Map<String, Object> result = new HashMap<>();
        result.put("allSeats", seatDTOList);

        return ResponseEntity.ok().body(new ResponseMessage(200, "전체 좌석 조회 성공", result));
    }

    @GetMapping("/{seatId}")
    @Operation(summary = "좌석 상세 조회", description = "특정 좌석 상세 정보를 조회합니다.")
    public ResponseEntity<ResponseMessage> getSeatById(@PathVariable("seatId") int seatId) {
        SeatDTO seat = gameSeatService.getSeatById(seatId);

        Map<String, Object> result = new HashMap<>();
        result.put("seat", seat);

        return ResponseEntity.ok().body(new ResponseMessage(200, "좌석 상세 조회 성공", result));
    }

    @GetMapping("/occupied/{gameId}")
    @Operation(summary = "경기별 예매 불가 좌석 조회", description = "경기별 예매 불가 좌석 목록을 조회합니다.")
    public ResponseEntity<ResponseMessage> getAvailableSeats(@PathVariable("gameId") int gameId) {
        List<GameSeatDTO> seatList = gameSeatService.getOccupiedSeats(gameId);

        Map<String, Object> result = new HashMap<>();
        result.put("seats", seatList);

        return ResponseEntity.ok().body(new ResponseMessage(200, "예매 가능 좌석 조회", result));
    }
}
