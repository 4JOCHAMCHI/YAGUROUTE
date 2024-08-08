package org.teamtuna.yaguroute.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.teamtuna.yaguroute.YaguRouteApplication;
import org.teamtuna.yaguroute.aggregate.Member;
import org.teamtuna.yaguroute.dto.MemberDTO;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = YaguRouteApplication.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private ModelMapper modelMapper;

    @Test
    @DisplayName("전체 회원 조회")
    void getAll() {
        List<MemberDTO> memberList = memberService.getAll();
        assertEquals(1, memberList.size());
    }

    @Test
    @DisplayName("상세 회원 조회(ID)")
    void getMemberById() {
        Optional<Member> member = memberService.getMemberById(1);
        Member memberValue = member.get();
        assertEquals("goeunPark", memberValue.getMemberName());
    }

    @Test
    @DisplayName("상세 회원 조회(email)")
    void getMemberByEmail() {
        Optional<Member> member = memberService.getMemberByEmail("goeunpark21");
        Member memberValue = member.get();
        assertEquals("goeunPark", memberValue.getMemberName());
    }

    @Test
    @DisplayName("회원 가입 실패 케이스")
    void addMemberFailure() {
        Member member = Member
                    .builder()
                    .memberName("goeunPark")
                    .memberEmail("goeunpark21") // 중복되는 이메일
                    .memberPhone("010-1111-2222")
                    .memberPassword("12341234Password")
                    .build();

        MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);
        boolean result = memberService.addMember(memberDTO);

//        assertEquals(false, result);
        assertFalse(result, "오류가 나야함");
    }

    @Test
    @DisplayName("회원 가입 성공 케이스")
    void addMemberSuccess() {
        Member member = Member
                .builder()
                .memberName("goeunPark")
                .memberEmail("goeunpark2135")
                .memberPhone("010-1111-2222")
                .memberPassword("12341234Password")
                .build();

        MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);
        boolean result = memberService.addMember(memberDTO);

//        assertEquals(true, result);
        assertTrue(result, "오류가 안나야함");
    }

    @Test
    @DisplayName("회원 가입 후 DB에 데이터가 잘 저장되었는지 확인")
    void saveMemberDataIntoDB() {
        Member member = Member
                .builder()
                .memberName("hong")
                .memberEmail("hong@gmail.com")
                .memberPhone("010-1234-5678")
                .memberPassword("password12345678")
                .build();

        MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);
        memberService.addMember(memberDTO);

        boolean result = memberService.getMemberByEmail(member.getMemberEmail()).isPresent();
        assertEquals(true, result);
    }

    @Test
    @DisplayName("회원정보(전화번호) 수정")
    void updateMemberPhone() {
        Optional<Member> member = memberService.getMemberByEmail("goeunpark21");
        Member memberValue = member.get();
        String origin = memberValue.getMemberPhone();
        memberValue.updateProfile("010-1111-2222");

        assertNotEquals(origin, memberValue.getMemberName());
    }
}
