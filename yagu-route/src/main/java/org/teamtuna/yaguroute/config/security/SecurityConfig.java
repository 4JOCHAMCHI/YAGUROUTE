package org.teamtuna.yaguroute.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@Configuration
//@EnableWebSecurity(debug = true) // request가 올 떄마다 어떤 filter를 사용하고 있는지를 출력
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//            .requestCache(cache -> cache.requestCache(new HttpSessionRequestCache())) // https://docs.spring.io/spring-security/reference/servlet/architecture.html#savedrequests
            .csrf((csrf) -> csrf.disable())
            .authorizeHttpRequests(requests -> requests
                    .requestMatchers("/game/**", "/ranking/**", "/rest_login").permitAll()
                    .requestMatchers("/member/**", "/ticket/**", "/booking/**").authenticated()
                    .anyRequest().permitAll()
//            ).formLogin(form -> form
//                    .loginPage("/signin")
//                    .defaultSuccessUrl("/login-success")
//                    .permitAll()
            ).oauth2Login(oauth2 -> oauth2
                    .defaultSuccessUrl("/login-success-oauth2"))
            .logout((logout)->logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index")									//로그아웃 성공시 보낼 url
                .permitAll());

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
