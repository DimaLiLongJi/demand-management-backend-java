package com.demand.management.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demand.management.entity.UserPermission;
import com.demand.management.dao.mapper.UserPermissionMapper;
import com.demand.management.service.UserPermissionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserPermissionServiceImpl extends ServiceImpl<UserPermissionMapper, UserPermission> implements UserPermissionService {

    @Override
    public void updateByLeft(String left, List<String> ids) {
        this.removeLeft(left);
        this.addRight(left, ids);
    }

    @Override
    public void removeLeft(String left) {
        UpdateWrapper<UserPermission> wrapper = new UpdateWrapper<>();
        wrapper.eq("user", left);
        this.remove(wrapper);
    }

    @Override
    public void addRight(String left, List<String> ids) {
        List<UserPermission> list = new ArrayList<UserPermission>();
        if (ids != null && ids.size() > 0) {
            for (String id : ids) {
                UserPermission userPermission = new UserPermission();
                userPermission.setUser(left);
                userPermission.setPermission(id);
                list.add(userPermission);
            }
            this.saveBatch(list);
        }
    }
}
