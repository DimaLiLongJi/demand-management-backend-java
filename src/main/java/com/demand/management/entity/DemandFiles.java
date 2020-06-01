package com.demand.management.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "demand_files")
public class DemandFiles {
    public String demand;
    public String file;
}
