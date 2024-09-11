package com.lpMarket.service;

import com.lpMarket.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Transactional
public class LoginServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    LoginService loginService;

    @Test
    void 로그인_성공(){

        //given
        Member member = Member.builder()
                .name("member")
                .email("jim000111@naver.com")
                .password("1!Aaaaaaa")
                .build();
        memberService.join(member);

        //when
        Member loginMember = loginService.login(member.getEmail(), member.getPassword());

        //then
        Assertions.assertEquals(loginMember, member);
    }

    @Test
    void 로그인_실패(){
        //given
        Member member = Member.builder()
                .name("member")
                .email("jim000111@naver.com")
                .password("1!Aaaaaaa")
                .build();

        memberService.join(member);

        //when
        Member loginMember = loginService.login(member.getEmail(), "1");

        // then
        assertNull(loginMember); // 로그인 실패 시 null이어야 함
    }
}
