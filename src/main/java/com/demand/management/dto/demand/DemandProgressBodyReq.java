package com.demand.management.dto.demand;

import lombok.Data;

import java.util.Date;

@Data
public class DemandProgressBodyReq {
    public String id;
    public String demand;
    public String user;
    public String creator;
    public Date createDate;
    public Date updateDate;
    public Date finishDate;
    public Date scheduleStartDate;
    public Date scheduleEndDate;
    // '1' | '2'
    public String finished;
    // DemandLogTypeEnum
    public String type;
}
