package com.demand.management.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "demand_devops")
public class DemandDevops {
    public String demand;
    public String user;
}
