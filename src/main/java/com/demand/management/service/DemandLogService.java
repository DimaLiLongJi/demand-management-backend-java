package com.demand.management.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demand.management.dto.demand.DemandLogBodyReq;
import com.demand.management.dto.demand.RelationDemandLog;
import com.demand.management.entity.DemandLog;

public interface DemandLogService extends IService<DemandLog> {
    RelationDemandLog create(DemandLogBodyReq body);
    RelationDemandLog findById(String id);
    Page<RelationDemandLog> findAll(String pageIndex, String pageSize, String demand, String creator);
}
