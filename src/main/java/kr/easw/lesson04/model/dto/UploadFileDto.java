package kr.easw.lesson04.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class UploadFileDto {
    @Getter
    private final MultipartFile file;
}
