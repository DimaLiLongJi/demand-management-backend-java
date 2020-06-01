package com.demand.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demand.management.dto.permission.RelationPermission;
import com.demand.management.entity.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface PermissionMapper extends BaseMapper<Permission>  {
    RelationPermission findById(@Param("id") String id);
    RelationPermission findOneByWhere(@Param("where") Map<String, Object> where);
    List<RelationPermission> findByPage(Page<RelationPermission> page, @Param("type") String type, @Param("isNull") String isNull);
}
