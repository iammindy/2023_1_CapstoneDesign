package com.capston.mainserver.repository;

import com.capston.mainserver.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em; //JPA는 엔티티 매니저로 사용, 스프링 부트가 모든 정보를 취합해서 엔티티 매니저를 생성해줌.

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findBymemberId(Long member_id) {
        Member member = em.find(Member.class, member_id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String memberName) {
        List<Member> result = em.createQuery("select m from Member m where m.memberName = :member_name", Member.class)
                .setParameter("member_name", memberName)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> removeBymemberId(Long member_id) {
        Optional<Member> member = findBymemberId(member_id);
        member.ifPresent(m -> em.remove(m));
        return member;
    }
}
