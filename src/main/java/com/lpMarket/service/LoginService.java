package com.lpMarket.service;

import com.lpMarket.domain.Member;
import com.lpMarket.repository.dataJpa.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;


    public Member login(String loginEmail, String password){
        return memberRepository.findByEmail(loginEmail)
                .filter(member -> member.getPassword().equals(password))
                .orElseThrow(null);
        }
    }

