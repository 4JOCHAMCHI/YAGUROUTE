package org.teamtuna.yaguroute.aggregate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.teamtuna.yaguroute.dto.MemberDTO;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "member")
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "member_email")
    private String memberEmail;

    @Column(name = "member_password")
    private String memberPassword;

    @Column(name = "member_phone")
    private String memberPhone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Booking> bookingList = new ArrayList<>();

    public Member(MemberDTO memberDTO) {
        this.memberId = memberDTO.getMemberId();
        this.memberName = memberDTO.getMemberName();
        this.memberEmail = memberDTO.getMemberEmail();
        this.memberPassword = memberDTO.getMemberPassword();
        this.memberPhone = memberDTO.getMemberPhone();
    }
}

