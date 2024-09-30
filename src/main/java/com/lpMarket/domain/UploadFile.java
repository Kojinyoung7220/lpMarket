package com.lpMarket.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Embeddable
public class UploadFile {

    /**
     * 업로드 파일 이름이 겹치면 중복이 돼서
     * 덮어쓰기 될 수 도 있기 때문에 storeFileName에는 uuid를 이용해 따로 저장할 수 있게 만들자.
     */

    private String uploadFileName; // 고객이 업로드한 파일명
    private String storeFileName; // 서버에 저장될 파일 이름 (UUID 등으로 변경)


    // public no-arg constructor (Lombok의 @Data는 기본적으로 생성하지 않음)
    public UploadFile() {}

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

}
