package com.lpMarket.service;

import com.lpMarket.domain.Member;
import com.lpMarket.exception.ExistingMemberException;
import com.lpMarket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = false)
    public Long join(Member member) {
        // 중복회원 검증
        if (memberRepository.findByEmail(member.getEmail()) != null) {
            throw new ExistingMemberException("이미 존재하는 회원입니다.");
        }
        memberRepository.save(member);
        return member.getId();
    }


    /**
     *  회원조회
     */
    public Member findMemberOne(Long id){
        return memberRepository.findOne(id);
    }

    public List<Member> findMemberAll(){
        return memberRepository.findAll();
    }


}
