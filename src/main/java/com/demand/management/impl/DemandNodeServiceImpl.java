package com.demand.management.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demand.management.dao.mapper.DemandNodeMapper;
import com.demand.management.dto.demand.*;
import com.demand.management.entity.DemandLog;
import com.demand.management.entity.DemandNode;
import com.demand.management.entity.DemandProgress;
import com.demand.management.entity.User;
import com.demand.management.service.*;
import com.demand.management.utils.ExtendPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DemandNodeServiceImpl  extends ServiceImpl<DemandNodeMapper, DemandNode> implements DemandNodeService {
    @Autowired
    UserService userService;

    @Autowired
    DemandLogService demandLogService;

    @Autowired
    DemandProgressService demandProgressService;

    @Override
    public RelationDemandNode create(DemandNodeBodyReq body) {
        DemandNode instance = new DemandNode();
        instance.setCreatorId(body.getCreator());
        instance.setDetail(body.getDetail());
        instance.setDemandProgressId(body.getDemandProgress());
        instance.setCreateDate(body.getCreateDate());
        instance.setDeleteDate(body.getDeleteDate());
        instance.setFinishDate(body.getFinishDate());
        boolean res = this.save(instance);
        if (res) {
            DemandLog log = new DemandLog();
            DemandProgress demandProgress = this.demandProgressService.getById(body.getDemandProgress());
            log.setCreatorId(body.getCreator());
            log.setProperty(DemandLogPropertyEnum.demandNode.getProperty());
            log.setDemandId(demandProgress.getDemandId());
            log.setType(DemandLogTypeEnum.CREATE.getType());
            log.setNewDetail(body.getDetail());
            this.demandLogService.save(log);

            return this.findById(instance.getId());
        } else return null;
    }

    @Override
    public RelationDemandNode findById(String id) {
        return this.baseMapper.findById(id);
    }

    @Override
    public Page<RelationDemandNode> findAll(String pageIndex, String pageSize, String demandProgress, String user) {
        ExtendPage<RelationDemandNode> page = new ExtendPage<RelationDemandNode>(pageIndex, pageSize);
        return page.setTotalAndRecord(this.baseMapper.findByPage(page.getPageForFind(), demandProgress, user));
    }

    @Override
    @Transactional
    public boolean updateById(String id, DemandNodeBodyReq body) {
        RelationDemandNode found = this.findById(id);
        if (found == null) return false;
        UpdateWrapper<DemandNode> update = new UpdateWrapper<DemandNode>();
        update.eq("id", id);
        update.set("id", id);

        if (body.getDetail() != null && !body.getDetail().equals(found.getDetail())) update.set("detail", body.getDetail());
        if (body.getDemandProgress() != null && !body.getDemandProgress().equals(found.getDemandProgressId())) update.set("demandProgressId", body.getDemandProgress());
        if (body.getCreator() != null && !body.getCreator().equals(found.getCreatorId())) update.set("creatorId", body.getCreator());
        if (body.getCreateDate() != null && !body.getCreateDate().equals(found.getCreateDate())) update.set("create_date", body.getCreateDate());
        if (body.getDeleteDate() != null && !body.getDeleteDate().equals(found.getDeleteDate())) update.set("delete_date", body.getDeleteDate());
        if (body.getFinished() != null && body.getFinished().equals("1"))  update.set("finish_date", new Date());
        if (body.getFinished() != null && body.getFinished().equals("2"))  update.set("finish_date", null);
        boolean res = this.update(update);
        List<DemandLog> logList = this.buildDemandLogs(id, found, body);
        this.demandLogService.saveBatch(logList);
        return res;
    }

    @Override
    public boolean delete(String id) {
        DemandLog log = new DemandLog();
        RelationDemandNode find = this.findById(id);
        log.setCreatorId(find.getCreatorId());
        log.setProperty(DemandLogPropertyEnum.demandNode.getProperty());
        log.setDemandId(find.getDemandProgress().getDemandId());
        log.setType(DemandLogTypeEnum.DELETE.getType());
        log.setNewDetail(find.getDetail());

        boolean res = this.removeById(id);
        if (res) {
            this.demandLogService.save(log);
        }
        return res;
    }

    public List<DemandLog> buildDemandLogs(String demandNodeId, RelationDemandNode found, DemandNodeBodyReq body) {
        List<DemandLog> logList = new ArrayList<DemandLog>();
        User user = this.userService.getById(found.getDemandProgress().getUserId());
        if (body.getFinished() != null && body.getFinished().equals("1")) {
            DemandLog log = new DemandLog();
            log.setCreatorId(body.getCreator());
            log.setProperty(DemandLogPropertyEnum.demandNode.getProperty());
            log.setDemandId(found.getDemandProgress().getDemandId());
            log.setType(DemandLogTypeEnum.FINISH.getType());
            log.setNewDetail(user.getName() + "的id为" + demandNodeId + "的需求节点");
            logList.add(log);
        }
        if (body.getFinished() != null && body.getFinished().equals("2")) {
            DemandLog log = new DemandLog();
            log.setCreatorId(body.getCreator());
            log.setProperty(DemandLogPropertyEnum.progress.getProperty());
            log.setDemandId(found.getDemandProgress().getDemandId());
            log.setType(DemandLogTypeEnum.UNFINISH.getType());
            log.setNewDetail(user.getName() + "的id为" + demandNodeId + "的需求节点");
            logList.add(log);
        }
        if (body.getDetail() != null && !body.getDetail().equals(found.getDetail())) {
            DemandLog log = new DemandLog();
            log.setCreatorId(body.getCreator());
            log.setProperty(DemandLogPropertyEnum.progress.getProperty());
            log.setDemandId(found.getDemandProgress().getDemandId());
            log.setType(DemandLogTypeEnum.UPDATE.getType());
            log.setOldDetail(found.getDetail());
            log.setNewDetail(body.getDetail());
            logList.add(log);
        }
        return logList;
    }
}
