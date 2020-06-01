package com.demand.management.dto.demand;

import com.baomidou.mybatisplus.annotation.TableField;
import com.demand.management.entity.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RelationDemand extends Demand {
    @TableField(value = "demandType",exist = false)
    public DemandType demandType;

    @TableField(value = "demandStatus",exist = false)
    public DemandStatus demandStatus;

    @TableField(value = "creator",exist = false)
    public User creator;

    // 需求进度
    @TableField(value = "demandProgressList",exist = false)
    public List<RelationDemandProgress> demandProgressList;

    // 需求提出人 不可为空
    @TableField(value = "proposerList",exist = false)
    public List<User> proposerList;

    // 需求对接人 可以为空
    @TableField(value = "brokerList",exist = false)
    public List<User> brokerList;

    // 开发者列表
    @TableField(value = "developerList",exist = false)
    public List<User> developerList;

    // 运维或其他人员列表
    @TableField(value = "devopsList",exist = false)
    public List<User> devopsList;

    @TableField(value = "fileList",exist = false)
    public List<File> fileList;
}
