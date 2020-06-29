package com.demand.management.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demand.management.dto.demand.DemandStatusBodyReq;
import com.demand.management.dto.demand.RelationDemandStatus;
import com.demand.management.dto.response.BaseResponse;
import com.demand.management.dto.response.DataResponse;
import com.demand.management.dto.response.PageDataResponse;
import com.demand.management.service.DemandStatusService;
import com.demand.management.utils.ManagementResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/demand-status")
public class DemandStatusController {
    @Autowired
    DemandStatusService service;

    @PostMapping("")
    public DataResponse<RelationDemandStatus> create(
            @RequestBody DemandStatusBodyReq body,
            HttpServletRequest request
    ) {
        body.setCreator(request.getAttribute("authId").toString());
        RelationDemandStatus res = this.service.create(body);
        if (res != null) return ManagementResponseUtil.<RelationDemandStatus>buildDataResponse("创建需求状态成功", true, res);
        else return ManagementResponseUtil.<RelationDemandStatus>buildDataResponse("创建需求状态失败", false);
    }

    @GetMapping("/find")
    public PageDataResponse<RelationDemandStatus> find(
            @RequestParam(required = false) String pageIndex,
            @RequestParam(required = false) String pageSize,
            @RequestParam(required = false) String isOn
    ) {
        Page<RelationDemandStatus> list = this.service.findAll(pageIndex, pageSize, isOn);
        return ManagementResponseUtil.<RelationDemandStatus>buildPageDataResponse("获取需求状态列表成功", true, list.getRecords(), list.getTotal());
    }

    @GetMapping("/{id}")
    public DataResponse<RelationDemandStatus> getById(@PathVariable String id) {
        RelationDemandStatus role = this.service.findById(id);
        if (role != null) return ManagementResponseUtil.<RelationDemandStatus>buildDataResponse("获取id为" +id + "的需求状态成功", true, role);
        else return ManagementResponseUtil.<RelationDemandStatus>buildDataResponse("id为" +id + "的需求状态不存在", false);
    }

    @Transactional
    @RequestMapping(value= "/{id}", method = { RequestMethod.POST, RequestMethod.PUT } )
    public BaseResponse update(@PathVariable String id, @RequestBody DemandStatusBodyReq body) {
        if (!this.service.updateById(id, body)) return new BaseResponse("更新id为" +id + "的需求状态失败", false);
        RelationDemandStatus role = this.service.findById(id);
        if (role != null) return ManagementResponseUtil.buildBaseResponse("更新id为" +id + "的需求状态成功", true);
        else return ManagementResponseUtil.buildBaseResponse("id为" +id + "的需求状态不存在", false);
    }
}
