package com.demand.management.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demand.management.dto.permission.PermissionBodyReq;
import com.demand.management.dto.permission.RelationPermission;
import com.demand.management.entity.Permission;
import com.demand.management.dao.mapper.PermissionMapper;
import com.demand.management.service.PermissionService;
import com.demand.management.utils.CommonUtil;
import com.demand.management.utils.ExtendPage;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Override
    public RelationPermission create(PermissionBodyReq permission) {
        Permission permissionMapper = new Permission();
        permissionMapper.setName(permission.getName());
        String type = permission.getType();
        permissionMapper.setType(type);
        if (type.equals("1")) permissionMapper.setRoute(permission.getRoute());
        if (type.equals("2")) permissionMapper.setOperating(permission.getOperating());
        permissionMapper.setModuleId(permission.getModule());
        permissionMapper.setCreatorId(permission.getCreator());

        boolean res = this.save(permissionMapper);
        if (res) {
            return this.findById(permissionMapper.getId());
        } else {
            return null;
        }
    }

    @Override
    public Page<RelationPermission> findAll(String pageIndex, String pageSize, String isOn, String type) {
        ExtendPage<RelationPermission> page = new ExtendPage<RelationPermission>(pageIndex, pageSize);
        String isNull = CommonUtil.buildWithIsOn(isOn);
        return page.setTotalAndRecord(this.baseMapper.findByPage(page.getPageForFind(), type, isNull));
    }

    @Override
    public RelationPermission findById(String id)  {
        return this.baseMapper.findById(id);
    }

    @Override
    public boolean updateById(String id, PermissionBodyReq permission) {
        RelationPermission permissionFound = this.findById(id);
        if (permissionFound == null) return false;
        UpdateWrapper<Permission> update = new UpdateWrapper<Permission>();
        update.eq("id", id);
        update.set("id", id);
        if (permission.getName() != null && !permission.getName().equals(permissionFound.getName())) update.set("name", permission.getName());
        if (permission.getModule() != null && !permission.getModule().equals(permissionFound.getModuleId())) update.set("moduleId", permission.getModule());
        if (permission.getRoute() != null && !permission.getRoute().equals(permissionFound.getRoute())) update.set("route", permission.getRoute());
        if (permission.getOperating() != null && !permission.getOperating().equals(permissionFound.getOperating())) update.set("operating", permission.getOperating());
        if (permission.getType() != null && !permission.getType().equals(permissionFound.getType())) {
            if (permission.getType().equals("1")) update.set("operating", null);
            if (permission.getType().equals("2")) update.set("route", null);
        }
        if (permission.getCreator() != null && !permission.getCreator().equals(permissionFound.getCreatorId())) update.set("creatorId", permission.getCreator());
        if (permission.getIsOn() != null && permission.getIsOn().equals("1")) update.set("delete_date", null);
        if (permission.getIsOn() != null && permission.getIsOn().equals("2")) update.set("delete_date", new Date());
        return this.update(update);
    }
}
