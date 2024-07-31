package org.teamtuna.yaguroute.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
//@EnableWebSecurity(debug = true) // request가 올 떄마다 어떤 filter를 사용하고 있는지를 출력
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf((csrf) -> csrf.disable())
            .authorizeHttpRequests(requests -> requests
                    .requestMatchers("/member/**", "/game/**", "/ranking/**").permitAll()
                    .requestMatchers("/ticket/**", "/booking/**").authenticated()
                    .anyRequest().permitAll()
            ).formLogin(form -> form
//                    .loginPage("/signin")
                    .defaultSuccessUrl("/login-success")
                    .permitAll()
            ).logout((logout)->logout
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
}
