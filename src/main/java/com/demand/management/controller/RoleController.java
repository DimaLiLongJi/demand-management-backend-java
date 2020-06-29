package com.demand.management.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demand.management.dto.response.BaseResponse;
import com.demand.management.dto.response.DataResponse;
import com.demand.management.dto.response.PageDataResponse;
import com.demand.management.dto.role.RelationRole;
import com.demand.management.dto.role.RoleBodyReq;
import com.demand.management.service.RoleService;
import com.demand.management.utils.ManagementResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService service;

    @GetMapping("/find")
    public PageDataResponse<RelationRole> find(
            @RequestParam(required = false) String pageIndex,
            @RequestParam(required = false) String pageSize,
            @RequestParam(required = false) String isOn
    ) {
        Page<RelationRole> list = this.service.findAll(pageIndex, pageSize, isOn);
        return ManagementResponseUtil.<RelationRole>buildPageDataResponse("获取角色列表成功", true, list.getRecords(), list.getTotal());
    }

    @GetMapping("/{id}")
    public DataResponse<RelationRole> getById(@PathVariable String id) {
        RelationRole role = this.service.findById(id);
        if (role != null) return ManagementResponseUtil.<RelationRole>buildDataResponse("获取id为" +id + "的角色成功", true, role);
        else return ManagementResponseUtil.buildDataResponse("id为" +id + "的角色不存在", false);
    }

    @Transactional
    @PostMapping("")
    public DataResponse<RelationRole> create(@RequestBody RoleBodyReq body, HttpServletRequest request) {
        body.setCreator(request.getAttribute("authId").toString());
        RelationRole role = this.service.create(body);
        if (role != null) return new DataResponse<RelationRole>("创建权限成功", true, role);
        else return new DataResponse<RelationRole>("创建权限失败", false);
    }

    @Transactional
    @RequestMapping(value= "/{id}", method = { RequestMethod.POST, RequestMethod.PUT } )
    public BaseResponse update(@PathVariable String id, @RequestBody RoleBodyReq body) {
        if (!this.service.updateById(id, body)) return new BaseResponse("更新id为" +id + "的角色失败", false);
        RelationRole role = this.service.findById(id);
        if (role != null) return new BaseResponse("更新id为" +id + "的角色成功", true);
        else return new BaseResponse("id为" +id + "的用户不存在", false);
    }

}
