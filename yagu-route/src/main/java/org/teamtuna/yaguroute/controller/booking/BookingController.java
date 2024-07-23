package org.teamtuna.yaguroute.controller.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "tickets";

    @PostMapping("/")
    public String bookTicket(@RequestParam("ticketId") int ticketId) {

        kafkaTemplate.send(TOPIC, String.valueOf(ticketId));
        return "발송 성공!";
    }
}

