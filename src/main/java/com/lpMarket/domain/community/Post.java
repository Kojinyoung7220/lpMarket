package com.lpMarket.domain.community;

import com.lpMarket.domain.Member;
import com.lpMarket.web.request.UpdatePostDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob //@Lob은 일반적인 데이터베이스에서 저장하는 길이인 255개 이상의 문자를 저장하고 싶을 때 지정한다. ->mysql에서 tinytext로 되어있는 거 확인.
    @Column(nullable = false, columnDefinition = "TEXT")  // 또는 "LONGTEXT"로 설정
    private String content;

    private LocalDateTime createAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Comment> comments = new ArrayList<>();

    private boolean isUpdated = false;


    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
        this.createAt = LocalDateTime.now();
    }

    /**
     * 연관관계 메서드??. //연관관계 편의 메서드는 유지보수하기 편한곳에 두자!!
     */
    public void setMember(Member member){
        this.member = member;
        member.getPosts().add(this);
    }


    /**
     *  생성 메서드
     *  정적 팩토리 메서드 패턴.
     */
    public static Post createPost(String title, String content,Member member){
        Post post = Post.builder()
                .title(title)
                .content(content)
                .build();

        post.setMember(member);
        return post;
    }

    public void changePost(String title, String content){
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now(); //나중에 수정됨 표시 해보자. -> 9/10일 완료
        this.isUpdated = true;
    }
}
