package com.demand.management.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demand.management.dto.approver.DemandTypeApproverItem;
import com.demand.management.dto.approver.RelationApprover;
import com.demand.management.dto.demand.DemandTypeBodyReq;
import com.demand.management.dto.demand.RelationDemandType;
import com.demand.management.dto.demand.RelationDemandTypeWithApprover;
import com.demand.management.entity.DemandType;
import com.demand.management.dao.mapper.DemandTypeMapper;
import com.demand.management.service.ApproverService;
import com.demand.management.service.DemandTypeService;
import com.demand.management.service.DemandTypeDemandStatusService;
import com.demand.management.utils.CommonUtil;
import com.demand.management.utils.ExtendPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DemandTypeServiceImpl extends ServiceImpl<DemandTypeMapper, DemandType> implements DemandTypeService {
    @Autowired
    private DemandTypeDemandStatusService dtdsService;

    @Autowired
    private ApproverService approverService;

    @Override
    public RelationDemandType create(DemandTypeBodyReq body) {
        DemandType save = new DemandType();
        save.setName(body.getName());
        save.setCreatorId(body.getCreator());
        boolean res = this.save(save);
        if (res) {
            this.dtdsService.addRight(save.getId(), body.getDemandStatusIds());
            this.approverService.updateApproverList(save.getId(), body.getApproverList());
            return this.findById(save.getId());
        } else return null;
    }

    @Override
    public RelationDemandTypeWithApprover findById(String id) {
        RelationDemandType find = this.baseMapper.findById(id);

        RelationDemandTypeWithApprover findWithApprover = new RelationDemandTypeWithApprover();
        findWithApprover.setId(find.getId());
        findWithApprover.setName(find.getName());
        findWithApprover.setCreatorId(find.getCreatorId());
        findWithApprover.setCreateDate(find.getCreateDate());
        findWithApprover.setUpdateDate(find.getUpdateDate());
        findWithApprover.setDeleteDate(find.getDeleteDate());
        findWithApprover.setCreator(find.getCreator());
        findWithApprover.setDemandStatusList(find.getDemandStatusList());

        List<RelationApprover> approvers = this.approverService.findByDemandByWhere(id, null, null);

        List<DemandTypeApproverItem> approverList = new ArrayList<>();

        approvers.forEach(approve -> {
            DemandTypeApproverItem findApprover = null;

            for (DemandTypeApproverItem ap : approverList) {
                if (ap.getDemandStatusId().equals(approve.getDemandStatusId())) findApprover = ap;
            }

            if (findApprover != null) findApprover.getApprovers().add(approve.getUserId());
            else {
                DemandTypeApproverItem newItem = new DemandTypeApproverItem();
                newItem.setDemandStatusId(approve.getDemandStatusId());
                List<String> initApprovers = new ArrayList<>();
                initApprovers.add(approve.getUserId());
                newItem.setApprovers(initApprovers);
                approverList.add(newItem);
            }
        });

        findWithApprover.setApproverList(approverList);

        return findWithApprover;
    }

    @Override
    public Page<RelationDemandType> findAll(String pageIndex, String pageSize, String isOn) {
        ExtendPage<RelationDemandType> page = new ExtendPage<RelationDemandType>(pageIndex, pageSize);
        String isNull = CommonUtil.buildWithIsOn(isOn);
        return page.setTotalAndRecord(this.baseMapper.findByPage(page.getPageForFind(), isNull));
    }

    @Override
    public boolean updateById(String id, DemandTypeBodyReq body) {
        RelationDemandType found = this.findById(id);
        if (found == null) return false;
        UpdateWrapper<DemandType> update = new UpdateWrapper<DemandType>();
        update.eq("id", id);
        update.set("id", id);
        if (body.getName() != null && !body.getName().equals(found.getName())) update.set("name", body.getName());
        if (body.getCreator() != null && !body.getCreator().equals(found.getCreatorId())) update.set("creatorId", body.getCreator());
        if (body.getCreateDate() != null && !body.getCreateDate().equals(found.getCreateDate())) update.set("create_date", body.getCreateDate());
        if (body.getUpdateDate() != null && !body.getUpdateDate().equals(found.getUpdateDate())) update.set("update_date", body.getUpdateDate());
        if (body.getIsOn() != null && body.getIsOn().equals("1")) update.set("delete_date", null);
        if (body.getIsOn() != null && body.getIsOn().equals("2")) update.set("delete_date", new Date());
        boolean res = this.update(update);
        this.approverService.updateApproverList(id, body.getApproverList());
        if (res && body.getDemandStatusIds() != null) {
            this.dtdsService.updateByLeft(id, body.getDemandStatusIds());
        }
        return res;
    }
}
