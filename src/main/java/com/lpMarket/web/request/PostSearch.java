package com.lpMarket.web.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Setter
@Getter
@Builder
public class PostSearch {

    private static final int MAX_SIZE = 2000;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;

    public long getOffset(){
        return (long)(max(1, page) - 1 )* min(size, MAX_SIZE);
    }
    public int getSize() {
        return Math.min(size, MAX_SIZE);
    }

    // page가 null일 때 기본값 1로 처리
    private int getSafePage() {
        return (page != null) ? page : 1;
    }

    public void validate() {
        if (size == null || size < 1) {
            size = 10;  // 기본값 설정
        }
        if (page == null || page < 1) {
            page = 1;   // 기본값 설정
        }
    }
}
