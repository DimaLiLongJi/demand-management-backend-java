package com.demand.management.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demand.management.dto.module.ModuleBodyReq;
import com.demand.management.dto.module.RelationModule;
import com.demand.management.entity.Module;
import com.demand.management.dao.mapper.ModuleMapper;
import com.demand.management.service.ModuleService;
import com.demand.management.utils.ExtendPage;
import org.springframework.stereotype.Service;

@Service
public class ModuleServiceImpl extends ServiceImpl<ModuleMapper, Module> implements ModuleService {
    @Override
    public Page<RelationModule> findAll() {
        ExtendPage<RelationModule> page = new ExtendPage<RelationModule>(null, null);
        return page.setTotalAndRecord(this.baseMapper.findByPage(page.getPageForFind()));
    }

    @Override
    public RelationModule create(ModuleBodyReq body) {
        Module module = new Module();
        module.setName(body.getName());
        module.setIcon(body.getIcon());
        module.setCreatorId(body.getCreator());
        boolean res = this.save(module);
        if (res) {
            return this.findById(module.getId());
        } else return null;
    }

    @Override
    public RelationModule findById(String id) {
        return this.baseMapper.findById(id);
    }
}
