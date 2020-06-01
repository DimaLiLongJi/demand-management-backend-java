package com.demand.management.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demand.management.dto.demand.DemandStatusBodyReq;
import com.demand.management.dto.demand.RelationDemandStatus;
import com.demand.management.entity.DemandStatus;
import com.demand.management.dao.mapper.DemandStatusMapper;
import com.demand.management.service.DemandStatusService;
import com.demand.management.utils.CommonUtil;
import com.demand.management.utils.ExtendPage;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DemandStatusServiceImpl extends ServiceImpl<DemandStatusMapper, DemandStatus> implements DemandStatusService {
    @Override
    public RelationDemandStatus create(DemandStatusBodyReq body) {
        DemandStatus demandStatus = new DemandStatus();
        demandStatus.setName(body.getName());
        demandStatus.setIsEndStatus(body.getIsEndStatus());
        demandStatus.setCreatorId(body.getCreator());
        boolean res = this.save(demandStatus);
        if (res) {
            return this.findById(demandStatus.getId());
        } else return null;
    }

    @Override
    public RelationDemandStatus findById(String id) {
        return this.baseMapper.findById(id);
    }

    @Override
    public Page<RelationDemandStatus> findAll(String pageIndex, String pageSize, String isOn) {
        ExtendPage<RelationDemandStatus> page = new ExtendPage<RelationDemandStatus>(pageIndex, pageSize);
        String isNull = CommonUtil.buildWithIsOn(isOn);
        return page.setTotalAndRecord(this.baseMapper.findByPage(page.getPageForFind(), isNull));
    }

    @Override
    public boolean updateById(String id, DemandStatusBodyReq body) {
        RelationDemandStatus found = this.findById(id);
        if (found == null) return false;
        UpdateWrapper<DemandStatus> update = new UpdateWrapper<DemandStatus>();
        update.eq("id", id);
        update.set("id", id);
        if (body.getName() != null && !body.getName().equals(found.getName())) update.set("name", body.getName());
        if (body.getIsEndStatus() != null && !body.getIsEndStatus().equals(found.getIsEndStatus())) update.set("isEndStatus", body.getIsEndStatus());
        if (body.getCreator() != null && !body.getCreator().equals(found.getCreatorId())) update.set("creatorId", body.getCreator());
        if (body.getCreateDate() != null && !body.getCreateDate().equals(found.getCreateDate())) update.set("create_date", body.getCreateDate());
        if (body.getUpdateDate() != null && !body.getUpdateDate().equals(found.getUpdateDate())) update.set("update_date", body.getUpdateDate());
        if (body.getIsOn() != null && body.getIsOn().equals("1")) update.set("delete_date", null);
        if (body.getIsOn() != null && body.getIsOn().equals("2")) update.set("delete_date", new Date());
        return this.update(update);
    }
}
