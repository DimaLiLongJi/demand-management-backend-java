package com.demand.management.dto.role;

import com.demand.management.entity.DemandType;
import com.demand.management.entity.Permission;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RoleBodyReq {
    public String id;
    public String name;
    public List<String> permissionIds;
    public List<Permission> permissionList;
    public List<String> demandTypeIds;
    public List<DemandType> demandTypeList;
    public String creator;
    public Date createDate;
    public Date updateDate;
    public String isOn;
}
