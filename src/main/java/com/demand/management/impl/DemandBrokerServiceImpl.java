package com.demand.management.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demand.management.dao.mapper.DemandBrokerMapper;
import com.demand.management.entity.DemandBroker;
import com.demand.management.service.DemandBrokerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemandBrokerServiceImpl extends ServiceImpl<DemandBrokerMapper, DemandBroker> implements DemandBrokerService {

    @Transactional
    @Override
    public void updateByLeft(String left, List<String> ids) {
        this.removeLeft(left);
        this.addRight(left, ids);
    }

    @Transactional
    @Override
    public void removeLeft(String left) {
        UpdateWrapper<DemandBroker> wrapper = new UpdateWrapper<DemandBroker>();
        wrapper.eq("demand", left);
        this.remove(wrapper);
    }

    @Transactional
    @Override
    public void addRight(String left, List<String> ids) {
        List<DemandBroker> list = new ArrayList<DemandBroker>();
        if (ids != null && ids.size() > 0) {
            for (String id : ids) {
                DemandBroker relation = new DemandBroker();
                relation.setDemand(left);
                relation.setUser(id);
                list.add(relation);
            }
            this.saveBatch(list);
        }
    }
}
