package com.lpMarket.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Size(max = 200, message = "제목은 최대 200자까지 입력 가능합니다.")
    private String title;

    @NotBlank(message = "내용 작성은 필수입니다.")
    @Size(max = 1000, message = "내용은 최대 1000자까지 입력 가능합니다.")
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