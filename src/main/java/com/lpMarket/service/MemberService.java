package com.lpMarket.service;

import com.lpMarket.domain.Member;
import com.lpMarket.exception.ExistingMemberException;
import com.lpMarket.repository.dataJpa.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = false)
    public Long join(Member member) {
        // 중복회원 검증
        memberRepository.findByEmail(member.getEmail())
                .ifPresent((member1) -> {throw new ExistingMemberException("이미 존재하는 회원입니다.");});

        memberRepository.save(member);
        return member.getId();
    }


    /**
     * 회원조회
     */
    public Optional<Member> findMemberOne(Long id){
        return memberRepository.findById(id);
    }

    public List<Member> findMemberAll(){
        return memberRepository.findAll();
    }
}
