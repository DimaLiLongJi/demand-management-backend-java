package com.demand.management.dto.role;

import com.baomidou.mybatisplus.annotation.TableField;
import com.demand.management.dto.demand.RelationDemandType;
import com.demand.management.dto.permission.RelationPermission;
import com.demand.management.entity.Permission;
import com.demand.management.entity.Role;
import com.demand.management.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RelationRole extends Role {

    @TableField(value = "permissionList",exist = false)
    public List<RelationPermission> permissionList;

    @TableField(value = "permissionList",exist = false)
    public List<RelationDemandType> demandTypeList;

    @TableField(value = "creator",exist = false)
    public User creator;
}
