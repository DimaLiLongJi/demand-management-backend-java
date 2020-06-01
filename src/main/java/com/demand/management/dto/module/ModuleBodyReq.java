package com.demand.management.dto.module;

import lombok.Data;

import java.util.Date;

@Data
public class ModuleBodyReq {
    public String id;
    public String name;
    public String icon;
    public String creator;
    public Date createDate;
    public Date updateDate;
    public Date deleteDate;
}
