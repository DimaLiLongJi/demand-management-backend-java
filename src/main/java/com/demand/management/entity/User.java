package com.demand.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "user")//指定表名
public class User {
    @TableId(type = IdType.AUTO)
    public String id;
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY, updateStrategy = FieldStrategy.NOT_EMPTY)
    public String name;
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY, updateStrategy = FieldStrategy.NOT_EMPTY)
    public String mobile;
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY, updateStrategy = FieldStrategy.NOT_EMPTY)
    public String email;

    @JsonIgnore
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY, updateStrategy = FieldStrategy.NOT_EMPTY, select = false)
    public String salt;
    @JsonIgnore
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY, updateStrategy = FieldStrategy.NOT_EMPTY, select = false)
    public String password;

    @TableField(value = "roleId")
    public String roleId;

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