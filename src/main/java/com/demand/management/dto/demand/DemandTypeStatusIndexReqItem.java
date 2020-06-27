package com.demand.management.dto.demand;

import lombok.Data;

@Data
public class DemandTypeStatusIndexReqItem {
    public String id;
    public String statusIndex;
    public String demandType;
    public String demandStatus;
}