package com.demand.management.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demand.management.dto.user.UserBodyReq;
import com.demand.management.dto.user.RelationUser;
import com.demand.management.entity.User;

import java.util.Map;

public interface UserService extends IService<User> {
    RelationUser create(UserBodyReq user);
    Page<RelationUser> findAll(String pageIndex, String pageSize, String isOn, String keyword);
    RelationUser findById(String id);
    boolean updateById(String id, UserBodyReq user);
    RelationUser findOneByWhere(Map<String, Object> where);
    boolean updatePassword(String id, String newPassword);
}
