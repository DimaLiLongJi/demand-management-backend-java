package com.demand.management.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demand.management.dao.mapper.FileMapper;
import com.demand.management.entity.File;
import com.demand.management.service.FileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {
    static private String uploadFilePath = "./static/";

    @Override
    public String writeFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();

        if (fileName == null) return null;

        int lastDot = fileName.lastIndexOf(".");
        String suffix = lastDot == -1 ? "" : fileName.substring(lastDot);
        String fileRealyName =  lastDot == -1 ? fileName : fileName.substring(0, lastDot);

        java.io.File target = new java.io.File(FileServiceImpl.uploadFilePath, fileRealyName + "." + UUID.randomUUID().toString() + suffix);
        try {
            this.saveFile(file, target);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return target.getName();
    }

    public void saveFile(MultipartFile multipartFile, java.io.File target) throws IOException {
        if (target.getParentFile() != null && !target.getParentFile().exists()) {
            target.getParentFile().mkdirs();
        }

        InputStream is = multipartFile.getInputStream();

        try {
            Files.copy(is, target.toPath());
        } finally {
            IOUtils.closeQuietly(is);
        }

    }
}
