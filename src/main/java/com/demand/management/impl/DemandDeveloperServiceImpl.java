package com.demand.management.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demand.management.dao.mapper.DemandDeveloperMapper;
import com.demand.management.entity.DemandDeveloper;
import com.demand.management.service.DemandDeveloperService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemandDeveloperServiceImpl extends ServiceImpl<DemandDeveloperMapper, DemandDeveloper> implements DemandDeveloperService {
    @Transactional
    @Override
    public void updateByLeft(String left, List<String> ids) {
        this.removeLeft(left);
        this.addRight(left, ids);
    }

    @Transactional
    @Override
    public void removeLeft(String left) {
        UpdateWrapper<DemandDeveloper> wrapper = new UpdateWrapper<DemandDeveloper>();
        wrapper.eq("demand", left);
        this.remove(wrapper);
    }

    @Transactional
    @Override
    public void addRight(String left, List<String> ids) {
        List<DemandDeveloper> list = new ArrayList<DemandDeveloper>();
        if (ids != null && ids.size() > 0) {
            for (String id : ids) {
                DemandDeveloper relation = new DemandDeveloper();
                relation.setDemand(left);
                relation.setUser(id);
                list.add(relation);
            }
            this.saveBatch(list);
        }
    }
}
