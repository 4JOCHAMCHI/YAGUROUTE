package org.teamtuna.yaguroute.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.teamtuna.yaguroute.aggregate.Member;
import org.teamtuna.yaguroute.common.ResponseMessage;
import org.teamtuna.yaguroute.dto.MemberDTO;
import org.teamtuna.yaguroute.service.MemberService;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;

    private RequestCache requestCache = new HttpSessionRequestCache();

    @GetMapping("/login-success")
    public ResponseEntity<ResponseMessage> loginSuccess() {
        return ResponseEntity.ok().body(new ResponseMessage(200, "로그인 성공", null));
    }

    @GetMapping("/login-success-oauth2")
    public ResponseEntity<ResponseMessage> loginSuccessOauth2(
            Principal principal,
            HttpServletResponse res,
            HttpSession session) throws IOException {

        Member profile = null;

        if (principal instanceof OAuth2AuthenticationToken token) {
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
//                if (true) throw new RuntimeException("!!!");
                memberService.addMember(member);
            }

            profile = memberService.getMemberByEmail(member.getMemberEmail()).get();
        }

        session.setAttribute("profile", profile);

//        var savedRequest = requestCache.getRequest(req, res);
//        var targetUrl = savedRequest.getRedirectUrl();

        var referer = session.getAttribute("original.referer");
        if (referer != null) {
            session.removeAttribute("original.referer");
            res.sendRedirect(referer.toString());
            return null;
        } else {
            return ResponseEntity.ok().body(new ResponseMessage(200, "로그인 성공", null));
        }
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

    /**
     * @see https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html#publish-authentication-manager-bean
     */
    @PostMapping("/rest_login")
    public ResponseEntity<?> restLogin(@RequestBody Map<String, String> body, HttpSession session) {
        try {
            Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(body.get("username"), body.get("password"));
            Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("result", "FAIL", "message", e.getMessage()));
        }

        // 로그인한 사용자의 이메일 정보 받아오기
        String email = body.get("username");

        // 이메일 정보로 사용자 객체 찾기
        Member profile = memberService.getMemberByEmail(email).get();

        // 세션 만들어서 사용자 객체 넣기
        session.setAttribute("profile", profile);

        return ResponseEntity.ok(Map.of("result", "OK"));
    }

    @GetMapping("/oauth2/{registrationId}")
    public void oauth2Login(
            @PathVariable("registrationId") String registrationId,
            HttpSession session,
            HttpServletRequest req,
            HttpServletResponse res) throws IOException {
//        requestCache.saveRequest(req, res);

        var referer = req.getHeader("Referer");
        var redirectUrl = UriComponentsBuilder.fromHttpUrl(referer).replacePath("dashboard").build().toString();
        System.out.println(redirectUrl);
        String dashboard = referer.replace("signin", "dashboard");

        if (referer.contains("signin"))
            session.setAttribute("original.referer", dashboard);
        else
            session.setAttribute("original.referer", referer);
        res.sendRedirect("/oauth2/authorization/" + registrationId);
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