package com.demand.management.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demand.management.dao.mapper.ApproverMapper;
import com.demand.management.dto.approver.DemandTypeApproverItem;
import com.demand.management.dto.approver.RelationApprover;
import com.demand.management.entity.Approver;
import com.demand.management.service.ApproverService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApproverServiceImpl extends ServiceImpl<ApproverMapper, Approver> implements ApproverService {

    @Override
    public List<RelationApprover> findByDemandByWhere(String demandTypeId, String demandStatusId, String userId) {
        return this.baseMapper.findByDemandByWhere(demandTypeId, demandStatusId, userId);
    }

    @Override
    @Transactional
    public void updateApproverList(String demandTypeId, List<DemandTypeApproverItem> approverList) {
        UpdateWrapper<Approver> wrapper = new UpdateWrapper<>();
        wrapper.eq("demandTypeId", demandTypeId);
        this.baseMapper.delete(wrapper);
        if (approverList != null && approverList.size() > 0) {
            List<Approver> list = new ArrayList<Approver>();
            approverList.forEach(approver -> {
                if (approver.getDemandStatusId() != null && approver.getApprovers() != null && approver.getApprovers().size() > 0) {
                    approver.getApprovers().forEach(user -> {
                        Approver instance = new Approver();
                        instance.setDemandTypeId(demandTypeId);
                        instance.setDemandStatusId(approver.getDemandStatusId());
                        instance.setUserId(user);
                        list.add(instance);
                    });
                }
            });
            this.saveBatch(list);
        }
    }
}
