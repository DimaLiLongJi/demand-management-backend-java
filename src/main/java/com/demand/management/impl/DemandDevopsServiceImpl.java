package com.demand.management.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demand.management.dao.mapper.DemandDevopsMapper;
import com.demand.management.entity.DemandDevops;
import com.demand.management.service.DemandDevopsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemandDevopsServiceImpl extends ServiceImpl<DemandDevopsMapper, DemandDevops> implements DemandDevopsService {
    @Transactional
    @Override
    public void updateByLeft(String left, List<String> ids) {
        this.removeLeft(left);
        this.addRight(left, ids);
    }

    @Transactional
    @Override
    public void removeLeft(String left) {
        UpdateWrapper<DemandDevops> wrapper = new UpdateWrapper<DemandDevops>();
        wrapper.eq("demand", left);
        this.remove(wrapper);
    }

    @Transactional
    @Override
    public void addRight(String left, List<String> ids) {
        List<DemandDevops> list = new ArrayList<DemandDevops>();
        if (ids != null && ids.size() > 0) {
            for (String id : ids) {
                DemandDevops relation = new DemandDevops();
                relation.setDemand(left);
                relation.setUser(id);
                list.add(relation);
            }
            this.saveBatch(list);
        }
    }
}
