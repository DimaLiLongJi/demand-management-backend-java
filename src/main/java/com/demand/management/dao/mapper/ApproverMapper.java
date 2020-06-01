package com.demand.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demand.management.dto.approver.RelationApprover;
import com.demand.management.entity.Approver;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ApproverMapper extends BaseMapper<Approver> {
    List<RelationApprover> findByDemandByWhere(
            @Param("demandTypeId") String demandTypeId,
            @Param("demandStatusId") String demandStatusId,
            @Param("userId") String userId
    );
}
