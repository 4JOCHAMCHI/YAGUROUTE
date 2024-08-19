package org.teamtuna.yaguroute.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamtuna.yaguroute.aggregate.Member;
import org.teamtuna.yaguroute.dto.MemberDTO;
import org.teamtuna.yaguroute.service.MemberService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/member")
@Tag(name = "회원", description = "회원 관련 API")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary = "회원 전체 조회", description = "전체 회원의 정보를 조회함")
    @GetMapping("all")
    public ResponseEntity<List<MemberDTO>> findAll() {
        List<MemberDTO> memberList = memberService.getAll();

        return ResponseEntity.ok(memberList);
    }

    @Operation(summary = "회원 상세 조회", description = "특정 회원의 정보를 조회함")
    @GetMapping("{memberId}")
    public ResponseEntity<MemberDTO> findMemberById(@PathVariable("memberId") int id) {
        Optional<Member> member = memberService.getMemberById(id);

//        return ResponseEntity.ok(new MemberDTO(member));
        return member.map(value -> ResponseEntity.ok(new MemberDTO(value))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @Operation(summary = "회원 추가", description = "회원을 추가함")
    @PostMapping("/signup")
    public ResponseEntity<MemberDTO> addMember(@RequestBody MemberDTO memberDTO) {
        boolean isComplete = memberService.addMember(memberDTO);

        if (!isComplete)
            return new ResponseEntity<>(new MemberDTO(), HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(memberDTO);
    }

}
