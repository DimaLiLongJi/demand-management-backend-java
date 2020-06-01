package com.demand.management.dto.approver;

import com.baomidou.mybatisplus.annotation.TableField;
import com.demand.management.entity.Approver;
import com.demand.management.entity.DemandStatus;
import com.demand.management.entity.DemandType;
import com.demand.management.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RelationApprover extends Approver {
    @TableField(value = "demandType",exist = false)
    public DemandType demandType;

    @TableField(value = "demandStatus",exist = false)
    public DemandStatus demandStatus;

    @TableField(value = "user",exist = false)
    public User user;
}
