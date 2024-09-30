package com.lpMarket.domain.file;

import com.lpMarket.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    /**
     * 파일을 저장할 경로를 반환하는 메서드입니다. @Value로 주입받은 file.dir 값(저장 경로)과 파일명을 결합하여, 파일의 전체 경로를 생성합니다.
     */
    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    /**
     * 여러 개의 파일을 리스트로 받아서 각각의 파일을 저장한 후 UploadFile 객체 리스트로 반환합니다. 빈 파일은 무시합니다.
     */
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    /**
     *  단일 파일을 저장하고, 파일 이름 충돌을 방지하기 위해 UUID로 파일명을 고유하게 만들어서 저장합니다.
     *  저장된 파일의 원본 파일명과 저장 파일명을 담은 UploadFile 객체를 반환합니다
     */
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadFile(originalFilename, storeFileName);
    }

    /**
     * 파일의 이름 충돌을 방지하기 위해 UUID를 생성하여 고유한 파일명을 만들어줍니다.
     * 파일의 확장자(ex: .jpg, .png)는 유지하고, 파일명만 고유하게 만듭니다.
     */
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    /**
     * 파일의 확장자를 추출하는 메서드입니다.
     * 파일명에서 마지막에 위치한 .(점) 이후의 문자열을 추출하여 확장자로 반환합니다.
     */
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}


