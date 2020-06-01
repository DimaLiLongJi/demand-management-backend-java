package com.demand.management.dto.demand;

import lombok.Data;

import java.util.Date;

@Data
public class DemandLogBodyReq {
    public String id;
    // DemandLogTypeEnum
    public String type;
    // DemandLogPropertyEnum
    public String property;
    public String oldDetail;
    public String newDetail;
    public String demand;
    public String creator;
    public Date createDate;
}
