package com.capston.mainserver.service;

import com.capston.mainserver.domain.Member;
import com.capston.mainserver.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Test
    public void 회원가입() throws Exception {
        //Given
        Member member = new Member();
        member.setMemberName("hello");
        //When
        Long saveId = memberService.join(member);

        //Then
        Member findMember = memberRepository.findBymemberId(saveId).get();
        assertEquals(member.getMemberName(), findMember.getMemberName());
    }
    @Test
    public void 중복회원예외() throws Exception {
        //Given
        Member member1 = new Member();
        member1.setMemberName("spring");
        Member member2 = new Member();
        member2.setMemberName("spring");

        //When
        memberService.join(member1);

        //Then
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("error: already exists member name");
    }

    @Test
    public void 닉네임수정() {
        //Given
        Member member = new Member();
        member.setMemberName("old name");
        memberRepository.save(member);

        //When
        memberService.modify(member.getMemberId(), "new name");

        //Then
        Optional<Member> result = memberRepository.findBymemberId(member.getMemberId());
        assertTrue(result.isPresent());
        assertEquals("new name", result.get().getMemberName());
    }

    @Test
    public void 회원탈퇴() {
        //Given
        Member member = new Member();
        member.setMemberName("John");
        memberService.join(member);

        //When
        Optional<Member> removedMember = memberService.remove(member.getMemberId());

        //Then
        assertThat(removedMember).isNotEmpty();
        assertThat(removedMember.get().getMemberId()).isEqualTo(member.getMemberId());
        assertThat(memberService.findOne(member.getMemberId())).isEmpty();
    }


}