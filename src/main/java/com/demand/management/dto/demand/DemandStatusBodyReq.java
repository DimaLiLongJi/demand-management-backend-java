package com.demand.management.dto.demand;

import lombok.Data;

import java.util.Date;

@Data
public class DemandStatusBodyReq {
    public String id;
    public String name;
    public String isEndStatus;
    public String creator;
    public Date createDate;
    public Date updateDate;
    public String isOn;
}
