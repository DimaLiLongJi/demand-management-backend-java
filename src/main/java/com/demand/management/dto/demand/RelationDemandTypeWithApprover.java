package com.demand.management.dto.demand;

import com.demand.management.dto.approver.DemandTypeApproverItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RelationDemandTypeWithApprover extends RelationDemandType {
    public List<DemandTypeApproverItem> approverList;
}
