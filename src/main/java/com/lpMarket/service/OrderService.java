package com.lpMarket.service;

import com.lpMarket.domain.*;
import com.lpMarket.repository.ItemRepository;
import com.lpMarket.repository.MemberRepository;
import com.lpMarket.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count){ //여기에 id를 넘기는게 좋나? 객체를 넘기는게 좋나

        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery =Delivery.builder()
                .address(member.getAddress())
                .status(DeliveryStatus.READY)
                .build();

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);  // 이거 하나만 저장을 해도 cascade = CascadeType.ALL 이거 때문에 오더 아이템이랑 딜리버리가 다 저장이 자동으로 됨.
        return order.getId();
    }

    //주문 번호로 취소..
    @Transactional
    public void orderCancel(Long orderId){

        Order order = orderRepository.findOne(orderId);
        order.cancel();

    }

    public List<Order> findOrder(OrderSearch orderSearch){
        return orderRepository.findAllByString(orderSearch);

    }
}
