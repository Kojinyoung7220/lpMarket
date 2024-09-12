package com.lpMarket.domain.community;

import com.lpMarket.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor  // 기본 생성자 추가
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;


    /**
     * 연관관계 편의 메서드
     */
    public void setMember(Member member){
        this.member = member;
        member.getHearts().add(this);
    }

    public void setPost(Post post){
        this.post = post;
        post.getHearts().add(this);
    }

    /**
     * 생성메서드 ->팩토리 메서드
     */
    public static Heart createHeart(Member member, Post post){
        Heart heart = new Heart();
        heart.setMember(member);
        heart.setPost(post);
        return heart;
    }

//    // 상태 전환 메서드 ??????
//    public void toggleStatus() {
//        if (this.status == LikeStatus.LIKE) {
//            this.status = LikeStatus.UNLIKE;
//        } else {
//            this.status = LikeStatus.LIKE;
//        }
//    }
}
