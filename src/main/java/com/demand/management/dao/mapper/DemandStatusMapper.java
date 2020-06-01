package com.demand.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demand.management.dto.demand.RelationDemandStatus;
import com.demand.management.entity.DemandStatus;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DemandStatusMapper extends BaseMapper<DemandStatus> {
    RelationDemandStatus findById(@Param("id") String id);
    List<RelationDemandStatus> findByPage(Page<RelationDemandStatus> page, @Param("isNull") String isNull);
}
