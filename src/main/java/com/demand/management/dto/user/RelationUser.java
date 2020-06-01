package com.demand.management.dto.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.demand.management.dto.permission.RelationPermission;
import com.demand.management.dto.role.RelationRole;
import com.demand.management.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RelationUser extends User {
    @TableField(value = "creator",exist = false)
    public User creator;

    @TableField(value = "role",exist = false)
    public RelationRole role;

    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<RelationPermission> permissionList;
}
