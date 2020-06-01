package com.demand.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demand.management.dto.approver.DemandTypeApproverItem;
import com.demand.management.dto.approver.RelationApprover;
import com.demand.management.entity.Approver;

import java.util.List;

public interface ApproverService extends IService<Approver> {
    List<RelationApprover> findByDemandByWhere(String demandTypeId, String demandStatusId, String userId);
    void updateApproverList(String demandTypeId, List<DemandTypeApproverItem> approverList);
}
