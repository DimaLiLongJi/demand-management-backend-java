package com.demand.management.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demand.management.entity.DemandTypeDemandStatus;
import com.demand.management.dao.mapper.DemandTypeDemandStatusMapper;
import com.demand.management.service.DemandTypeDemandStatusService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemandTypeDemandStatusServiceImpl extends ServiceImpl<DemandTypeDemandStatusMapper, DemandTypeDemandStatus> implements DemandTypeDemandStatusService {

    @Override
    public void updateByLeft(String left, List<String> ids) {
        this.removeLeft(left);
        this.addRight(left, ids);
    }

    @Override
    public void removeLeft(String left) {
        UpdateWrapper<DemandTypeDemandStatus> wrapper = new UpdateWrapper<>();
        wrapper.eq("demand_type", left);
        this.remove(wrapper);
    }

    @Override
    public void addRight(String left, List<String> ids) {
        List<DemandTypeDemandStatus> list = new ArrayList<DemandTypeDemandStatus>();
        if (ids != null && ids.size() > 0) {
            for (String id : ids) {
                DemandTypeDemandStatus dsds = new DemandTypeDemandStatus();
                dsds.setDemandType(left);
                dsds.setDemandStatus(id);
                list.add(dsds);
            }
            this.saveBatch(list);
        }
    }
}
