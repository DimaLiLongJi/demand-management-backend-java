package com.demand.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demand.management.dto.demand.RelationDemandType;
import com.demand.management.entity.DemandType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DemandTypeMapper extends BaseMapper<DemandType> {
    RelationDemandType findById(@Param("id") String id);
    List<RelationDemandType> findByPage(Page<RelationDemandType> page, @Param("isNull") String isNull);
}
