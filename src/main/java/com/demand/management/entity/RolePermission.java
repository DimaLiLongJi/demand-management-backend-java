package com.demand.management.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "role_permission")
public class RolePermission {
    public String role;
    public String permission;
}
