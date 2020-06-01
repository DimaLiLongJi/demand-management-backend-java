package com.demand.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demand.management.dto.file.RelationFile;
import com.demand.management.entity.File;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FileMapper extends BaseMapper<File> {
    RelationFile findById(String id);
    List<RelationFile> findByIds(List<String> ids);
}
