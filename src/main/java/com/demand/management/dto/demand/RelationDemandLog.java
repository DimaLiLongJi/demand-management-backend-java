package com.demand.management.dto.demand;

import com.baomidou.mybatisplus.annotation.TableField;
import com.demand.management.entity.Demand;
import com.demand.management.entity.DemandLog;
import com.demand.management.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RelationDemandLog extends DemandLog {
    @TableField(value = "creator",exist = false)
    public User creator;
    @TableField(value = "demand",exist = false)
    public Demand demand;
}
