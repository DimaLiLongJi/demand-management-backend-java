package com.demand.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "demand_progress")
public class DemandProgress {
    @TableId(type = IdType.AUTO)
    public String id;

//    developer = '1',
//    devops = '2',
    public String type;

    @TableField(value = "demandId")
    public String demandId;

    @TableField(value = "userId")
    public String userId;

    @TableField(value = "creatorId")
    public String creatorId;

    // 期开始时间
    @JsonInclude(JsonInclude.Include.NON_NULL)
    // @JsonFormat(pattern = "YYYY-MM-DD hh:mm:ss", locale = "zh", timezone = "GMT+8")
    @TableField(value = "schedule_start_date",exist = true, fill = FieldFill.INSERT)
    public Date scheduleStartDate;

    // 排期结束时间
    @JsonInclude(JsonInclude.Include.NON_NULL)
    // @JsonFormat(pattern = "YYYY-MM-DD hh:mm:ss", locale = "zh", timezone = "GMT+8")
    @TableField(value = "schedule_end_date",exist = true, fill = FieldFill.INSERT)
    public Date scheduleEndDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    // @JsonFormat(pattern = "YYYY-MM-DD hh:mm:ss", locale = "zh", timezone = "GMT+8")
    @TableField(value = "create_date",exist = true, fill = FieldFill.INSERT)
    public Date createDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    // @JsonFormat(pattern = "YYYY-MM-DD hh:mm:ss", locale = "zh", timezone = "GMT+8")
    @TableField(value = "update_date",exist = true)
    public Date updateDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    // @JsonFormat(pattern = "YYYY-MM-DD hh:mm:ss", locale = "zh", timezone = "GMT+8")
    @TableField(value = "finish_date",exist = true, fill = FieldFill.INSERT_UPDATE)
    public Date finishDate;
}
