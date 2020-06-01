package com.demand.management.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demand.management.dto.user.UserBodyReq;
import com.demand.management.dto.user.RelationUser;
import com.demand.management.entity.User;
import com.demand.management.dao.mapper.UserMapper;
import com.demand.management.service.UserService;
import com.demand.management.service.UserPermissionService;
import com.demand.management.utils.CommonUtil;
import com.demand.management.utils.ExtendPage;
import com.demand.management.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserPermissionService userPermissionService;

    @Override
    public RelationUser findById(String id) {
        return this.baseMapper.findById(id);
    }

    @Override
    public RelationUser findOneByWhere(Map<String, Object> where) {
        return this.baseMapper.findOneByWhere(where);
    }

    @Override
    public boolean updatePassword(String id, String newPassword) {
        if (id == null || id.isEmpty() || newPassword == null || newPassword.isEmpty()) return false;
        User userFound = this.getById(id);
        if (userFound == null) return false;

        String salt = Objects.toString(System.currentTimeMillis());
        String saltPassword = salt + ':' + newPassword;
        String password = MD5Util.MD5Encode(saltPassword, "utf-8");

        UpdateWrapper<User> update = new UpdateWrapper<>();
        update.eq("id", id);
        update.set("id", id);
        update.set("password", password);

        return this.update(update);
    }

    @Override
    public boolean updateById(String id, UserBodyReq user) {
        RelationUser userFound = this.findById(id);
        if (userFound == null) return false;
        UpdateWrapper<User> update = new UpdateWrapper<>();
        update.eq("id", id);
        update.set("id", id);

        if (user.getName() != null && !user.getName().equals(userFound.getName())) update.set("name", user.getName());
        if (user.getMobile() != null && !user.getMobile().equals(userFound.getMobile())) update.set("mobile", user.getMobile());
        if (user.getEmail() != null && !user.getEmail().equals(userFound.getEmail())) update.set("email", user.getEmail());
        if (user.getRole() != null && !user.getRole().equals(userFound.getRoleId())) update.set("roleId", user.getRole());
        if (user.getCreator() != null && !user.getCreator().equals(userFound.getCreatorId())) update.set("creatorId", user.getCreator());
        if (user.getCreateDate() != null && !user.getCreateDate().equals(userFound.getCreateDate())) update.set("create_date", user.getCreateDate());
        if (user.getUpdateDate() != null && !user.getUpdateDate().equals(userFound.getUpdateDate())) update.set("update_date", user.getUpdateDate());
        if (user.getIsOn() != null && user.getIsOn().equals("1")) update.set("delete_date", null);
        if (user.getIsOn() != null && user.getIsOn().equals("2")) update.set("delete_date", new Date());
        boolean res = this.update(update);
        if (res && user.getPermissionIds() != null) {
            this.userPermissionService.updateByLeft(id, user.getPermissionIds());
        }
        return res;
    }

    @Override
    public RelationUser create(UserBodyReq user) {
        String salt = Objects.toString(System.currentTimeMillis());
        String saltPassword = salt + ':' + user.getPassword();
        String password = MD5Util.MD5Encode(saltPassword, "utf-8");
        User userMapper = new User();
        userMapper.setName(user.getName());
        userMapper.setMobile(user.getMobile());
        userMapper.setEmail(user.getEmail());
        userMapper.setSalt(salt);
        userMapper.setPassword(password);
        userMapper.setRoleId(user.getRole());
        userMapper.setCreatorId(user.getCreator());
        boolean res = this.save(userMapper);
        if (res) {
            this.userPermissionService.addRight(userMapper.getId(), user.getPermissionIds());
            return this.findById(userMapper.getId());
        } else {
            return null;
        }
    }

    @Override
    public Page<RelationUser> findAll(String pageIndex, String pageSize, String isOn, String keyword) {
        ExtendPage<RelationUser> page = new ExtendPage<RelationUser>(pageIndex, pageSize);
        String isNull = CommonUtil.buildWithIsOn(isOn);
        return page.setTotalAndRecord(this.baseMapper.findByPage(page.getPageForFind(), keyword, isNull));
    }
}
