package com.demand.management.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demand.management.dto.demand.DemandBodyReq;
import com.demand.management.dto.demand.DemandFilesBodyReq;
import com.demand.management.dto.demand.RelationDemand;
import com.demand.management.entity.Demand;
import com.demand.management.entity.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public interface DemandService extends IService<Demand> {
    File addFile(MultipartFile file, String creatorId, String demandId);
    boolean deleteFile(DemandFilesBodyReq body);
    RelationDemand findById(String id);
    Page<RelationDemand> findAll(
            String pageIndex,
            String pageSize,
            String keyword,
            String isOn,
            String creator,
            String proposer,
            String broker,
            String devops,
            String developer,
            String approver,
            String demandType,
            String demandStatus,
            String timeout,
            Date deleteDate,
            Date demandCreateFromDate,
            Date demandCreateToDate,
            Date demandEndFromDate,
            Date demandEndToDate,
            Date scheduleStartFromDate,
            Date scheduleStartToDate,
            Date scheduleEndFromDate,
            Date scheduleEndToDate
    );
    Page<RelationDemand> findSelfAll(
            String selfId,
            String type, // 'proposer' | 'broker' | 'devops' | 'developer' | 'creator'
            String pageIndex,
            String pageSize,
            String keyword,
            String isOn,
            String demandType,
            String demandStatus,
            String timeout,
            Date deleteDate,
            Date demandCreateFromDate,
            Date demandCreateToDate,
            Date demandEndFromDate,
            Date demandEndToDate,
            Date scheduleStartFromDate,
            Date scheduleStartToDate,
            Date scheduleEndFromDate,
            Date scheduleEndToDate
    );
    RelationDemand create(DemandBodyReq body);
    boolean updateById(String id, DemandBodyReq body);
}
