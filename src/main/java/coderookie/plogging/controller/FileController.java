package coderookie.plogging.controller;

import coderookie.plogging.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "File Controller", description = "사진 업로드 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @Operation(summary = "서버에 사진 업로드하기",
            description = "Form-Data로 사진을 업로드하고 URL을 돌려받음")
    @PostMapping("/upload")
    public String upload(@RequestParam(value = "file") MultipartFile file
    ) {
        return fileService.upload(file);
    }

    @Operation(summary = "서버에서 사진 가져오기",
            description = "path로 주어진 URL을 이용하여 JPEG 또는 PNG 형식의 이미지를 돌려받음")
    @GetMapping(value = "/{fileName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public Resource getImage(@PathVariable("fileName") String fileName
    ) {
        return fileService.getImage(fileName);
    }

}
