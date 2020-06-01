package com.demand.management.dto.demand;

import com.baomidou.mybatisplus.annotation.TableField;
import com.demand.management.entity.Demand;
import com.demand.management.entity.DemandNode;
import com.demand.management.entity.DemandProgress;
import com.demand.management.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RelationDemandProgress extends DemandProgress {
    @TableField(value = "creator",exist = false)
    public User creator;

    @TableField(value = "user",exist = false)
    public User user;

    @TableField(value = "demand",exist = false)
    public Demand demand;

    @TableField(value = "demandNodeList",exist = false)
    public List<DemandNode> demandNodeList;
}
