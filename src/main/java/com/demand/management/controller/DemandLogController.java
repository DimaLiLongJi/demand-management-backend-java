package com.demand.management.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demand.management.dto.demand.DemandLogBodyReq;
import com.demand.management.dto.demand.RelationDemandLog;
import com.demand.management.dto.response.DataResponse;
import com.demand.management.dto.response.PageDataResponse;
import com.demand.management.service.DemandLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/demand-log")
public class DemandLogController {
    @Autowired
    DemandLogService service;

    @PostMapping("")
    public DataResponse<RelationDemandLog> create(@RequestBody DemandLogBodyReq body, HttpServletRequest request) {
        body.setCreator(request.getAttribute("authId").toString());
        RelationDemandLog module = this.service.create(body);
        if (module != null) return new DataResponse<RelationDemandLog>("创建需求日志成功", true, module);
        else return new DataResponse<RelationDemandLog>("创建需求日志失败", false);
    }

    @GetMapping("/find")
    public PageDataResponse findAll(
            @RequestParam(required = false) String pageIndex,
            @RequestParam(required = false) String pageSize,
            @RequestParam(required = false) String demand,
            @RequestParam(required = false) String creator
    ) {
        Page<RelationDemandLog> list = this.service.findAll(pageIndex, pageSize, demand, creator);
        return new PageDataResponse("获取需求日志列表成功", true, list.getRecords(), list.getTotal());
    }
}
