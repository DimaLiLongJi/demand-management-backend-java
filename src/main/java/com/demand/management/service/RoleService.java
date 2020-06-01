package com.demand.management.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demand.management.dto.role.RelationRole;
import com.demand.management.dto.role.RoleBodyReq;
import com.demand.management.entity.Role;

public interface RoleService extends IService<Role> {
    RelationRole findById(String id);
    Page<RelationRole> findAll(String pageIndex, String pageSize, String isOn);
    RelationRole create(RoleBodyReq body);
    boolean updateById(String id, RoleBodyReq body);
}
