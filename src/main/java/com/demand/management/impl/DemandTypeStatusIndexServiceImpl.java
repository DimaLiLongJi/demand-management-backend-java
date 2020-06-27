package com.demand.management.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demand.management.dao.mapper.DemandTypeStatusIndexMapper;
import com.demand.management.dto.demand.DemandTypeStatusIndexReqItem;
import com.demand.management.dto.demand.RelationDemandTypeStatusIndex;
import com.demand.management.entity.DemandTypeStatusIndex;
import com.demand.management.service.DemandTypeStatusIndexService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DemandTypeStatusIndexServiceImpl extends ServiceImpl<DemandTypeStatusIndexMapper, DemandTypeStatusIndex> implements DemandTypeStatusIndexService {
    @Override
    public List<RelationDemandTypeStatusIndex> findByDemandTypeId(String demandTypeId) {
        return this.baseMapper.findByDemandTypeId(demandTypeId);
    }

    @Override
    public List<RelationDemandTypeStatusIndex> createIndexList(String demandTypeId, List<DemandTypeStatusIndexReqItem> body) {
        UpdateWrapper<DemandTypeStatusIndex> wrapper = new UpdateWrapper<>();
        wrapper.eq("demandTypeId", demandTypeId);
        this.remove(wrapper);

        List<DemandTypeStatusIndex> list = new ArrayList<DemandTypeStatusIndex>();
        if (body != null && body.size() > 0) {
            for (DemandTypeStatusIndexReqItem item : body) {
                DemandTypeStatusIndex demandTypeStatusIndex = new DemandTypeStatusIndex();
                demandTypeStatusIndex.setStatusIndex(item.getStatusIndex());
                demandTypeStatusIndex.setDemandTypeId(item.getDemandType());
                demandTypeStatusIndex.setDemandStatusId(item.getDemandStatus());
                list.add(demandTypeStatusIndex);
            }
            this.saveBatch(list);
            return this.findByDemandTypeId(demandTypeId);
        }
        else return null;
    }
}
