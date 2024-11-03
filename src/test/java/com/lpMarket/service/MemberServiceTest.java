package com.lpMarket.service;

import com.lpMarket.domain.Member;
import com.lpMarket.exception.ExistingMemberException;
import com.lpMarket.repository.dataJpa.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        assertEquals(member, memberRepository.findById(saveId).orElseThrow());
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
        Assertions.assertThrows(ExistingMemberException.class, () -> memberService.join(member1));
    }
}