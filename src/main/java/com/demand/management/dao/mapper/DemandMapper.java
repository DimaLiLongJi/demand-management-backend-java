package com.demand.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demand.management.dto.demand.RelationDemand;
import com.demand.management.entity.Demand;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface DemandMapper extends BaseMapper<Demand> {
    RelationDemand findById(String id);
    List<RelationDemand> findByIds(List<String> ids);
    List<RelationDemand> findByPage(
            Page<RelationDemand> page,
            @Param("isNull") String isNull,
            @Param("keyword") String keyword,
            @Param("creator") String creator,
            @Param("proposer") String proposer,
            @Param("broker") String broker,
            @Param("devops") String devops,
            @Param("developer") String developer,
            @Param("approver") String approver,
            @Param("demandType") String demandType,
            @Param("demandStatus") String demandStatus,
            @Param("timeout") String timeout,
            @Param("demandCreateFromDate") Date demandCreateFromDate,
            @Param("demandCreateToDate") Date demandCreateToDate,
            @Param("demandEndFromDate") Date demandEndFromDate,
            @Param("demandEndToDate") Date demandEndToDate,
            @Param("scheduleStartFromDate") Date scheduleStartFromDate,
            @Param("scheduleStartToDate") Date scheduleStartToDate,
            @Param("scheduleEndFromDate") Date scheduleEndFromDate,
            @Param("scheduleEndToDate") Date scheduleEndToDate
    );
}
