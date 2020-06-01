package com.demand.management.dto.demand;

import com.demand.management.dto.approver.DemandTypeApproverItem;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DemandTypeBodyReq {
    public String id;
    public String name;
    public List<String> demandStatusIds;
    public String creator;
    public Date createDate;
    public Date updateDate;
    public String isOn;
    public List<DemandTypeApproverItem> approverList;
}
