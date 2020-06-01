package com.demand.management.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "user_permission")
public class UserPermission {
    public String user;
    public String permission;
}
