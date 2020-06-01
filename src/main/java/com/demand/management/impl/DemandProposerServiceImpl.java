package com.demand.management.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demand.management.dao.mapper.DemandProposerMapper;
import com.demand.management.entity.DemandProposer;
import com.demand.management.service.DemandProposerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemandProposerServiceImpl extends ServiceImpl<DemandProposerMapper, DemandProposer> implements DemandProposerService {
    @Transactional
    @Override
    public void updateByLeft(String left, List<String> ids) {
        this.removeLeft(left);
        this.addRight(left, ids);
    }

    @Transactional
    @Override
    public void removeLeft(String left) {
        UpdateWrapper<DemandProposer> wrapper = new UpdateWrapper<DemandProposer>();
        wrapper.eq("demand", left);
        this.remove(wrapper);
    }

    @Transactional
    @Override
    public void addRight(String left, List<String> ids) {
        List<DemandProposer> list = new ArrayList<DemandProposer>();
        if (ids != null && ids.size() > 0) {
            for (String id : ids) {
                DemandProposer relation = new DemandProposer();
                relation.setDemand(left);
                relation.setUser(id);
                list.add(relation);
            }
            this.saveBatch(list);
        }
    }
}
