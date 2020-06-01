package com.demand.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demand.management.dto.module.RelationModule;
import com.demand.management.entity.Module;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ModuleMapper extends BaseMapper<Module> {
    RelationModule findById(@Param("id") String id);
    List<RelationModule> findByPage(Page<RelationModule> page);
}
