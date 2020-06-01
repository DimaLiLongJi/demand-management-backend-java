package com.demand.management.dto.user;

import com.demand.management.entity.Permission;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserBodyReq {
    public String id;
    public String name;
    public String mobile;
    public String email;
    public String salt;
    public String password;
    public String role;
    public String creator;
    public List<String> permissionIds;
    public List<Permission> permissionList;
    public Date createDate;
    public Date updateDate;
    public String isOn;
}
