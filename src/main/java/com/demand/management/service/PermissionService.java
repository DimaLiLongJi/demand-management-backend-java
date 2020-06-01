package com.demand.management.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demand.management.dto.permission.PermissionBodyReq;
import com.demand.management.dto.permission.RelationPermission;
import com.demand.management.entity.Permission;

public interface PermissionService extends IService<Permission> {
    RelationPermission create(PermissionBodyReq permission);
    Page<RelationPermission> findAll(String pageIndex, String pageSize, String isOn, String type);
    RelationPermission findById(String id);
    boolean updateById(String id, PermissionBodyReq permission);
}
