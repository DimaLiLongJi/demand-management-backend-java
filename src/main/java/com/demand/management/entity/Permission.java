package com.demand.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "permission")//指定表名
public class Permission {
    @TableId(type = IdType.AUTO)
    public String id;
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY, updateStrategy = FieldStrategy.NOT_EMPTY)
    public String name;

    /**
     * 权限类型 1 或 2
     * 1：访问权限，控制路由route
     * 2：操作权限控制，控制操作项operating
     */
    public String type;

    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    public String route;

    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    public String operating;

    @TableField(value = "moduleId")
    public String moduleId;

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
