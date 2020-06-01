package com.demand.management.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "role_demand_type")//指定表名
public class RoleDemandType {
    @TableField(value = "role", exist = true)
    String role;
    @TableField(value = "demand_type", exist = true)
    String demand_type;
}
