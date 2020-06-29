package com.demand.management.controller;

import com.demand.management.dto.approver.RelationApprover;
import com.demand.management.dto.response.DataResponse;
import com.demand.management.service.ApproverService;
import com.demand.management.utils.ManagementResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/approver")
public class ApproverController {
    @Autowired
    ApproverService service;

    @GetMapping("/all")
    public DataResponse<List<RelationApprover>> getAll() {
        List<RelationApprover> list = this.service.findByDemandByWhere(null, null, null);
        return ManagementResponseUtil.<List<RelationApprover>>buildDataResponse("获取全部用户成功", true, list);
    }
}
