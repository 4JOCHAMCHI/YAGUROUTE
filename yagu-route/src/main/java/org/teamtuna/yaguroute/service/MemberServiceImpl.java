package org.teamtuna.yaguroute.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.teamtuna.yaguroute.aggregate.Member;
import org.teamtuna.yaguroute.dto.MemberDTO;
import org.teamtuna.yaguroute.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository, ModelMapper modelMapper) {
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public Optional<Member> getMemberById(int memberId)  {
        return memberRepository.findById(memberId);
    }

    @Override
    public List<MemberDTO> getAll() {
        List<Member> memberList =  memberRepository.findAll();
        List<MemberDTO> memberDTOList = memberList.stream()
                .map(member -> modelMapper.map(member, MemberDTO.class)).toList();

        return memberDTOList;
    }
}
