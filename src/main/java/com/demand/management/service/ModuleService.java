package com.demand.management.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demand.management.dto.module.ModuleBodyReq;
import com.demand.management.dto.module.RelationModule;
import com.demand.management.entity.Module;

public interface ModuleService extends IService<Module> {
    Page<RelationModule> findAll();
    RelationModule create(ModuleBodyReq body);
    RelationModule findById(String id);
}
