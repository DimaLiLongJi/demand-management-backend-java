package com.demand.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demand.management.entity.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService extends IService<File> {
    String writeFile(MultipartFile file);
    void saveFile(MultipartFile multipartFile, java.io.File target) throws IOException;
}
