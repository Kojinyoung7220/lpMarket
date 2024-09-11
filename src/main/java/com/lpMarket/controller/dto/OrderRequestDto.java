package com.lpMarket.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {

    @NotNull(message = "회원 입력은 필수입니다.")
    private Long memberId;

    @NotNull(message = "상품 입력은 필수입니다.")
    private Long itemId;

    @NotNull(message = "수량 입력은 필수입니다.")
    @Min(value = 1)
    private Integer count;


}
