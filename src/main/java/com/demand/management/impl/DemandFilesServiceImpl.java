package com.demand.management.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demand.management.dao.mapper.DemandFilesMapper;
import com.demand.management.entity.DemandFiles;
import com.demand.management.service.DemandFilesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemandFilesServiceImpl extends ServiceImpl<DemandFilesMapper, DemandFiles> implements DemandFilesService {
    @Transactional
    @Override
    public void updateByLeft(String left, List<String> ids) {
        this.removeLeft(left);
        this.addRight(left, ids);
    }

    @Transactional
    @Override
    public void removeLeft(String left) {
        UpdateWrapper<DemandFiles> wrapper = new UpdateWrapper<DemandFiles>();
        wrapper.eq("demand", left);
        this.remove(wrapper);
    }

    @Transactional
    @Override
    public void addRight(String left, List<String> ids) {
        List<DemandFiles> list = new ArrayList<DemandFiles>();
        if (ids != null && ids.size() > 0) {
            for (String id : ids) {
                DemandFiles relation = new DemandFiles();
                relation.setDemand(left);
                relation.setFile(id);
                list.add(relation);
            }
            this.saveBatch(list);
        }
    }
}
