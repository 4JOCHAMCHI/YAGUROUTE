package org.teamtuna.yaguroute;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    @KafkaListener(topics = "tickets", groupId = "notification-consumers")
    public void sendMail(String message) {
        System.out.println("수신: " + message);
    }
}
