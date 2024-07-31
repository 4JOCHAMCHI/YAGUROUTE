package org.teamtuna.yaguroute.config.security;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class WebSigninSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override               //인증에 성공 했을때 처리할 로직 작성할 메소드
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String targetUrl = "/";
        WebUserDetails userDetails = (WebUserDetails) authentication.getPrincipal();
        if (userDetails.getAuthorities() == null) {
            targetUrl = "/member/signup";
            redirectStrategy.sendRedirect(request, response, targetUrl);
        }
//        ========================================일반 login(권한이 있다면)=========================================
        else {
            targetUrl = determineTargetUrl(request);                                         //기존 가려고 했던 페이지로 로그인 완료 후 가기 위한 메소드 호출
            getRedirectStrategy().sendRedirect(request, response, targetUrl);                // determineTargetUrl 에서 얻어온 url 로 리다이렉트 해준다.
            super.onAuthenticationSuccess(request, response, authentication);                // onAuthenticationSuccess 에 정의 된 나머지 동작들을 수행
        }
    }


    protected String determineTargetUrl(HttpServletRequest request) {                            // 사용자가 원래 가려고 했던 페이지를 가져오는 로직 구현
        String targetUrl = "/index"; // 기본 페이지 설정

        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, null);     // 세션에서 원래의 요청 URL 가져오기
        if (savedRequest != null) {                                                             // 세션에서 저장된 targetUrl이 있다면 그것을 반환
            targetUrl = savedRequest.getRedirectUrl();
        }
        //없다면 기본 페이지로 이동하도록 설정
        return targetUrl;
    }


}
