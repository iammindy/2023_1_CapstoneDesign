package com.capston.mainserver.service;

import com.capston.mainserver.domain.Member;
import com.capston.mainserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional //JPA
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getMemberId();
    }

    public void modify(Long member_id, String name) {
        memberRepository.findBymemberId(member_id)
                .ifPresent(m -> {
                    m.setMemberName(name);
                });
    }

    public Optional<Member> remove(Long member_id) { return memberRepository.removeBymemberId(member_id); }

    public Optional<Member> findOne(Long member_id) {
        return memberRepository.findBymemberId(member_id);
    }


    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getMemberName())
                .ifPresent(m -> {
                    throw new IllegalStateException("error: already exists member name");
                });
    }

}
