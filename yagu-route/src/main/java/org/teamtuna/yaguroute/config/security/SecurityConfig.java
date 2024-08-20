package org.teamtuna.yaguroute.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity(debug = true) // request가 올 떄마다 어떤 filter를 사용하고 있는지를 출력
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//            .requestCache(cache -> cache.requestCache(new HttpSessionRequestCache())) // https://docs.spring.io/spring-security/reference/servlet/architecture.html#savedrequests
            .csrf((csrf) -> csrf.disable())
//                .addFilterBefore()
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/", "/rest_login", "/login", "/login/**", "/oauth2/**", "/login-success-oauth2", "/api/logout", "/error").permitAll()
                .requestMatchers("/api/game/**", "/api/ranking/**", "/api/teams/**").permitAll()
                .requestMatchers("/api/member/**", "/api/ticket/**", "/api/booking/**", "/api/seat/**").authenticated()
                .anyRequest().authenticated()
//            ).formLogin(form -> form
//                    .loginPage("/signin")
//                    .defaultSuccessUrl("/login-success")
//                    .permitAll()
            )
            .oauth2Login(oauth2 -> oauth2
                // https://blog.naver.com/tjdwns8574/221888780340
                .defaultSuccessUrl("/login-success-oauth2", true))
            .logout((logout)->logout
                .logoutUrl("/api/logout")
                .logoutSuccessUrl("/")									//로그아웃 성공시 보낼 url
                .permitAll())
            .exceptionHandling(exHandle -> exHandle
                .authenticationEntryPoint(new AjaxAuthenticationEntryPoint("/login"))
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    /**
     * @see http://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html#customize-global-authentication-manager
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
//                .userDetailsService(customUserDetailService)
//                .passwordEncoder(passwordEncoder())
//                .and().build();
    }
}
