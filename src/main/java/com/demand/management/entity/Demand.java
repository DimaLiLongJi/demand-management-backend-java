package com.demand.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.demand.management.dto.demand.DemandPendingEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "demand")
public class Demand {
    @TableId(type = IdType.AUTO)
    public String id;
    public String name;

    // DemandPendingEnum
    // notPending("1"), // 已审核
    // isPending("2");  // 待审核
    @TableField(value = "isPending", fill = FieldFill.INSERT)
    public String isPending;

    // 需求详情
    public String detail;

    // 需求备注
    public String comment;

    // 线上链接
    public String url;

    // 开发人天
    @TableField(value = "manDay")
    public String manDay;

    // 需求类型
    @TableField(value = "demandTypeId")
    public String demandTypeId;

    // 需求状态
    @TableField(value = "demandStatusId")
    public String demandStatusId;

    // 需求创建者
    @TableField(value = "creatorId")
    public String creatorId;

    // 需求期待完成时间
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(value = "expect_date",exist = true, fill = FieldFill.INSERT)
    public Date expectDate;

    // 排期开始时间
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(value = "schedule_start_date",exist = true, fill = FieldFill.INSERT)
    public Date scheduleStartDate;

    // 排期结束时间
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(value = "schedule_end_date",exist = true, fill = FieldFill.INSERT)
    public Date scheduleEndDate;

    // 创建时间
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(value = "create_date",exist = true, fill = FieldFill.INSERT)
    public Date createDate;

    // 归档时间
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(value = "delete_date",exist = true)
    public Date deleteDate;

    // 更新时间
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(value = "update_date",exist = true)
    public Date updateDate;

    // 实际完成时间
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(value = "finish_date",exist = true, fill = FieldFill.INSERT_UPDATE)
    public Date finishDate;
}
