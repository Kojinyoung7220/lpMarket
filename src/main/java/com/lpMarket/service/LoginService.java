package com.lpMarket.service;

import com.lpMarket.domain.Member;
import com.lpMarket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;


    public Member login(String loginEmail, String password){
        Member member = memberRepository.findByEmail(loginEmail);

        if(member != null && member.getPassword().equals(password)){
            return member;
        }else {
            return null;
        }
    }

}
