package com.demand.management.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "demand_developer")
public class DemandDeveloper {
    public String demand;
    public String user;
}
