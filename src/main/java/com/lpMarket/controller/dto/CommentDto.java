package com.lpMarket.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    @NotEmpty(message = "내용 작성은 필수입니다.")
    @Size(max = 500, message = "댓글은 500자 이내로 작성해주세요.")
    private  String comment;

}
