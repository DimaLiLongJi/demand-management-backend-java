package com.demand.management.dto.demand;

import lombok.Data;

import java.util.Date;

@Data
public class DemandNodeBodyReq {
    public String id;
    public String detail;
    public String demandProgress;
    public String creator;
    public Date createDate;
    public Date finishDate;
    public Date deleteDate;
    public String finished;
}
