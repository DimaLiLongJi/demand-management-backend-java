package com.demand.management.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demand.management.dto.demand.DemandNodeBodyReq;
import com.demand.management.dto.demand.RelationDemandNode;
import com.demand.management.dto.response.BaseResponse;
import com.demand.management.dto.response.DataResponse;
import com.demand.management.dto.response.PageDataResponse;
import com.demand.management.service.DemandNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/demand-node")
public class DemandNodeController {
    @Autowired
    DemandNodeService service;

    @PostMapping("")
    public DataResponse<RelationDemandNode> create(@RequestBody DemandNodeBodyReq body, HttpServletRequest request) {
        body.setCreator(request.getAttribute("authId").toString());
        RelationDemandNode res = this.service.create(body);
        if (res != null) return new DataResponse<RelationDemandNode>("创建需求节点成功", true, res);
        else return new DataResponse<RelationDemandNode>("创建需求节点失败", false);
    }

    @GetMapping("/find")
    public PageDataResponse findAll(
            @RequestParam(required = false) String pageIndex,
            @RequestParam(required = false) String pageSize,
            @RequestParam(required = false) String demandProgress,
            @RequestParam(required = false) String user
    ) {
        Page<RelationDemandNode> list = this.service.findAll(pageIndex, pageSize, demandProgress, user);
        return new PageDataResponse("获取需求节点列表成功", true, list.getRecords(), list.getTotal());
    }

    @GetMapping("/{id}")
    public DataResponse<RelationDemandNode> findById(@PathVariable String id) {
        RelationDemandNode find = this.service.findById(id);
        if (find != null) return new DataResponse<RelationDemandNode>("获取id为" +id + "的需求节点成功", true, find);
        else return new DataResponse<RelationDemandNode>("id为" +id + "的需求节点不存在", false);
    }

    @PutMapping("/{id}")
    public BaseResponse update(
            @PathVariable String id,
            @RequestBody DemandNodeBodyReq body,
            HttpServletRequest request
    ) {
        body.setCreator(request.getAttribute("authId").toString());
        if (!this.service.updateById(id, body)) return new BaseResponse("更新id为" +id + "的需求节点失败", false);
        else return new BaseResponse("更新id为" +id + "的需求节点成功", true);
    }

    @DeleteMapping("/{id}")
    public BaseResponse delete(@PathVariable String id) {
        if (this.service.delete(id)) return new BaseResponse("删除id为" +id + "的需求节点成功", true);
        else return new BaseResponse("删除id为" +id + "的需求节点失败", false);
    }

}
