package com.demand.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "demand_log")
public class DemandLog {
    @TableId(type = IdType.AUTO)
    public String id;

    /**
     * DemandLogTypeEnum
     * 1：新增
     * 2：更新
     * 3：删除
     *
     */
    public String type;

    // 日志属性 DemandLogPropertyEnum
    public String property;

    @TableField(value = "demandId")
    public String demandId;

    @TableField(value = "oldDetail")
    public String oldDetail;
    @TableField(value = "newDetail")
    public String newDetail;

    @TableField(value = "creatorId")
    public String creatorId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(value = "create_date",exist = true, fill = FieldFill.INSERT)
    public Date createDate;

}
