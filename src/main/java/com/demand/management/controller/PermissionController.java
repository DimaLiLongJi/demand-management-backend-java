package com.demand.management.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demand.management.dto.permission.PermissionBodyReq;
import com.demand.management.dto.permission.RelationPermission;
import com.demand.management.dto.response.BaseResponse;
import com.demand.management.dto.response.DataResponse;
import com.demand.management.dto.response.PageDataResponse;
import com.demand.management.service.PermissionService;
import com.demand.management.utils.ManagementResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {
    @Autowired
    private PermissionService service;

    @GetMapping("/find")
    public PageDataResponse<RelationPermission> find(
            @RequestParam(required = false) String pageIndex,
            @RequestParam(required = false) String pageSize,
            @RequestParam(required = false) String isOn,
            @RequestParam(required = false) String type
    ) {
        Page<RelationPermission> list = this.service.findAll(pageIndex, pageSize, isOn, type);
        return ManagementResponseUtil.<RelationPermission>buildPageDataResponse("获取权限列表成功", true, list.getRecords(), list.getTotal());
    }

    @GetMapping("/{id}")
    public DataResponse<RelationPermission> getById(@PathVariable String id) {
        RelationPermission permission = this.service.findById(id);
        if (permission != null) return ManagementResponseUtil.<RelationPermission>buildDataResponse("获取id为" +id + "的权限成功", true, permission);
        else return ManagementResponseUtil.<RelationPermission>buildDataResponse("id为" +id + "的权限不存在", false);
    }

    @Transactional
    @PostMapping("")
    public DataResponse<RelationPermission> create(@RequestBody PermissionBodyReq permissionBody, HttpServletRequest request) {
        if (permissionBody.getType().equals("1") && permissionBody.getRoute() == null) return ManagementResponseUtil.<RelationPermission>buildDataResponse("创建权限失败，缺少访问权限", false);
        if (permissionBody.getType().equals("2") && permissionBody.getOperating() == null) return ManagementResponseUtil.<RelationPermission>buildDataResponse("创建权限失败，缺少操作权限", false);
        permissionBody.setCreator(request.getAttribute("authId").toString());
        RelationPermission permission = this.service.create(permissionBody);
        if (permission != null) return ManagementResponseUtil.<RelationPermission>buildDataResponse("创建权限成功", true, permission);
        else return ManagementResponseUtil.<RelationPermission>buildDataResponse("创建权限失败", false);
    }

    @Transactional
    @RequestMapping(value= "/{id}", method = { RequestMethod.POST, RequestMethod.PUT } )
    public BaseResponse update(@PathVariable String id, @RequestBody PermissionBodyReq permissionBody) {
        if (!this.service.updateById(id, permissionBody)) return ManagementResponseUtil.buildBaseResponse("更新id为" +id + "的权限失败", false);
        else return ManagementResponseUtil.buildBaseResponse("更新id为" +id + "的权限成功", true);
    }
}
