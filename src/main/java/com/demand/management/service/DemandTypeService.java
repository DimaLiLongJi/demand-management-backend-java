package com.demand.management.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demand.management.dto.demand.DemandTypeBodyReq;
import com.demand.management.dto.demand.RelationDemandType;
import com.demand.management.dto.demand.RelationDemandTypeWithApprover;
import com.demand.management.entity.DemandType;

public interface DemandTypeService extends IService<DemandType> {
    RelationDemandType create(DemandTypeBodyReq body);
    RelationDemandTypeWithApprover findById(String id);
    Page<RelationDemandType> findAll(String pageIndex, String pageSize, String isOn);
    boolean updateById(String id, DemandTypeBodyReq body);
}
