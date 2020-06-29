package com.demand.management.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demand.management.dto.demand.DemandProgressBodyReq;
import com.demand.management.dto.demand.RelationDemandProgress;
import com.demand.management.dto.response.BaseResponse;
import com.demand.management.dto.response.DataResponse;
import com.demand.management.dto.response.PageDataResponse;
import com.demand.management.service.DemandProgressService;
import com.demand.management.utils.ManagementResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/demand-progress")
public class DemandProgressController {
    @Autowired
    DemandProgressService service;

    @PostMapping("")
    public DataResponse<RelationDemandProgress> create(@RequestBody DemandProgressBodyReq body, HttpServletRequest request) {
        body.setCreator(request.getAttribute("authId").toString());
        RelationDemandProgress res = this.service.create(body);
        if (res != null) return ManagementResponseUtil.<RelationDemandProgress>buildDataResponse("创建需求进度成功", true, res);
        else return ManagementResponseUtil.<RelationDemandProgress>buildDataResponse("创建需求进度失败", false);
    }

    @GetMapping("/find")
    public PageDataResponse<RelationDemandProgress> find(
            @RequestParam(required = false) String pageIndex,
            @RequestParam(required = false) String pageSize,
            @RequestParam(required = false) String demand,
            @RequestParam(required = false) String user
    ) {
        Page<RelationDemandProgress> list = this.service.findAll(pageIndex, pageSize, demand, user);
        return ManagementResponseUtil.<RelationDemandProgress>buildPageDataResponse("获取需求进度列表成功", true, list.getRecords(), list.getTotal());
    }

    @GetMapping("/{id}")
    public DataResponse<RelationDemandProgress> getById(@PathVariable String id) {
        RelationDemandProgress find = this.service.findById(id);
        if (find != null) return ManagementResponseUtil.<RelationDemandProgress>buildDataResponse("获取id为" +id + "的需求进度成功", true, find);
        else return ManagementResponseUtil.<RelationDemandProgress>buildDataResponse("id为" +id + "的需求进度不存在", false);
    }

    @RequestMapping(value= "/{id}", method = { RequestMethod.POST, RequestMethod.PUT } )
    public BaseResponse updateById(
            @PathVariable String id,
            @RequestBody DemandProgressBodyReq body,
            HttpServletRequest request
    ) {
        body.setCreator(request.getAttribute("authId").toString());
        if (!this.service.updateById(id, body)) return ManagementResponseUtil.buildBaseResponse("更新id为" +id + "的需求进度失败", false);
        RelationDemandProgress find = this.service.findById(id);
        if (find != null) return ManagementResponseUtil.buildBaseResponse("更新id为" +id + "的需求进度成功", true);
        else return ManagementResponseUtil.buildBaseResponse("id为" +id + "的需求进度不存在", false);
    }

    @DeleteMapping("/{id}")
    public BaseResponse delete(@PathVariable String id) {
        if (this.service.removeById(id)) return ManagementResponseUtil.buildBaseResponse("删除id为" +id + "的需求进度成功", true);
        else return ManagementResponseUtil.buildBaseResponse("删除id为" +id + "的需求进度失败", false);
    }
}
