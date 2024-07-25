package org.teamtuna.yaguroute.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamtuna.yaguroute.aggregate.Member;
import org.teamtuna.yaguroute.dto.MemberDTO;
import org.teamtuna.yaguroute.service.MemberService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<MemberDTO>> findAll() {
        List<MemberDTO> memberList = memberService.getAll();

        return ResponseEntity.ok(memberList);
    }

    @GetMapping("{memberId}")
    public ResponseEntity<MemberDTO> findMemberById(@PathVariable("memberId") int id) {
        Optional<Member> member = memberService.getMemberById(id);

//        return ResponseEntity.ok(new MemberDTO(member));
        return member.map(value -> ResponseEntity.ok(new MemberDTO(value))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }


}
