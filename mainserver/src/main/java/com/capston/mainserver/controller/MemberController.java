package com.capston.mainserver.controller;

import com.capston.mainserver.domain.Member;
import com.capston.mainserver.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member/{name}")
    public ResponseEntity<?> createMember(@PathVariable("name") String name) {
        Member member = new Member();
        member.setMemberName(name);

        try {
            memberService.join(member);
            return new ResponseEntity<>(member, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/member/{member_id}")
    public Optional<Member> readMember(@PathVariable("member_id") Long member_id) {
        return memberService.findOne(member_id);
    }

    @PutMapping("/member/{id}")
    public ResponseEntity<String> updateMember(@PathVariable("member_id") Long member_id, @RequestParam("name") String name) {
        memberService.modify(member_id,name);
        return ResponseEntity.status(HttpStatus.CREATED).body("Member modify successfully");
    }

    @DeleteMapping("/member/{member_id}")
    public ResponseEntity<String> deleteMember(@PathVariable("member_id") Long member_id) {
        memberService.remove(member_id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Member delete successfully");
    }
}
