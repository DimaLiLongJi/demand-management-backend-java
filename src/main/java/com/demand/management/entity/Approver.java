package com.demand.management.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "approver")
public class Approver {
    @TableId(type = IdType.AUTO)
    public String id;

    // 需求类型
    @TableField(value = "demandTypeId")
    public String demandTypeId;

    // 需求状态
    @TableField(value = "demandStatusId")
    public String demandStatusId;

    // 需求创建者
    @TableField(value = "userId")
    public String userId;
}
