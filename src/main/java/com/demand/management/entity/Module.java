package com.demand.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "module")
public class Module {
    @TableId(type = IdType.AUTO)
    public String id;
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY, updateStrategy = FieldStrategy.NOT_EMPTY)
    public String name;
    public String icon;

    @TableField(value = "creatorId")
    public String creatorId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    // @JsonFormat(pattern = "YYYY-MM-DD hh:mm:ss", locale = "zh", timezone = "GMT+8")
    @TableField(value = "create_date",exist = true, fill = FieldFill.INSERT)
    public Date createDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    // @JsonFormat(pattern = "YYYY-MM-DD hh:mm:ss", locale = "zh", timezone = "GMT+8")
    @TableField(value = "update_date",exist = true, fill = FieldFill.INSERT_UPDATE)
    public Date updateDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    // @JsonFormat(pattern = "YYYY-MM-DD hh:mm:ss", locale = "zh", timezone = "GMT+8")
    @TableField(value = "delete_date",exist = true)
    public Date deleteDate;
}
