package com.demand.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demand.management.dto.user.RelationUser;
import com.demand.management.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface UserMapper extends BaseMapper<User> {
    RelationUser findById(@Param("id") String id);
    RelationUser findOneByWhere(@Param("where") Map<String, Object> where);
    List<RelationUser> findByPage(Page<RelationUser> page, @Param("keyword") String keyword, @Param("isNull") String isNull);
}
