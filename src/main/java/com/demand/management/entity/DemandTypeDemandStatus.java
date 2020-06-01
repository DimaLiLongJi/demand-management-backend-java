package com.demand.management.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "demand_type_demand_status")
public class DemandTypeDemandStatus {
    @TableField(value = "demand_type", exist = true)
    public String demandType;
    @TableField(value = "demand_status", exist = true)
    public String DemandStatus;
}
