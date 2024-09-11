package com.lpMarket.web.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePostForm {

    private Long id;

    @NotBlank(message = "제목 작성은 필수입니다.")
    private String title;

    @NotBlank(message = "내용 작성은 필수입니다.")
    private String content;

    private String author;

    private LocalDateTime createAt; //수정된 날짜

    private boolean isUpdated = false;

    @Builder
    public UpdatePostForm(Long id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createAt = LocalDateTime.now();
    }

    public UpdatePostDto toUpdatePostDto(){
        return UpdatePostDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .author(author)
                .build();
    }

}