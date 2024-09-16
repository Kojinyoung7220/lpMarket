package com.lpMarket.controller;

import com.lpMarket.controller.dto.CommentDto;
import com.lpMarket.domain.Member;
import com.lpMarket.domain.community.Post;
import com.lpMarket.service.CommentService;
import com.lpMarket.service.HeartService;
import com.lpMarket.service.PostService;
import com.lpMarket.web.SessionConst;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.nio.file.AccessDeniedException;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final HeartService heartService;

    @PostMapping("/board/{postId}/add")
    public String addComment(@PathVariable("postId") Long postId,
                             @Valid @ModelAttribute CommentDto commentDto,
                             BindingResult result,
                             HttpSession session,
                             Model model){

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (member == null) {
            return "redirect:/login"; // 또는 에러 메시지를 보여주는 페이지로 이동
        }

        if(result.hasErrors()){
            Post post = postService.findOne(postId);
            boolean liked = heartService.isLikedByMember(member.getId(), postId);

            model.addAttribute("loginMember", member);
            model.addAttribute("post", post);
            model.addAttribute("liked", liked);
            model.addAttribute("commentDto", commentDto); // 폼에 입력한 데이터 유지

            return "community/postView";
        }

        commentService.addComment(commentDto.getComment(), member.getId(), postId);

        return "redirect:/board/" + postId;
    }

    @PostMapping("/comments/delete/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId,
                                HttpSession session) throws AccessDeniedException {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (member == null) {
            return "redirect:/login";
        }

        Long postId = commentService.deleteComment(commentId, member.getId());


        // 삭제 후 현재 게시글 상세 페이지로 리다이렉트
        return "redirect:/board/" + postId;
    }

}
