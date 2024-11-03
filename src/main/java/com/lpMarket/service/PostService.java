package com.lpMarket.service;

import com.lpMarket.domain.Member;
import com.lpMarket.domain.community.Post;
import com.lpMarket.exception.ExistingMemberException;
import com.lpMarket.repository.PostRepository;
import com.lpMarket.repository.dataJpa.MemberRepository;
import com.lpMarket.web.request.PostSearch;
import com.lpMarket.web.request.UpdatePostDto;
import com.lpMarket.web.response.PostResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;


    @Transactional(readOnly = false)
    public Long save(Post post){
        postRepository.save(post);
        return post.getId();
    }

//    @Transactional
//    public Long save(PostForm postForm, Member member) {
//        Member findMember = memberRepository.findOne(member.getId()); //db에서 직접 조회!
//        // Post 엔티티로 변환 및 연관 관계 설정
//        Post post = Post.builder()
//                .title(postForm.getTitle())
//                .content(postForm.getContent())
//                .build();
//
//        post.setMember(findMember);  // 연관 관계 설정 (여기서 지연 로딩된 member의 posts 컬렉션에 접근)
//
//        postRepository.save(post);  // 엔티티 저장
//        return post.getId();
//    }
    @Transactional(readOnly = false)
    public Long makePost(String title, String content, Long memberId){  //객체넘기는거 id넘기는거 확인해보자.
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new ExistingMemberException("PostService 에러")); //이렇게 member를 먼저 조회하면 LazyInitializationException이 해결될까?

        Post post = Post.createPost(title, content, findMember);

        postRepository.save(post);
        return post.getId();
    }

    public List<Post> findAllPost(){
        return postRepository.findAll();
    }

    public Post findOne(Long id){
        return postRepository.findOne(id);
    }

    //페이징 처리 해줘야 함
    public List<PostResponse> getList(PostSearch postSearch){
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
        //        return postRepository.getList(postSearch).stream().toList();
    }

    public long getTotalCount() {
        return postRepository.getTotalCount();
    }

    @Transactional
    public Post countView(Post post){
        post.setViewCount(post.getViewCount() + 1);
        return post;
    }

    /**
     * 수정
     */
    @Transactional(readOnly = false)
    public void postUpdate(UpdatePostDto updatePostDto) {
        Post post = postRepository.findOne(updatePostDto.getId());
        post.changePost(updatePostDto.getTitle(), updatePostDto.getContent());
    }

    /**
     * 삭제
     */
    @Transactional
    public void deletePost(Long postId){
        Post post = postRepository.findOne(postId);
        if(post == null){
            throw new EntityNotFoundException("해당 게시물이 존재하지 않습니다.");
        }else {
            postRepository.delete(postId);
        }
    }
}
