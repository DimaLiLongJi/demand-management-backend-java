package com.demand.management.dto.demand;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DemandBodyReq {
    public String id;
    public String name;
    public String isPending;
    public String manDay;
    public String detail;
    public String comment;
    public String url;
    public String demandType;
    public String demandStatus;
    public String creator;
    public List<String> proposerIds;
    public List<String> brokerIds;
    public List<String> developerIds;
    public List<String> devopsIds;
    public List<String> fileIds;
    public Date expectDate;
    public Date scheduleStartDate;
    public Date scheduleEndDate;
    public Date finishDate;
    public Date createDate;
    public Date updateDate;
    public String isOn;
}
