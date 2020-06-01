package com.demand.management.dto.demand;

import com.baomidou.mybatisplus.annotation.TableField;
import com.demand.management.entity.DemandStatus;
import com.demand.management.entity.DemandType;
import com.demand.management.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RelationDemandType extends DemandType {
    @TableField(value = "creator",exist = false)
    public User creator;
    @TableField(value = "demandStatusList",exist = false)
    public List<DemandStatus> demandStatusList;
}
