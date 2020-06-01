package com.demand.management.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demand.management.entity.RolePermission;
import com.demand.management.dao.mapper.RolePermissionMapper;
import com.demand.management.service.RolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    @Override
    @Transactional
    public void updateByLeft(String left, List<String> ids) {
        this.removeLeft(left);
        this.addRight(left, ids);
    }

    @Override
    public void removeLeft(String left) {
        UpdateWrapper<RolePermission> wrapper = new UpdateWrapper<>();
        wrapper.eq("role", left);
        this.remove(wrapper);
    }

    @Override
    public void addRight(String left, List<String> ids) {
        List<RolePermission> list = new ArrayList<RolePermission>();
        if (ids != null && ids.size() > 0) {
            for (String id : ids) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRole(left);
                rolePermission.setPermission(id);
                list.add(rolePermission);
            }
            this.saveBatch(list);
        }
    }
}
