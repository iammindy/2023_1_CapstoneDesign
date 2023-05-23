package com.capston.mainserver.repository;

import com.capston.mainserver.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findBymemberId(Long member_id);
    Optional<Member> findByName(String name);
    Optional<Member> removeBymemberId(Long member_id);
}
