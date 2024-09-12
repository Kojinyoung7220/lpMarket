package com.lpMarket.controller;

import com.lpMarket.domain.Member;
import com.lpMarket.domain.community.Post;
import com.lpMarket.service.PostService;
import com.lpMarket.web.request.PostForm;
import com.lpMarket.web.request.PostSearch;
import com.lpMarket.web.SessionConst;
import com.lpMarket.web.request.UpdatePostForm;
import com.lpMarket.web.response.PostResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/community")
    public String communityHome(@ModelAttribute PostSearch postSearch, Model model) {
        postSearch.validate(); // null 체크 및 기본값 설정
        List<PostResponse> list = postService.getList(postSearch);

        // 총 게시글 수를 가져와서 페이지 수 계산 (예시로 getTotalCount() 사용)
        long totalPosts = postService.getTotalCount();
        int totalPages = (int) Math.ceil((double) totalPosts / postSearch.getSize());

        model.addAttribute("posts", list);
        model.addAttribute("postSearch", postSearch); // 검색 및 페이징 정보를 뷰로 전달
        model.addAttribute("totalPages", totalPages); // 총 페이지 수 전달
        return "community/communityHome";
    }

    @GetMapping("/board/new")
    public String postHome(Model model, HttpSession session) {
//        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
//
//        PostForm postForm = PostForm.builder()
//                .author(member.getName())
//                .build();
//        postForm.setMemberId(member.getId());

        model.addAttribute("postForm", new PostForm());
        return "community/newPost";
    }

    @PostMapping("/board/new")
    public String createPost(@Valid @ModelAttribute("postForm") PostForm postForm, BindingResult bindingResult, HttpSession session) {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (bindingResult.hasErrors()) {
            return "community/newPost";
        }
//        postService.save(postForm, member);
        postService.makePost(postForm.getTitle(),postForm.getContent(), member.getId());

        return "redirect:/community";

    }

    @GetMapping("/board/{postId}")
    public String postView(@PathVariable("postId") Long postId, Model model, HttpSession session){
        Post post = postService.findOne(postId);
        Post post1 = postService.countView(post);

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);  // 세션에서 로그인한 회원 가져오기
        boolean liked = false;

        if (loginMember != null) {
            liked = postService.isLikedByMember(loginMember.getId(), postId);
        }

        model.addAttribute("loginMember", loginMember);
        model.addAttribute("post", post1);
        model.addAttribute("liked", liked);  // 좋아요 여부 추가

        return "community/postView";
    }

    @PostMapping("/board/{postId}/like")
    public String likePost(@PathVariable("postId") Long postId, HttpSession session) {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);  // 세션에서 현재 로그인한 사용자 가져오기
        if (member == null) {
            // 로그인하지 않은 사용자가 접근할 경우 처리
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }
        postService.likePost(member.getId(), postId);  // 좋아요 서비스 호출

        // 현재 게시물 상세 페이지로 리다이렉트
        return "redirect:/board/" + postId;
    }


    @GetMapping("/board/edit/{postId}")
    public String updatePost(@PathVariable("postId") Long postId, Model model){
        Post post = postService.findOne(postId);

        UpdatePostForm updatePostForm = PostToUpdatePostForm(post);

        model.addAttribute("updatePostForm", updatePostForm);

        return "community/updatePost";
    }

    @PostMapping("/board/edit/{postId}")
    public String updatePost(@Valid @ModelAttribute("updatePostForm") UpdatePostForm form, BindingResult result) {

        if(result.hasErrors()){
            return "community/updatePost";
        }

        postService.postUpdate(form.toUpdatePostDto());

        return "redirect:/community";
    }

    @PostMapping("/board/delete/{postId}")
    public String deletePost(@PathVariable("postId") Long postId){
        postService.deletePost(postId);
        return "redirect:/community";
    }


//    private Post formToPost(PostForm postForm, Member member) {
//        Post post = Post.builder()
//                .title(postForm.getTitle())
//                .content(postForm.getContent())
//                .build();
//        post.setMember(member);
//        return post;
//    }

    private UpdatePostForm PostToUpdatePostForm(Post post) {
        return UpdatePostForm.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getMember().getName()) //문제가 생겼나..? -> post에서 member를 가니깐 n+1이 생기나?
                .build();
    }

}
