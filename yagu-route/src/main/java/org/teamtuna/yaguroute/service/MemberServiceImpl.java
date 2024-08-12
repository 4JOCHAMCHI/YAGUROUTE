package org.teamtuna.yaguroute.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<Member> getMemberById(int memberId)  {
        return memberRepository.findById(memberId);
    }

    @Override
    public Optional<Member> getMemberByEmail(String memberEmail) {
        return Optional.ofNullable(memberRepository.findByMemberEmail(memberEmail));
    }

    @Override
    public List<MemberDTO> getAll() {
        List<Member> memberList =  memberRepository.findAll();
        List<MemberDTO> memberDTOList = memberList.stream()
                .map(member -> modelMapper.map(member, MemberDTO.class)).toList();

        return memberDTOList;
    }

    @Override
    public boolean addMember(MemberDTO memberDTO) {
        // 이미 존재하는 이메일
        if (memberRepository.findByMemberEmail(memberDTO.getMemberEmail()) != null)
            return false;

        String encodedPassword = passwordEncoder.encode(memberDTO.getMemberPassword());

        Member member = Member
            .builder()
            .memberName(memberDTO.getMemberName())
            .memberEmail(memberDTO.getMemberEmail())
            .memberPhone(memberDTO.getMemberPhone())
            .memberPassword(encodedPassword)
            .build();

        memberRepository.save(member);
        return true;
    }

    @Override
    @Transactional
    public void updateMemberPhone(String email, String newPhoneNumber) {
        Member member = memberRepository.findByMemberEmail(email);
        member.updateProfile(newPhoneNumber);

        System.out.println(member.getMemberPhone());
    }
}
