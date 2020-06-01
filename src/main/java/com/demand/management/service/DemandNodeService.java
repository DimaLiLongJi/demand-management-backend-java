package com.demand.management.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demand.management.dto.demand.DemandNodeBodyReq;
import com.demand.management.dto.demand.RelationDemandNode;
import com.demand.management.entity.DemandNode;

public interface DemandNodeService extends IService<DemandNode> {
    RelationDemandNode create(DemandNodeBodyReq body);
    RelationDemandNode findById(String id);
    Page<RelationDemandNode> findAll(String pageIndex, String pageSize, String demandProgress, String user);
    boolean updateById(String id, DemandNodeBodyReq body);
    boolean delete(String id);
}
