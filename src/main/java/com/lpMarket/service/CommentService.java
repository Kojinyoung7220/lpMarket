package com.lpMarket.service;

import com.lpMarket.domain.Member;
import com.lpMarket.domain.community.Comment;
import com.lpMarket.domain.community.Post;
import com.lpMarket.repository.PostRepository;
import com.lpMarket.repository.dataJpa.CommentRepository;
import com.lpMarket.repository.dataJpa.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public void saveComment(Comment comment){
        commentRepository.save(comment);
    }

    /**
     *  댓글 추가
     */
    public Long addComment(String content, Long memberId, Long postId){
        Member member = memberRepository.findById(memberId).orElseThrow();
        Post post = postRepository.findOne(postId);

        Comment comment = Comment.createComment(content, member, post);

        commentRepository.save(comment);
        return comment.getId();
    }

    /**
     * 댓글 삭제
     *
     */
    public Long deleteComment(Long commentId, Long memberId) throws AccessDeniedException {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new NoSuchElementException("해당 댓글을 찾을 수 없습니다."));

        if(!comment.getMember().getId().equals(memberId)){
            throw new AccessDeniedException("삭제 권한이 없습니다."); //다른 걸로 변경하자~~!
        }

        Long id = comment.getPost().getId();

        commentRepository.delete(comment);

        return id;
    }


}
