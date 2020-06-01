package com.demand.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "file")
public class File {
    @TableId(type = IdType.AUTO)
    public String id;

    public String name;

    @TableField(value = "creatorId")
    public String creatorId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    // @JsonFormat(pattern = "YYYY-MM-DD hh:mm:ss", locale = "zh", timezone = "GMT+8")
    @TableField(value = "create_date",exist = true, fill = FieldFill.INSERT)
    public Date createDate;
}
