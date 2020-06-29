package com.demand.management.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demand.management.dto.demand.DemandTypeBodyReq;
import com.demand.management.dto.demand.RelationDemandType;
import com.demand.management.dto.demand.RelationDemandTypeWithApprover;
import com.demand.management.dto.response.BaseResponse;
import com.demand.management.dto.response.DataResponse;
import com.demand.management.dto.response.PageDataResponse;
import com.demand.management.service.DemandTypeService;
import com.demand.management.utils.ManagementResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/demand-type")
public class DemandTypeController {
    @Autowired
    DemandTypeService service;

    @PostMapping("")
    public DataResponse<RelationDemandType> create(
            @RequestBody DemandTypeBodyReq body,
            HttpServletRequest request
    ) {
        body.setCreator(request.getAttribute("authId").toString());
        RelationDemandType res = this.service.create(body);
        if (res != null) return ManagementResponseUtil.<RelationDemandType>buildDataResponse("创建需求类型成功", true, res);
        else return ManagementResponseUtil.<RelationDemandType>buildDataResponse("创建需求类型失败", false);
    }

    @GetMapping("/find")
    public PageDataResponse<RelationDemandType> find(
            @RequestParam(required = false) String pageIndex,
            @RequestParam(required = false) String pageSize,
            @RequestParam(required = false) String isOn
    ) {
        Page<RelationDemandType> list = this.service.findAll(pageIndex, pageSize, isOn);
        return ManagementResponseUtil.<RelationDemandType>buildPageDataResponse("获取需求类型列表成功", true, list.getRecords(), list.getTotal());
    }

    @GetMapping("/{id}")
    public DataResponse<RelationDemandTypeWithApprover> getById(@PathVariable String id) {
        RelationDemandTypeWithApprover role = this.service.findById(id);
        if (role != null) return ManagementResponseUtil.<RelationDemandTypeWithApprover>buildDataResponse("获取id为" +id + "的需求类型成功", true, role);
        else return ManagementResponseUtil.<RelationDemandTypeWithApprover>buildDataResponse("id为" +id + "的需求类型不存在", false);
    }

    @Transactional
    @RequestMapping(value= "/{id}", method = { RequestMethod.POST, RequestMethod.PUT } )
    public BaseResponse update(@PathVariable String id, @RequestBody DemandTypeBodyReq body) {
        if (!this.service.updateById(id, body)) return ManagementResponseUtil.buildBaseResponse("更新id为" +id + "的需求类型失败", false);
        RelationDemandType role = this.service.findById(id);
        if (role != null) return ManagementResponseUtil.buildBaseResponse("更新id为" +id + "的需求类型成功", true);
        else return ManagementResponseUtil.buildBaseResponse("id为" +id + "的需求类型不存在", false);
    }
}
