package com.demand.management.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demand.management.dao.mapper.RoleDemandTypeMapper;
import com.demand.management.entity.RoleDemandType;
import com.demand.management.service.RoleDemandTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleDemandTypeServiceImpl extends ServiceImpl<RoleDemandTypeMapper, RoleDemandType> implements RoleDemandTypeService {
    @Override
    @Transactional
    public void updateByLeft(String left, List<String> ids) {
        this.removeLeft(left);
        this.addRight(left, ids);
    }

    @Override
    public void removeLeft(String left) {
        UpdateWrapper<RoleDemandType> wrapper = new UpdateWrapper<>();
        wrapper.eq("role", left);
        this.remove(wrapper);
    }

    @Override
    public void addRight(String left, List<String> ids) {
        List<RoleDemandType> list = new ArrayList<RoleDemandType>();
        if (ids != null && ids.size() > 0) {
            for (String id : ids) {
                RoleDemandType roleDemandType = new RoleDemandType();
                roleDemandType.setRole(left);
                roleDemandType.setDemand_type(id);
                list.add(roleDemandType);
            }
            this.saveBatch(list);
        }
    }
}
