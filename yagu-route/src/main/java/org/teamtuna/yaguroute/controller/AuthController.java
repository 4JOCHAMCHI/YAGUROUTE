package org.teamtuna.yaguroute.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.teamtuna.yaguroute.common.ResponseMessage;
import org.teamtuna.yaguroute.dto.MemberDTO;
import org.teamtuna.yaguroute.service.MemberService;

import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    @GetMapping("/login-success")
    public ResponseEntity<ResponseMessage> loginSuccess() {
        return ResponseEntity.ok().body(new ResponseMessage(200, "로그인 성공", null));
    }

    @GetMapping("/login-success-oauth2")
    public ResponseEntity<ResponseMessage> loginSuccessOauth2(Principal principal) {
        if (principal instanceof  OAuth2AuthenticationToken token) {
            OAuth2User oauthUser = token.getPrincipal();
            String registrationId = token.getAuthorizedClientRegistrationId();

            MemberDTO member = switch (registrationId) {
                case "naver" -> handleNaverLogin(oauthUser);
                case "kakao" -> handleKakaoLogin(oauthUser);
                case "google" -> handleGoogleLogin(oauthUser);
                default -> MemberDTO.builder().memberEmail("hhhh@gmail.com").build();
            };

            //TODO: memberService.find... insert... or upsert...
            if (memberService.getMemberByEmail(member.getMemberEmail()).isEmpty()) {
                memberService.addMember(member);
            }
        }

        return ResponseEntity.ok().body(new ResponseMessage(200, "로그인 성공", null));
    }

    // 전화번호 업데이트 처리
    @PutMapping("/update-phone")
    public ResponseEntity<ResponseMessage> updatePhone(
            @RequestParam String email,
            @RequestParam String phone) {

        memberService.updateMemberPhone(email, phone);
        return ResponseEntity.ok(new ResponseMessage(200, "전화번호 업데이트 성공", null));
    }

    @GetMapping("/oauth2user")
    public ResponseEntity<?> oauth2user(Principal principal) {
        if (principal instanceof  OAuth2AuthenticationToken token) {
            OAuth2User oauthUser = token.getPrincipal();
            return ResponseEntity.ok().body(oauthUser);
        }

        return ResponseEntity.ok().body("ERROR");
    }

    private MemberDTO handleNaverLogin(OAuth2User oauthUser) {
        Map<String, String> attr = oauthUser.getAttribute("response");
        String id = attr.get("id");
        String email = attr.get("email");
        String name = attr.get("name");

        return MemberDTO
                .builder()
                .memberName(name)
                .memberEmail(email)
                .memberPassword(id)             // Member insert를 위한 안 쓰는 데이터
                .memberPhone("000-0000-0000")   // Member insert를 위한 더미데이터
                .build();
    }

    private MemberDTO handleKakaoLogin(OAuth2User oauthUser) {
        Map<String, Object> attr = oauthUser.getAttribute("kakao_account");
        Map<String, String> profile = (Map<String, String>) attr.get("profile");

        Long id = oauthUser.getAttribute("id");
        String email = (String) attr.get("email");
        String name = profile.get("nickname");

        System.out.println(id);
        System.out.println(email);
        System.out.println(name);

        return MemberDTO
                .builder()
                .memberName(name)
                .memberEmail(email)
                .memberPassword(id.toString())      // Member insert를 위한 안 쓰는 데이터
                .memberPhone("000-0000-0000")       // Member insert를 위한 더미데이터
                .build();
    }

    private MemberDTO handleGoogleLogin(OAuth2User oauthUser) {
        Map<String, Object> attr = oauthUser.getAttributes();
        String id = oauthUser.getAttribute("sub");
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        return MemberDTO
                .builder()
                .memberName(name)
                .memberEmail(email)
                .memberPassword(id)             // Member insert를 위한 안 쓰는 데이터
                .memberPhone("000-0000-0000")   // Member insert를 위한 더미데이터
                .build();
    }
}
