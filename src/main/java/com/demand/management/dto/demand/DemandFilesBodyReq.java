package com.demand.management.dto.demand;

import lombok.Data;

@Data
public class DemandFilesBodyReq {
    public String demandId;
    public String fileId;
    public String creator;
}
