package org.teamtuna.yaguroute.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.teamtuna.yaguroute.aggregate.Member;

@RestController
@RequestMapping("/api")
@Tag(name = "회원정보", description = "세션에 담는 회원 정보 관련 API")
public class ProfileController {
    @Operation(summary = "프로필 조회", description = "세션에 담긴 사용자의 프로필 조회")
    @GetMapping("/profile")
    public Member getProfile(HttpSession session) {
        // email에 해당하는 member 정보 가져오기
        return (Member) session.getAttribute("profile");
    }
}
