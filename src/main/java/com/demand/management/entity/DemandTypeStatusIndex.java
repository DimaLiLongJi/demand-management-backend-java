package com.demand.management.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "demand_type_stataus_index")//指定表名
public class DemandTypeStatusIndex {
    @TableId(type = IdType.AUTO)
    public String id;
    // 位置索引
    @TableField(value = "statusIndex")
    public String statusIndex;
    // 需求类型
    @TableField(value = "demandTypeId")
    public String demandTypeId;

    // 需求状态
    @TableField(value = "demandStatusId")
    public String demandStatusId;
}
