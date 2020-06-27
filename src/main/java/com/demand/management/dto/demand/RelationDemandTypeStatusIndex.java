package com.demand.management.dto.demand;

import com.baomidou.mybatisplus.annotation.TableField;
import com.demand.management.entity.DemandStatus;
import com.demand.management.entity.DemandType;
import com.demand.management.entity.DemandTypeStatusIndex;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RelationDemandTypeStatusIndex extends DemandTypeStatusIndex {
    @TableField(value = "demandType",exist = false)
    public DemandType demandType;

    @TableField(value = "demandStatus",exist = false)
    public DemandStatus demandStatus;
}
