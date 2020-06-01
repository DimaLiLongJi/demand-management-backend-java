package com.demand.management.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demand.management.dto.role.RelationRole;
import com.demand.management.dto.role.RoleBodyReq;
import com.demand.management.entity.Role;
import com.demand.management.dao.mapper.RoleMapper;
import com.demand.management.service.RoleDemandTypeService;
import com.demand.management.service.RoleService;
import com.demand.management.service.RolePermissionService;
import com.demand.management.utils.CommonUtil;
import com.demand.management.utils.ExtendPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private RoleDemandTypeService roleDemandTypeService;

    @Override
    public RelationRole findById(String id) {
        return this.baseMapper.findById(id);
    }

    @Override
    public Page<RelationRole> findAll(String pageIndex, String pageSize, String isOn) {
        ExtendPage<RelationRole> page = new ExtendPage<RelationRole>(pageIndex, pageSize);
        String isNull = CommonUtil.buildWithIsOn(isOn);
        return page.setTotalAndRecord(this.baseMapper.findByPage(page.getPageForFind(), isNull));
    }

    @Override
    public RelationRole create(RoleBodyReq body) {
        Role role = new Role();
        role.setName(body.getName());
        role.setCreatorId(body.getCreator());
        boolean res = this.save(role);
        if (res) {
            this.rolePermissionService.addRight(role.getId(), body.getPermissionIds());
            this.roleDemandTypeService.addRight(role.getId(), body.getDemandTypeIds());
            return this.findById(role.getId());
        } else return null;
    }

    @Override
    public boolean updateById(String id, RoleBodyReq role) {
        RelationRole roleFound = this.findById(id);
        if (roleFound == null) return false;
        UpdateWrapper<Role> update = new UpdateWrapper<Role>();
        update.eq("id", id);
        update.set("id", id);
        if (role.getName() != null && !role.getName().equals(roleFound.getName())) update.set("name", role.getName());
        if (role.getCreator() != null && !role.getCreator().equals(roleFound.getCreatorId())) update.set("creatorId", role.getCreator());
        if (role.getCreateDate() != null && !role.getCreateDate().equals(roleFound.getCreateDate())) update.set("create_date", role.getCreateDate());
        if (role.getUpdateDate() != null && !role.getUpdateDate().equals(roleFound.getUpdateDate())) update.set("update_date", role.getUpdateDate());
        if (role.getIsOn() != null && role.getIsOn().equals("1")) update.set("delete_date", null);
        if (role.getIsOn() != null && role.getIsOn().equals("2")) update.set("delete_date", new Date());
        boolean res = this.update(update);
        if (res && role.getPermissionIds() != null) {
            this.rolePermissionService.updateByLeft(id, role.getPermissionIds());
            this.roleDemandTypeService.updateByLeft(id, role.getDemandTypeIds());
        }
        return res;
    }


}
