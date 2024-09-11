package com.lpMarket.web.request;

import com.lpMarket.service.dto.UpdateItemDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * //아이템폼은 컨트롤러까지만 사용하자 처음부터 dto로 잡고 시작해도 되지만
 *  UI를 처리하기 위한 데이터들은 ui를 처리하기 위한 데이터로 남겨둬야지 나중에 사이즈가 켜졌을때 유지보수 하기 쉬움.
 *  하지만 애플리케이션이 단순하다면 그냥 Form을 DTO로 취급해서 넘겨도됨.
 *  보통 Form은 컨트롤러 까지만 사용하는 것을 지향하자
 */
@Getter
@Setter
@NoArgsConstructor
public class ItemForm {

    private Long id;

    @NotEmpty(message = "상품 이름은 필수입니다.")
    private String name;

    @NotNull(message = "가격은 필수입니다.")
    @Min(value = 1000, message = "가격은 최소 1000원 이상이어야 합니다.")
    private Integer price;

    @NotNull(message = "수량은 필수입니다.")
    @Min(value = 1, message = "수량은 최소 1개 이상이어야 합니다.")
    private Integer stockQuantity;

    private String genre;
    private String era;
    private String artist;

    @Builder
    public ItemForm(Long id, String name, int price, int stockQuantity, String genre, String era, String artist) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.genre = genre;
        this.era = era;
        this.artist = artist;
    }

    //업데이트 dto로 변환하는 메서드
    public UpdateItemDto toUpdateItemDto(){
        return UpdateItemDto.builder()
                .id(id)
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .genre(genre)
                .era(era)
                .artist(artist)
                .build();
    }
}
