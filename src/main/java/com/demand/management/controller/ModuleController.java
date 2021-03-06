package com.demand.management.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demand.management.dto.module.ModuleBodyReq;
import com.demand.management.dto.module.RelationModule;
import com.demand.management.dto.response.DataResponse;
import com.demand.management.dto.response.PageDataResponse;
import com.demand.management.service.ModuleService;
import com.demand.management.utils.ManagementResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/module")
public class ModuleController {
    @Autowired
    private ModuleService service;

    @RequestMapping(value= "", method = { RequestMethod.POST, RequestMethod.PUT } )
    public DataResponse<RelationModule> create(@RequestBody ModuleBodyReq body, HttpServletRequest request) {
        body.setCreator(request.getAttribute("authId").toString());
        RelationModule module = this.service.create(body);
        if (module != null) return ManagementResponseUtil.<RelationModule>buildDataResponse("创建模块成功", true, module);
        else return ManagementResponseUtil.<RelationModule>buildDataResponse("创建模块失败", false);
    }

    @GetMapping("/all")
    public PageDataResponse<RelationModule> getAll() {
        Page<RelationModule> list = this.service.findAll();
        return ManagementResponseUtil.<RelationModule>buildPageDataResponse("获取模块列表成功", true, list.getRecords(), list.getTotal());
    }
}
