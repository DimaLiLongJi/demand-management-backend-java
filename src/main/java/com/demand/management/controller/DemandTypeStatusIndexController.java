package com.demand.management.controller;

import com.demand.management.dto.demand.DemandTypeStatusIndexReqItem;
import com.demand.management.dto.demand.RelationDemandTypeStatusIndex;
import com.demand.management.dto.response.DataResponse;
import com.demand.management.service.DemandTypeStatusIndexService;
import com.demand.management.utils.ManagementResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demand-type-stataus-index")
public class DemandTypeStatusIndexController {
    @Autowired
    private DemandTypeStatusIndexService service;

    @GetMapping("/{typeId}")
    public DataResponse<List<RelationDemandTypeStatusIndex>> getByDemandTypeId(@PathVariable String typeId) {
        List<RelationDemandTypeStatusIndex> list = this.service.findByDemandTypeId(typeId);
        if (list != null) return ManagementResponseUtil.<List<RelationDemandTypeStatusIndex>>buildDataResponse("获取demandTypeId为" +typeId + "的需求类型顺序成功", true, list);
        else return ManagementResponseUtil.<List<RelationDemandTypeStatusIndex>>buildDataResponse("demandTypeId为" + typeId + "的需求类型顺序不存在", false);
    }

    @Transactional
    @PostMapping("/{typeId}")
    public DataResponse<List<RelationDemandTypeStatusIndex>> create(@PathVariable String typeId, @RequestBody List<DemandTypeStatusIndexReqItem> body) {
        List<RelationDemandTypeStatusIndex> list = this.service.createIndexList(typeId, body);
        if (list != null) return ManagementResponseUtil.<List<RelationDemandTypeStatusIndex>>buildDataResponse("创建demandTypeId为" +typeId + "的需求类型顺序成功", true, list);
        else return ManagementResponseUtil.<List<RelationDemandTypeStatusIndex>>buildDataResponse("创建demandTypeId为" + typeId + "的需求类型顺序不失败", false);
    }
}
