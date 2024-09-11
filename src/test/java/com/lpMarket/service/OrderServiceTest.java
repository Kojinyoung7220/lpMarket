package com.lpMarket.service;

import com.lpMarket.domain.*;
import com.lpMarket.exception.NotEnoughStockException;
import com.lpMarket.repository.ItemRepository;
import com.lpMarket.repository.MemberRepository;
import com.lpMarket.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    EntityManager em;

    @Autowired
    OrderRepository orderRepository;
    @Test
    void 주문성공(){
        //given
        Member member = Member.builder()
                .name("jin")
                .address(new Address("LV", "금동로", "1234"))
                .email("123213")
                .build();
        em.persist(member);


        Item item = Item.builder()
                .stockQuantity(10)
                .price(10000)
                .name("포말한정엘범 팝니다.")
                .build();
        em.persist(item);

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), 2);

        Order order = orderRepository.findOne(orderId);

        //then
        assertEquals(8, item.getStockQuantity());
        assertEquals(OrderStatus.ORDER, order.getStatus());
        assertEquals(1, order.getOrderItems().size());
        assertEquals(20000, order.getTotalPrice());
    }
    @Test
    void 주문실패_재고수량초과(){
        //given
        Member member = Member.builder()
                .name("jin")
                .address(new Address("LV", "금동로", "1234"))
                .email("123213")
                .build();
        em.persist(member);


        Item item = Item.builder()
                .stockQuantity(0)
                .price(10000)
                .name("포말한정엘범 팝니다.")
                .build();
        em.persist(item);

        //expected
        Assertions.assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), item.getId(), 2));
    }

    @Test
    void 주문취소(){
        //given
        Member member = Member.builder()
                .name("jin")
                .address(new Address("LV", "금동로", "1234"))
                .email("123213")
                .build();
        em.persist(member);


        Item item = Item.builder()
                .stockQuantity(10)
                .price(10000)
                .name("포말한정엘범 팝니다.")
                .build();
        em.persist(item);

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), 2);

        orderService.orderCancel(orderId);

        Order order = orderRepository.findOne(orderId);

        //then
        assertEquals(10, item.getStockQuantity());
        assertEquals(OrderStatus.CANCEL, order.getStatus());
    }
}