package org.teamtuna.yaguroute.service;

import org.teamtuna.yaguroute.aggregate.Member;
import org.teamtuna.yaguroute.dto.MemberDTO;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    Optional<Member> getMemberById(int memberId);
    List<MemberDTO> getAll();

}