package org.teamtuna.yaguroute.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.io.IOException;
import java.util.List;

/**
 * @see https://docs.spring.io/spring-security/reference/6.3-SNAPSHOT/servlet/architecture.html#servlet-exceptiontranslationfilter
 * @see https://redcoder.tistory.com/243
 */
public class AjaxAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public AjaxAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        String ajaxHeader = request.getHeader("X-Requested-With");
//        boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);

        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);
        List<MediaType> acceptTypes = MediaType.parseMediaTypes(acceptHeader);
        MediaType preferType = acceptTypes.getFirst();
        boolean isAjax = preferType.isCompatibleWith(MediaType.APPLICATION_JSON);

        // 403 Error를 반환한다.
        if (isAjax) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "세션 만료로 인해서 거부되었습니다.");
        } else {
            super.commence(request, response, authException);
        }
    }
}