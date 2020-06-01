package com.demand.management.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demand.management.dto.demand.DemandProgressBodyReq;
import com.demand.management.dto.demand.RelationDemandProgress;
import com.demand.management.entity.DemandProgress;

public interface DemandProgressService extends IService<DemandProgress> {
    RelationDemandProgress create(DemandProgressBodyReq body);
    RelationDemandProgress findById(String id);
    Page<RelationDemandProgress> findAll(String pageIndex, String pageSize, String demandId, String userId);
    boolean updateById(String id, DemandProgressBodyReq body);
}
