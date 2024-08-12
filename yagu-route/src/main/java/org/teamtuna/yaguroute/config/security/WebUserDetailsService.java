package org.teamtuna.yaguroute.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.teamtuna.yaguroute.aggregate.Member;
import org.teamtuna.yaguroute.repository.MemberRepository;

import java.util.List;

@Service
public class WebUserDetailsService implements UserDetailsService {
    private MemberRepository memberRepository;
    public WebUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberEmail(email);

        if (member == null)
            throw new UsernameNotFoundException(email + " is not found");

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_MEMBER"));

        WebUserDetails userDetails = WebUserDetails
                .builder()
                .memberId(member.getMemberId())
                .memberName(member.getMemberName())
                .memberPassword(member.getMemberPassword())
                .memberEmail(member.getMemberEmail())
                .memberPhone(member.getMemberPhone())
                .authorities(authorities)
                .build();

        return userDetails;
    }
}