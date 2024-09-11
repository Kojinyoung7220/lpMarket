package com.lpMarket.controller;

import com.lpMarket.controller.dto.OrderRequestDto;
import com.lpMarket.domain.Item;
import com.lpMarket.domain.Member;
import com.lpMarket.domain.Order;
import com.lpMarket.domain.OrderSearch;
import com.lpMarket.service.ItemService;
import com.lpMarket.service.MemberService;
import com.lpMarket.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderService orderService;

    @GetMapping("/order")
    public String createForm(Model model){

        List<Member> members = memberService.findMemberAll();
        List<Item> items = itemService.findItems();

        model.addAttribute("members" , members);
        model.addAttribute("items", items);
        model.addAttribute("orderRequestDto", new OrderRequestDto()); // 여기에 orderRequestDto 추가

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@Valid @ModelAttribute("orderRequestDto") OrderRequestDto orderRequestDto, BindingResult bindingResult, Model model){ //알아서 매핑해준다..???

        if(bindingResult.hasErrors()){
            List<Member> members = memberService.findMemberAll();
            List<Item> items = itemService.findItems();
            model.addAttribute("members", members);
            model.addAttribute("items", items);
            return "order/orderForm";  // 오류 발생 시 폼 페이지로 이동
        }

        Long order = orderService.order(orderRequestDto.getMemberId(), orderRequestDto.getItemId(), orderRequestDto.getCount());

        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model){

        List<Order> orders = orderService.findOrder(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";

    }

    @PostMapping("/orders/{orderId}/cancel")
    public String orderCancel(@PathVariable("orderId") Long orderId){

        orderService.orderCancel(orderId);

        return "redirect:/orders";
    }
}
