package org.teamtuna.yaguroute.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.teamtuna.yaguroute.aggregate.Member;

@RestController
public class ProfileController {
    @GetMapping("/profile")
    public Member getProfile(HttpSession session) {
        // email에 해당하는 member 정보 가져오기
        return (Member) session.getAttribute("profile");
    }
}
