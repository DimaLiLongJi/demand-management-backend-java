package com.demand.management.dto.module;

import com.baomidou.mybatisplus.annotation.TableField;
import com.demand.management.entity.Module;
import com.demand.management.entity.Permission;
import com.demand.management.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RelationModule extends Module {
    @TableField(value = "permissionList",exist = false)
    public List<Permission> permissionList;
    @TableField(value = "creator",exist = false)
    public User creator;
}
