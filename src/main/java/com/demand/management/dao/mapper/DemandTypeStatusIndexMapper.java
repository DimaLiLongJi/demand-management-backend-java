package com.demand.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demand.management.dto.demand.RelationDemandTypeStatusIndex;
import com.demand.management.entity.DemandTypeStatusIndex;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DemandTypeStatusIndexMapper extends BaseMapper<DemandTypeStatusIndex> {
    List<RelationDemandTypeStatusIndex> findByDemandTypeId(@Param("demandTypeId") String demandTypeId);
}
