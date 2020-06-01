package com.demand.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demand.management.dto.role.RelationRole;
import com.demand.management.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoleMapper extends BaseMapper<Role> {
    RelationRole findById(@Param("id") String id);
    List<RelationRole> findByPage(Page<RelationRole> page, @Param("isNull") String isNull);
}
