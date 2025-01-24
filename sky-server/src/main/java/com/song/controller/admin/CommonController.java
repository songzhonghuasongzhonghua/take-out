package com.song.controller.admin;

import com.song.result.Result;
import com.song.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/admin/common")
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extend = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = UUID.randomUUID() + extend;
        try {
            String fileUrl = aliOssUtil.upload(file.getBytes(), filename);
            return Result.success(fileUrl);
        } catch (IOException e) {
            log.error("文件上传失败：{}",e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
