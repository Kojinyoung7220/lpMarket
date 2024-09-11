package com.lpMarket.service;

import com.lpMarket.domain.community.Post;
import com.lpMarket.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;

    @Test
    void 게시글_작성_성공(){
        Post post = Post.builder()
                .title("안녕하세요")
                .content("14141414")
                .build();

        postService.save(post);

        Assertions.assertEquals(1L, postRepository.count());
        Post post1 = postService.findAllPost().get(0);
        Assertions.assertEquals("안녕하세요", post1.getTitle());
        Assertions.assertEquals("14141414", post1.getContent());
    }

    @Test
    void 게시글_작성_실패(){
        Post post = Post.builder()
                .content("14141414")
                .build();

        postService.save(post);

    }
}