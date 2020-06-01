package com.demand.management.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demand.management.dao.mapper.DemandLogMapper;
import com.demand.management.dto.demand.DemandLogBodyReq;
import com.demand.management.dto.demand.RelationDemandLog;
import com.demand.management.entity.DemandLog;
import com.demand.management.service.DemandLogService;
import com.demand.management.utils.ExtendPage;
import org.springframework.stereotype.Service;

@Service
public class DemandLogServiceImpl extends ServiceImpl<DemandLogMapper, DemandLog> implements DemandLogService {
    @Override
    public RelationDemandLog create(DemandLogBodyReq body) {
        DemandLog instance = new DemandLog();
        instance.setType(body.getType());
        instance.setProperty(body.getProperty());
        instance.setOldDetail(body.getOldDetail());
        instance.setNewDetail(body.getNewDetail());
        instance.setDemandId(body.getDemand());
        instance.setCreatorId(body.getCreator());
        instance.setCreateDate(body.getCreateDate());
        boolean res = this.save(instance);
        if (res) {
            return this.findById(instance.getId());
        } else return null;
    }

    @Override
    public RelationDemandLog findById(String id) {
        return this.baseMapper.findById(id);
    }

    @Override
    public Page<RelationDemandLog> findAll(String pageIndex, String pageSize, String demand, String creator) {
        ExtendPage<RelationDemandLog> page = new ExtendPage<RelationDemandLog>(pageIndex, pageSize);
        return page.setTotalAndRecord(this.baseMapper.findByPage(page.getPageForFind(), demand, creator));
    }
}
