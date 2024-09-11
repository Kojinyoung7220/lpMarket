package com.lpMarket.domain;

import com.lpMarket.domain.community.Comment;
import com.lpMarket.domain.community.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id") // TODO: 2024-08-19  데이터베이스 erd도 그리자
    private Long id; //column 안적으면 그냥 id가 이름이 됨.

    private String password;

    private String name;

    @Embedded
    private Address address;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "member")             // cascade = CascadeType.ALL을 왜 안썼지..?
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL) //fetch = FetchType.EAGER 쓰지말고 해결해보자... // orphanRemoval = true도 제거했음!
    private List<Post> posts = new ArrayList<>();

//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Member(String name, String password, Address address, String email) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.email = email;
    }
}
