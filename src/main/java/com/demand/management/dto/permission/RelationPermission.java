package com.demand.management.dto.permission;

import com.baomidou.mybatisplus.annotation.TableField;
import com.demand.management.entity.Module;
import com.demand.management.entity.Permission;
import com.demand.management.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RelationPermission extends Permission {
    @TableField(value = "creator",exist = false)
    public User creator;

    @TableField(value = "module",exist = false)
    public Module module;
}
