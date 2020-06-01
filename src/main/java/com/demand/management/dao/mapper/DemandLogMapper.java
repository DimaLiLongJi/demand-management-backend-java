package com.demand.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demand.management.dto.demand.RelationDemandLog;
import com.demand.management.entity.DemandLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DemandLogMapper extends BaseMapper<DemandLog> {
    RelationDemandLog findById(String id);
    List<RelationDemandLog> findByPage(Page<RelationDemandLog> page, @Param("demand") String demand, @Param("creator") String creator);
}
