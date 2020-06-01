package com.demand.management.dto.approver;

import lombok.Data;

import java.util.List;

@Data
public class DemandTypeApproverItem {
    String demandStatusId;
    List<String> approvers;
}
