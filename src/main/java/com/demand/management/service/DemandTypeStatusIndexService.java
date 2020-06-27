package com.demand.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demand.management.dto.demand.DemandTypeStatusIndexReqItem;
import com.demand.management.dto.demand.RelationDemandTypeStatusIndex;
import com.demand.management.entity.DemandTypeStatusIndex;

import java.util.List;

public interface DemandTypeStatusIndexService extends IService<DemandTypeStatusIndex> {
    List<RelationDemandTypeStatusIndex> findByDemandTypeId(String demandTypeId);
    List<RelationDemandTypeStatusIndex> createIndexList(String demandTypeId, List<DemandTypeStatusIndexReqItem> body);
}
