package com.lpMarket.web.response;

import com.lpMarket.domain.community.Post;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 서비스 정책에 맞는 클래스 -> 만약 요구사항으로 타이틀의 글자를 10글자로 제한 시켜 달라.
 *                          그러면 getter메서드 안에 로직을 작성해 제한하면 모든 로직들이 30글자로 제한 하였음.
 *                          각각의 요구사항에 맞게 규칙을 정하기 위해 PostResponse 클래스로 분리 하였음,!!
 */
@Getter
public class PostResponse {

    private final Long id;

    private final String title;

    private final String content;

    private final String author;

    private final LocalDateTime createAt;

    private final LocalDateTime updatedAt;

    private boolean isUpdated = false;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getMember().getName();
        this.createAt = post.getCreateAt();
        this.updatedAt = post.getUpdatedAt();
        this.isUpdated = post.isUpdated();
    }

    public PostResponse(Long id, String title, String content, String author, LocalDateTime updateAt) {
        this.id = id;
        this.title = title.substring(0, Math.min(title.length(), 30));
        this.content = content;
        this.author = author;
        this.updatedAt = updateAt;
        this.createAt = LocalDateTime.now();
    }
}
