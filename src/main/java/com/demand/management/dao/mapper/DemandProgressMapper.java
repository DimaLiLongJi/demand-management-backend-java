package com.demand.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demand.management.dto.demand.RelationDemandProgress;
import com.demand.management.entity.DemandProgress;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DemandProgressMapper extends BaseMapper<DemandProgress> {
    RelationDemandProgress findById(@Param("id") String id);
    List<RelationDemandProgress> findByPage(Page<RelationDemandProgress> page, @Param("demandId") String demandId, @Param("userId") String userId);
}
