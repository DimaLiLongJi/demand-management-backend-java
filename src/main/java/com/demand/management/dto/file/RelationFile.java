package com.demand.management.dto.file;

import com.baomidou.mybatisplus.annotation.TableField;
import com.demand.management.entity.File;
import com.demand.management.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RelationFile extends File {
    @TableField(value = "creator",exist = false)
    public User creator;
}
