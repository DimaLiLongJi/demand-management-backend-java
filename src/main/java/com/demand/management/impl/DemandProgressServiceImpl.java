package com.demand.management.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demand.management.dao.mapper.DemandProgressMapper;
import com.demand.management.dto.demand.DemandLogPropertyEnum;
import com.demand.management.dto.demand.DemandLogTypeEnum;
import com.demand.management.dto.demand.DemandProgressBodyReq;
import com.demand.management.dto.demand.RelationDemandProgress;
import com.demand.management.entity.Demand;
import com.demand.management.entity.DemandLog;
import com.demand.management.entity.DemandProgress;
import com.demand.management.service.DemandLogService;
import com.demand.management.service.DemandProgressService;
import com.demand.management.utils.ExtendPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DemandProgressServiceImpl extends ServiceImpl<DemandProgressMapper, DemandProgress> implements DemandProgressService {
    @Autowired
    DemandLogService demandLogService;

    @Override
    public RelationDemandProgress create(DemandProgressBodyReq body) {
        DemandProgress instance = new DemandProgress();
        instance.setType(body.getType());
        instance.setCreatorId(body.getCreator());
        instance.setDemandId(body.getDemand());
        instance.setUserId(body.getUser());
        instance.setScheduleStartDate(body.getScheduleStartDate());
        instance.setScheduleEndDate(body.getScheduleEndDate());
        instance.setCreateDate(body.getCreateDate());
        instance.setUpdateDate(body.getUpdateDate());
        instance.setFinishDate(body.getFinishDate());
        boolean res = this.save(instance);
        if (res) {
            return this.findById(instance.getId());
        } else return null;
    }

    @Override
    public RelationDemandProgress findById(String id) {
        return this.baseMapper.findById(id);
    }

    @Override
    public Page<RelationDemandProgress> findAll(String pageIndex, String pageSize, String demandId, String userId) {
        ExtendPage<RelationDemandProgress> page = new ExtendPage<RelationDemandProgress>(pageIndex, pageSize);
        return page.setTotalAndRecord(this.baseMapper.findByPage(page.getPageForFind(), demandId, userId));
    }

    @Override
    @Transactional
    public boolean updateById(String id, DemandProgressBodyReq body) {
        RelationDemandProgress found = this.findById(id);
        if (found == null) return false;
        UpdateWrapper<DemandProgress> update = new UpdateWrapper<DemandProgress>();
        update.eq("id", id);
        update.set("id", id);

        if (body.getType() != null && !body.getType().equals(found.getType())) update.set("type", body.getType());
        if (body.getDemand() != null && !body.getDemand().equals(found.getDemandId())) update.set("demandId", body.getDemand());
        if (body.getUser() != null && !body.getUser().equals(found.getUserId())) update.set("userId", body.getUser());
        if (body.getScheduleStartDate() != null && !body.getScheduleStartDate().equals(found.getScheduleStartDate())) update.set("schedule_start_date", body.getScheduleStartDate());
        if (body.getScheduleEndDate() != null && !body.getScheduleEndDate().equals(found.getScheduleEndDate())) update.set("schedule_end_date", body.getScheduleEndDate());
        if (body.getCreator() != null && !body.getCreator().equals(found.getCreatorId())) update.set("creatorId", body.getCreator());
        if (body.getCreateDate() != null && !body.getCreateDate().equals(found.getCreateDate())) update.set("create_date", body.getCreateDate());
        if (body.getUpdateDate() != null && !body.getUpdateDate().equals(found.getUpdateDate())) update.set("update_date", body.getUpdateDate());
        if (body.getFinished() != null && body.getFinished().equals("1"))  update.set("finish_date", new Date());
        if (body.getFinished() != null && body.getFinished().equals("2"))  update.set("finish_date", null);

        boolean res = this.update(update);
        List<DemandLog> logList = this.buildDemandLogs(found, body);
        this.demandLogService.saveBatch(logList);
        return res;
    }

    public List<DemandLog> buildDemandLogs(RelationDemandProgress found, DemandProgressBodyReq body) {
        List<DemandLog> logList = new ArrayList<DemandLog>();
        String progressName = found.getType() != null && found.getType().equals("1") ? "开发任务" : "运维或其他任务";
        if (body.getFinished() != null && body.getFinished().equals("1")) {
            DemandLog log = new DemandLog();
            log.setCreatorId(body.getCreator());
            log.setDemandId(found.getDemandId());
            log.setProperty(DemandLogPropertyEnum.progress.getProperty());
            log.setType(DemandLogTypeEnum.FINISH.getType());
            log.setNewDetail(found.getUser().getName() + "的" + progressName);
            logList.add(log);
        }
        if (body.getFinished() != null && body.getFinished().equals("2")) {
            DemandLog log = new DemandLog();
            log.setCreatorId(body.getCreator());
            log.setDemandId(found.getDemandId());
            log.setProperty(DemandLogPropertyEnum.progress.getProperty());
            log.setType(DemandLogTypeEnum.UNFINISH.getType());
            log.setNewDetail(found.getUser().getName() + "的" + progressName);
            logList.add(log);
        }
        DateFormat dateFormat = DateFormat.getDateInstance();
        if (body.getScheduleStartDate() != null) {
            DemandLog log = new DemandLog();
            log.setCreatorId(body.getCreator());
            log.setDemandId(found.getDemandId());
            log.setProperty(DemandLogPropertyEnum.progress.getProperty());
            log.setType(DemandLogTypeEnum.UPDATEPROGRESSDATE.getType());
            log.setOldDetail(found.getUser().getName() + "的" + progressName + "的 排期开始时间");
            log.setNewDetail(dateFormat.format(body.getScheduleStartDate()));
            logList.add(log);
        }
        if (body.getScheduleEndDate() != null) {
            DemandLog log = new DemandLog();
            log.setCreatorId(body.getCreator());
            log.setDemandId(found.getDemandId());
            log.setProperty(DemandLogPropertyEnum.progress.getProperty());
            log.setType(DemandLogTypeEnum.UPDATEPROGRESSDATE.getType());
            log.setOldDetail(found.getUser().getName() + "的" + progressName + "的 排期结束时间");
            log.setNewDetail(dateFormat.format(body.getScheduleEndDate()));
            log.setNewDetail(dateFormat.format(body.getScheduleEndDate()));
            logList.add(log);
        }
        return logList;
    }
}
