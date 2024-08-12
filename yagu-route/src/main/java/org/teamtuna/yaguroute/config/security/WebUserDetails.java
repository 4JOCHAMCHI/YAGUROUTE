package org.teamtuna.yaguroute.config.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class WebUserDetails implements UserDetails, OAuth2User {
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


    /* for OAuth2User */
    private String  name;
    private Map<String, Object> attributes;

    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }
}
