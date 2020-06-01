package com.demand.management.dto.demand;

import com.baomidou.mybatisplus.annotation.TableField;
import com.demand.management.entity.DemandNode;
import com.demand.management.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RelationDemandNode extends DemandNode {
    @TableField(value = "creator",exist = false)
    public User creator;
    @TableField(value = "demandProgress",exist = false)
    public RelationDemandProgress demandProgress;
}
