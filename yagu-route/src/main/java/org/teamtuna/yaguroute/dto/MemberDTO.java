package org.teamtuna.yaguroute.dto;

import jakarta.persistence.Column;
import lombok.*;
import org.teamtuna.yaguroute.aggregate.Member;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDTO {
    private int memberId;
    private String memberName;
    private String memberEmail;
    private String memberPassword;
    private String memberPhone;

    public MemberDTO(Member member) {
        this.memberId = member.getMemberId();
        this.memberName = member.getMemberName();
        this.memberEmail = member.getMemberEmail();
        this.memberPassword = member.getMemberPassword();
        this.memberPhone = member.getMemberPhone();
    }
}
