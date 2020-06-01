package com.demand.management.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demand.management.dto.demand.DemandStatusBodyReq;
import com.demand.management.dto.demand.RelationDemandStatus;
import com.demand.management.entity.DemandStatus;

public interface DemandStatusService extends IService<DemandStatus> {
    RelationDemandStatus create(DemandStatusBodyReq body);
    RelationDemandStatus findById(String id);
    Page<RelationDemandStatus> findAll(String pageIndex, String pageSize, String isOn);
    boolean updateById(String id, DemandStatusBodyReq body);
}
