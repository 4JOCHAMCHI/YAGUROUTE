package org.teamtuna.yaguroute.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private static final String SENDER = "yaguroute@naver.com";

    @KafkaListener(topics = "tickets", groupId = "notification-consumers")
    public String sendMail(int info) {

        MimeMessage message = mailSender.createMimeMessage();

        try {
            message.addRecipients(MimeMessage.RecipientType.TO, "goeunpark21@gmail.com");
            message.setSubject("[야구루트] 예매 확인 메일");

            message.setText("test", "utf-8");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


        return info + "번 예매 확인 메일 발송";
    }
}
