package com.demand.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demand.management.dto.demand.RelationDemandNode;
import com.demand.management.entity.DemandNode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DemandNodeMapper extends BaseMapper<DemandNode> {
    RelationDemandNode findById(String id);
    List<RelationDemandNode> findByPage(Page<RelationDemandNode> page, @Param("demandProgress") String demandProgress, @Param("user") String user);
}
