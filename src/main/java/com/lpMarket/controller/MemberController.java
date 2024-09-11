package com.lpMarket.controller;

import com.lpMarket.domain.Address;
import com.lpMarket.domain.Member;
import com.lpMarket.service.MemberService;
import com.lpMarket.web.request.MemberForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    /**
         * 메인("/main")
         *
         * 로그인 ->상품등록("/lp")
         * 사진, 이름, 상품가격,  -> 화면에 노출
         *
         * 상품수정("/") -> 처음에는 버튼 달아서 클릭해서 할 수 있게
         * 나중에는 나의 상품 창에서 자기의 상품 나열 후 거기서 수정
         *
         *
         * lpshop.com/collection-groups/~~
         * 조회("/market-~") -> 처음 홈화면에 노출된 상품들 검색(조건필수)
         *
         * 삭제는 수정이랑 비슷
         * @return
         */

    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String signUp(Model model){
        model.addAttribute("memberForm", new MemberForm());
        log.info("signUp controller");
        return "members/createMemberForm";
    }
    @PostMapping(value = "/members/new")
    public String createMember(@Valid MemberForm form, BindingResult result, HttpServletRequest request){

        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = Member.builder()
                .email(form.getEmail())
                .password(form.getPassword())
                .name(form.getName())
                .address(address)
                .build();

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping(value = "/members")
    public String memberList(Model model){

        List<Member> memberAll = memberService.findMemberAll();
        model.addAttribute("members", memberAll);

        return "members/memberList";
    }

}
