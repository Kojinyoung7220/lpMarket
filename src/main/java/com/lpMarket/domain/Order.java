package com.lpMarket.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter @Setter
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order",  cascade = CascadeType.ALL) // TODO: 2024-08-20   cascade 알아보자
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) //fk를 어디에 둘지 고민 => 액세스를 많이 하는 곳에 두자.
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // enum

    @Builder
    public Order(Member member, Delivery delivery, LocalDateTime orderDate, OrderStatus status) {
        this.member = member;
        this.delivery = delivery;
        this.orderDate = orderDate;
        this.status = status;
    }

    /**
     * 연관관계 메서드
     */
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }


//1.주문을 하면 orderitem과 item이 생겨남 => 주문 생성
    //2.주문을 취소하면 아이템 수량이 적어짐
    //3.전체주문 가격을 계산해줘야됨.

    /**
     * 생성메서드
     */
    public static Order createOrder(Member member, Delivery delivery, OrderItem...orderItems){
        Order order = Order.builder()       //order생성
                .member(member)
                .delivery(delivery)
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.ORDER)
                .build();
        order.setDelivery(delivery);    //연관관계 생성
        order.setMember(member);        //연관관계 생성

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem); //연관관계 생성
        }
        return order;
    }

    /**
     * 주문을 취소하면 아이템 수량 증가
     */
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.status = OrderStatus.CANCEL;

        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    /**
     * 전체주문 가격을 계산해줘야됨.
     */
    public int getTotalPrice(){
        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }

}
