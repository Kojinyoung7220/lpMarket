package com.lpMarket.controller;

import com.lpMarket.domain.Member;
import com.lpMarket.web.SessionConst;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    /**
     *   header에 이름이 생기게 만드는 메서드.
     */
    @ModelAttribute("memberName")
    public String addMemberNameToModel(HttpSession session) {
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (loginMember != null) {
            return loginMember.getName(); // 세션에 저장된 회원 이름 반환
        }
        return null; // 로그인하지 않았으면 null 반환
    }
}

