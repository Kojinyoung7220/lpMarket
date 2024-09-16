package com.lpMarket.web.request;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 컨트롤러 단에서 쓰이는 dto, 새 게시물 작성.
 */
@Getter
@Setter
@NoArgsConstructor
public class PostForm {


    private Long id;

    @NotBlank(message = "제목 작성은 필수입니다.")
    @Size(max = 200, message = "제목은 최대 200자까지 입력 가능합니다.")
    private String title;

    @NotEmpty(message = "내용 작성은 필수입니다.")
    @Size(max = 1000, message = "내용은 최대 1000자까지 입력 가능합니다.")
    private String content;

    private String author;

    private Long memberId;  // 작성자의 ID를 저장

    @Builder
    public PostForm(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
