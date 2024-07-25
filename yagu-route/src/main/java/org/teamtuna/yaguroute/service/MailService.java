package org.teamtuna.yaguroute.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

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
            message.setSubject("[야구루트] 예매 확인 메일" + info);

            String content =
                    "<div style=\"width: 90%; max-width: 600px; margin: 30px auto; background-color: #FFFFFF; padding: 20px; border-radius: 10px; box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);\">" +
                        "<h1 style=\"color: #007BFF; text-align: center; margin: 45px 0px\">야구 경기 예매 확인</h1>" +
                            "<p style=\"text-align: center; font-size: 16px;\">안녕하세요, <strong>[고객님 성함]님</strong>.</p>" +
                            "<p style=\"text-align: center; font-size: 16px; margin-bottom: 30px\">[예매일시] <br> 다음과 같이 야구 경기 예매가 확인되었습니다.</p>" +

                        "<div style=\"margin-top: 20px; padding: 30px; background-color: #E9ECEF; border-radius: 8px;\">" +
                            "<p style=\"margin: 7; font-size: 16px;\"><strong>경기 일시:</strong> 2024년 8월 15일 오후 6시</p>" +
                            "<p style=\"margin: 7; font-size: 16px;\"><strong>경기 장소:</strong> 서울 종합운동장 야구장</p>" +
                            "<p style=\"margin: 7; font-size: 16px;\"><strong>좌석 번호:</strong> A구역 5열 12번</p>" +
                            "<p style=\"margin: 7; font-size: 16px;\"><strong>경기 팀:</strong> 팀 A vs 팀 B</p>" +
                        "</div>" +

                        "<p style=\"margin-top: 20px; font-size: 16px;\"></p>" +

                        "<div style=\"margin-top: 10px; padding-top: 20px; border-top: 1px solid #ddd; text-align: center; color: #777;\">" +
                            "<p style=\"font-size: 14px;\">야구루트를 이용해주셔서 진심으로 감사드립니다.<br> 즐거운 관람 되세요!</p>" +
                        "</div>" +
                    "</div>";

            message.setText(content, "utf-8", "html");
            message.setFrom(new InternetAddress(SENDER, "YaguRoute"));

            mailSender.send(message);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException("메일 발송 오류");
        }

        return info + "번 예매 확인 메일 발송";
    }

    // 메일 발송에 필요한 정보 조회
//    public
}
