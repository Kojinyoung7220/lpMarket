package com.lpMarket.domain.community;

import com.lpMarket.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Comment(String content) {
        this.content = content;
        createAt = LocalDateTime.now();
    }

    /**
     * 연관관계 메서드
     */
    public void setMember(Member member){
        this.member = member;
        member.getComments().add(this);

    }
    public void setPost(Post post){
        this.post = post;
        post.getComments().add(this);
    }

    /**
     * 생성메서드
     */
    public static Comment createComment(String content, Member member, Post post){
        Comment comment = Comment.builder()
                .content(content)
                .build();
        comment.setMember(member);
        comment.setPost(post);
        return comment;
    }
}
