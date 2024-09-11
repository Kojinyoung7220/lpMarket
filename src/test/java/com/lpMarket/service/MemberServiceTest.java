package com.lpMarket.service;

import com.lpMarket.domain.Member;
import com.lpMarket.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;


    @Test
    public void 회원가입_성공_테스트(){
        //given
        Member member = Member.builder()
                .name("memberA")
                .email("jim000111@naver.com")
                .build();

        //when
        Long saveId = memberService.join(member);

        //then
        Assertions.assertEquals(saveId, 1L);
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    public void 회원가입_중복_실패_테스트(){
        //given
        Member member = Member.builder()
                .name("member")
                .email("jim000111@naver.com")
                .build();
        Member member1 = Member.builder()
                .name("member")
                .email("jim000111@naver.com")
                .build();
        memberService.join(member);

        //expected
        Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(member1));
    }
}