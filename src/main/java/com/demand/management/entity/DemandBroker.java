package com.demand.management.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "demand_broker")
public class DemandBroker {
    public String demand;
    public String user;
}
