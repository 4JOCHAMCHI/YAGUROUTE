package org.teamtuna.yaguroute.config.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class WebUserDetails implements UserDetails {
    private int memberId;
    private String memberName;
    private String memberEmail;
    private String memberPassword;
    private String memberPhone;
    private List<GrantedAuthority> authorities;

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return this.memberEmail;    // email을 id로 삼아 로그인
    }

    @Override
    public String getPassword() {
        return this.memberPassword;
    }
}
