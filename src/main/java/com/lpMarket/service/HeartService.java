package com.lpMarket.service;

import com.lpMarket.domain.Member;
import com.lpMarket.domain.community.Heart;
import com.lpMarket.domain.community.Post;
import com.lpMarket.repository.HeartRepository;
import com.lpMarket.repository.MemberRepository;
import com.lpMarket.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /**
     * 좋아요 기능
     */
    @Transactional
    public void likePost(Long memberId, Long postId) {
        Post post = postRepository.findOne(postId);
        Heart existingHeart  = heartRepository.findHeartByMemberAndPost(memberId, postId);

        if (existingHeart != null) {
            // 좋아요 취소 -> Heart 엔티티 삭제
            heartRepository.delete(existingHeart);
            post.decrementLikeCount();  // 좋아요 수 감소
        } else {
            // 좋아요 추가
            Member member = memberRepository.findOne(memberId);
            Heart newHeart = Heart.createHeart(member, post);
            heartRepository.save(newHeart);
            post.incrementLikeCount();  // 좋아요 수 증가
        }
        postRepository.save(post);
    }
    @Transactional(readOnly = true)
    public boolean isLikedByMember(Long memberId, Long postId) {
        return heartRepository.findHeartByMemberAndPost(memberId, postId) != null;
    }
}
