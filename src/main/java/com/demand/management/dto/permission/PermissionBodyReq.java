package com.demand.management.dto.permission;

import lombok.Data;

import java.util.Date;

@Data
public class PermissionBodyReq {
    public String id;
    public String name;
    /**
     * 权限类型 1 或 2
     * 1：访问权限，控制路由route
     * 2：操作权限控制，控制操作项operating
     */
    public String type;
    public String route;
    public String operating;
    public String module;
    public String creator;
    public Date createDate;
    public Date updateDate;
    public String isOn;
}
