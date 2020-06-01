package com.demand.management.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demand.management.dao.mapper.DemandMapper;
import com.demand.management.dao.mapper.FileMapper;
import com.demand.management.dao.mapper.UserMapper;
import com.demand.management.dto.approver.RelationApprover;
import com.demand.management.dto.demand.*;
import com.demand.management.entity.*;
import com.demand.management.service.*;
import com.demand.management.utils.CommonUtil;
import com.demand.management.utils.ExtendPage;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DemandServiceImpl extends ServiceImpl<DemandMapper, Demand> implements DemandService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    DemandBrokerService brokerService;
    @Autowired
    DemandProposerService proposerService;
    @Autowired
    DemandDeveloperService developerService;
    @Autowired
    DemandDevopsService devopsService;
    @Autowired
    DemandStatusService statusService;
    @Autowired
    DemandProgressService progressService;
    @Autowired
    DemandLogService logService;
    @Autowired
    FileMapper fileMapper;
    @Autowired
    DemandFilesService demandFilesService;
    @Autowired
    FileService fileService;
    @Autowired
    ApproverService approverService;
    @Autowired
    EmailService emailService;

    @Override
    public File addFile(MultipartFile file, String creatorId, String demandId) {
        String newFileName = this.fileService.writeFile(file);
        File addFile = new File();
        addFile.setName(newFileName);
        addFile.setCreatorId(creatorId);
        addFile.setCreateDate(new Date());
        this.fileService.save(addFile);
        if (demandId == null || demandId.isEmpty()) return addFile;

        Demand demand = this.getById(demandId);
        if (demand == null) return addFile;

        DemandFiles demandFiles = new DemandFiles();
        demandFiles.setDemand(demandId);
        demandFiles.setFile(addFile.getId());
        this.demandFilesService.save(demandFiles);

        DemandLog log = new DemandLog();
        log.setCreatorId(creatorId);
        log.setType(DemandLogTypeEnum.CREATE.getType());
        log.setDemandId(demandId);
        log.setProperty(DemandLogPropertyEnum.file.getProperty());
        log.setNewDetail(addFile.getName());
        this.logService.save(log);

        return addFile;
    }

    @Override
    @Transactional
    public boolean deleteFile(DemandFilesBodyReq body) {
        Demand demand = this.getById(body.getDemandId());
        if (demand == null) return false;
        File file = this.fileMapper.findById(body.getFileId());
        if (file == null) return true;
        UpdateWrapper<DemandFiles> remove = new UpdateWrapper<DemandFiles>();
        remove.eq("demand", body.getDemandId());
        remove.eq("file", body.getFileId());
        boolean res = this.demandFilesService.remove(remove);

        DemandLog log = new DemandLog();
        log.setCreatorId(body.getCreator());
        log.setType(DemandLogTypeEnum.DELETE.getType());
        log.setDemandId(body.getDemandId());
        log.setProperty(DemandLogPropertyEnum.file.getProperty());
        log.setNewDetail(file.getName());
        this.logService.save(log);

        return res;
    }

    @Override
    public RelationDemand findById(String id) {
        return this.baseMapper.findById(id);
    }

    @Override
    public Page<RelationDemand> findAll(
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
    ) {
        ExtendPage<RelationDemand> page = new ExtendPage<RelationDemand>(pageIndex, pageSize);
        String isNull = CommonUtil.buildWithIsOn(isOn);
        List<RelationDemand> records = this.baseMapper.findByPage(
                page.getPageForFind(),
                isNull,
                keyword,
                creator,
                proposer,
                broker,
                devops,
                developer,
                approver,
                demandType,
                demandStatus,
                timeout,
                demandCreateFromDate,
                demandCreateToDate,
                demandEndFromDate,
                demandEndToDate,
                scheduleStartFromDate,
                scheduleStartToDate,
                scheduleEndFromDate,
                scheduleEndToDate
        );
        return page.setTotalAndRecord(records);
    }

    @Override
    public Page<RelationDemand> findSelfAll(
            String selfId,
            String type,
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
    ) {
        String creator = null;
        String proposer = null;
        String broker = null;
        String developer = null;
        String devops = null;
        String approver = null;
        if (type != null && type.equals("creator")) creator = selfId;
        if (type != null && type.equals("proposer")) proposer = selfId;
        if (type != null && type.equals("broker")) broker = selfId;
        if (type != null && type.equals("developer")) developer = selfId;
        if (type != null && type.equals("approver")) approver = selfId;
        return this.findAll(
                pageIndex,
                pageSize,
                keyword,
                isOn,
                creator,
                proposer,
                broker,
                devops,
                developer,
                approver,
                demandType,
                demandStatus,
                timeout,
                deleteDate,
                demandCreateFromDate,
                demandCreateToDate,
                demandEndFromDate,
                demandEndToDate,
                scheduleStartFromDate,
                scheduleStartToDate,
                scheduleEndFromDate,
                scheduleEndToDate
        );
    }


    @Override
    @Transactional
    public RelationDemand create(DemandBodyReq body) {
        Demand newOne = new Demand();
        newOne.setName(body.getName());
        newOne.setDetail(body.getDetail());
        newOne.setComment(body.getComment());
        newOne.setUrl(body.getUrl());
        newOne.setManDay(body.getManDay());
        newOne.setDemandTypeId(body.getDemandType());
        // 如果存在isPending 则设置，否则为 notPending
        if (body.getIsPending() != null) newOne.setIsPending(body.getIsPending());
        else newOne.setIsPending(DemandPendingEnum.notPending.getType());
        if (body.getDemandStatus() != null) {
            newOne.setDemandStatusId(body.getDemandStatus());
            DemandStatus demandStatusInfo = this.statusService.getById(body.getDemandStatus());
            if (demandStatusInfo.isEndStatus.equals("2")) newOne.setFinishDate(new Date());
            if (demandStatusInfo.isEndStatus.equals("1"))  newOne.setFinishDate(null);
            List<RelationApprover> approverList = this.approverService.findByDemandByWhere(body.getDemandType(), body.getDemandStatus(), null);
            // 如果是需求的类型和状态有审核的话，则为需求的isPending为待审核
            if (approverList != null && approverList.size() > 0) newOne.setIsPending(DemandPendingEnum.isPending.getType());
            else newOne.setIsPending(DemandPendingEnum.notPending.getType());
        }
        newOne.setCreatorId(body.getCreator());
        newOne.setExpectDate(body.getExpectDate());
        newOne.setScheduleStartDate(body.getScheduleStartDate());
        newOne.setScheduleEndDate(body.getScheduleEndDate());
        newOne.setCreateDate(body.getCreateDate());
        newOne.setUpdateDate(body.getUpdateDate());
        newOne.setFinishDate(body.getFinishDate());
        boolean res = this.save(newOne);
        if (res) {
            String demandId = newOne.getId();
            // 创建4种用户关联
            this.<DemandBroker, DemandBrokerService>updateUserWithIds(demandId, body.getBrokerIds(), this.brokerService);
            this.<DemandProposer, DemandProposerService>updateUserWithIds(demandId, body.getProposerIds(), this.proposerService);
            this.<DemandDeveloper, DemandDeveloperService>updateUserWithIds(demandId, body.getDeveloperIds(), this.developerService);
            this.<DemandDevops, DemandDevopsService>updateUserWithIds(demandId, body.getDevopsIds(), this.devopsService);

            if (body.getFileIds() != null && body.getFileIds().size() > 0) {
                this.demandFilesService.addRight(demandId, body.getFileIds());
            }
            // 创建进度
            this.buildProgress(newOne, body);
            // 创建日志
            DemandLog log = new DemandLog();
            log.setCreatorId(newOne.getCreatorId());
            log.setType(DemandLogTypeEnum.CREATE.getType());
            log.setDemandId(demandId);
            log.setProperty(DemandLogPropertyEnum.demand.getProperty());
            log.setNewDetail(newOne.getName());
            this.logService.save(log);

            return this.findById(newOne.getId());
        } else return null;
    }

    @Override
    @Transactional
    public boolean updateById(String id, DemandBodyReq body) {
        RelationDemand found = this.findById(id);
        if (found == null) return false;
        UpdateWrapper<Demand> update = new UpdateWrapper<Demand>();
        update.eq("id", id);
        update.set("id", id);

        // 如果要审核权限，则判断下是否有审核需求的权限
        if (body.getIsPending() != null && !body.getIsPending().equals(found.getIsPending())) {
            List<RelationApprover> approverList = this.approverService.findByDemandByWhere(body.getDemandType(), body.getDemandStatus(), body.getCreator());
            if (approverList == null || approverList.size() == 0) return false;
            else update.set("isPending", body.getIsPending());
        }

        // creator不能改 不能修改创建者
        if (body.getName() != null && !body.getName().equals(found.getName())) {
            update.set("name", body.getName());
        }
        if (body.getDetail() != null && !body.getDetail().equals(found.getDetail())) {
            update.set("detail", body.getDetail());
        }
        if (body.getComment() != null && !body.getComment().equals(found.getComment())) {
            update.set("comment", body.getComment());
        }
        if (body.getUrl() != null && !body.getUrl().equals(found.getUrl())) {
            update.set("url", body.getUrl());
        }
        if (body.getManDay() != null && !body.getManDay().equals(found.getManDay())) {
            update.set("manDay", body.getManDay());
        }
        if (body.getDemandType() != null && !body.getDemandType().equals(found.getDemandTypeId())) {
            update.set("demandTypeId", body.getDemandType());
        }
        if (body.getDemandStatus() != null && !body.getDemandStatus().equals(found.getDemandStatusId())) {
            update.set("demandStatusId", body.getDemandStatus());
            DemandStatus demandStatusInfo = this.statusService.getById(body.getDemandStatus());
            if (demandStatusInfo.isEndStatus.equals("2")) update.set("finish_date", new Date());
            if (demandStatusInfo.isEndStatus.equals("1"))  update.set("finish_date", null);
            List<RelationApprover> approverList = this.approverService.findByDemandByWhere(body.getDemandType(), body.getDemandStatus(), null);
            // 如果是需求的类型和状态有审核的话，则为需求的isPending为待审核
            if (approverList != null && approverList.size() > 0) update.set("isPending", DemandPendingEnum.isPending.getType());
            else update.set("isPending", DemandPendingEnum.notPending.getType());
        }
        if (body.getExpectDate() != null && !body.getExpectDate().equals(found.getExpectDate())) {
            update.set("expect_date", body.getExpectDate());
        }
        if (body.getScheduleStartDate() != null && !body.getScheduleStartDate().equals(found.getScheduleStartDate())) {
            update.set("schedule_start_date", body.getScheduleStartDate());
        }
        if (body.getScheduleEndDate() != null && !body.getScheduleEndDate().equals(found.getScheduleEndDate())) {
            update.set("schedule_end_date", body.getScheduleEndDate());
        }
        if (body.getCreateDate() != null && !body.getCreateDate().equals(found.getCreateDate())) {
            update.set("create_date", body.getCreateDate());
        }
        if (body.getUpdateDate() != null && !body.getUpdateDate().equals(found.getUpdateDate())) {
            update.set("update_date", body.getUpdateDate());
        }
        // 软删
        if (body.getIsOn() != null && body.getIsOn().equals("1")) update.set("delete_date", null);
        if (body.getIsOn() != null && body.getIsOn().equals("2")) {
            update.set("delete_date", new Date());
        }
        if (body.getFinishDate() != null && !body.getFinishDate().equals(found.getFinishDate())) {
            update.set("finish_date", body.getFinishDate());
        }

        boolean res = this.update(update);

        // 创建4种用户关联
        boolean res1 = this.<DemandBroker, DemandBrokerService>updateUserWithIds(id, body.getBrokerIds(), this.brokerService);
        boolean res2 = this.<DemandProposer, DemandProposerService>updateUserWithIds(id, body.getProposerIds(), this.proposerService);
        boolean res3 = this.<DemandDeveloper, DemandDeveloperService>updateUserWithIds(id, body.getDeveloperIds(), this.developerService);
        boolean res4 = this.<DemandDevops, DemandDevopsService>updateUserWithIds(id, body.getDevopsIds(), this.devopsService);

        RelationDemand newFound = this.findById(id);
        this.diffProgressList(id, body.getCreator(), newFound.getDemandProgressList(), body.getDeveloperIds(), body.getDevopsIds());

        if (body.getFileIds() != null && body.getFileIds().size() > 0) {
            this.demandFilesService.addRight(id, body.getFileIds());
        }

        this.diff4Log(found, body, newFound);
        // 只要1个更改句返回true
        return (res || res1 || res2 || res3 || res4);
    }

    private void buildProgress(Demand demand, DemandBodyReq body) {
        List<DemandProgress> demandProgressList = new ArrayList<DemandProgress>();

        String demandId = demand.getId();
        String creatorId = demand.getCreatorId();

        List<String> developerIds = body.getDeveloperIds();
        List<String> devopsIds = body.getDevopsIds();
        if (developerIds != null && developerIds.size() > 0) {
            developerIds.forEach((v) -> {
                DemandProgress demandProgress = new DemandProgress();
                demandProgress.setDemandId(demandId);
                demandProgress.setUserId(v);
                demandProgress.setCreatorId(creatorId);
                demandProgress.setType("1");
                demandProgressList.add(demandProgress);
            });
        }
        if (devopsIds != null && devopsIds.size() > 0) {
            devopsIds.forEach((v) -> {
                DemandProgress demandProgress = new DemandProgress();
                demandProgress.setDemandId(demandId);
                demandProgress.setUserId(v);
                demandProgress.setCreatorId(creatorId);
                demandProgress.setType("2");
                demandProgressList.add(demandProgress);
            });
        }
        this.progressService.saveOrUpdateBatch(demandProgressList);
        List<String> hasSendUsers = new ArrayList<String>();
        demandProgressList.forEach((progress) -> {
            if (!hasSendUsers.contains(progress.getUserId())) {
                hasSendUsers.add(progress.getUserId());
                User findToUser = this.userMapper.findById(progress.getUserId());
                User findFromUser = this.userMapper.findById(creatorId);
                if (findToUser != null && findToUser.getEmail() != null && findFromUser != null && findFromUser.getName() != null) {
                    this.emailService.sendEmail(findToUser.getEmail(), findFromUser.getName(), demandId);
                }
            }
        });
    }

    private <T, M extends BaseRelationService<T>> boolean updateUserWithIds(String demandId, List<String> userIds, M usedService) {
        if (userIds != null) {
            List<String> ids = new ArrayList<String>();
            if (userIds.size() > 0) {
                this.userMapper.selectBatchIds(userIds).forEach((k) -> {
                    ids.add(k.getId());
                });
            }
            usedService.updateByLeft(demandId, ids);
            return true;
        }
        return false;
    }

    private void diffProgressList(String demandId, String creatorId, List<RelationDemandProgress> progressList, List<String> developerIds, List<String> devopsIds) {
        List<DemandProgress> addDemandProgressList = new ArrayList<DemandProgress>();
//        List<String> deleteDemandProgressList = new ArrayList<String>();

        // 添加需求开发进度
        if (developerIds != null && developerIds.size() > 0) {
            developerIds.forEach((userId) -> {
                // 如果没有进度直接push
                if (progressList == null || progressList.size() == 0) {
                    DemandProgress progress = new DemandProgress();
                    progress.setDemandId(demandId);
                    progress.setUserId(userId);
                    progress.setCreatorId(creatorId);
                    progress.setType("1");
                    addDemandProgressList.add(progress);
                    return;
                }
                RelationDemandProgress findProgress = null;
                for (RelationDemandProgress p : progressList) {
                    // 没找到userId demandId type是1的进度就添加
                    if (p.getUserId().equals(userId) && p.getDemandId().equals(demandId) && p.getType().equals("1")) findProgress = p;
                }
                if (findProgress == null) {
                    DemandProgress progress = new DemandProgress();
                    progress.setDemandId(demandId);
                    progress.setUserId(userId);
                    progress.setCreatorId(creatorId);
                    progress.setType("1");
                    addDemandProgressList.add(progress);
                }
            });
        }

        // 添加需求运维或其他进度
        if (devopsIds != null && devopsIds.size() > 0) {
            devopsIds.forEach((userId) -> {
                // 如果没有进度直接push
                if (progressList == null || progressList.size() == 0) {
                    DemandProgress progress = new DemandProgress();
                    progress.setDemandId(demandId);
                    progress.setUserId(userId);
                    progress.setCreatorId(creatorId);
                    progress.setType("2");
                    addDemandProgressList.add(progress);
                    return;
                }
                RelationDemandProgress findProgress = null;
                for (RelationDemandProgress p : progressList) {
                    // 没找到userId demandId type是1的进度就添加
                    if (p.getUserId().equals(userId) && p.getDemandId().equals(demandId) && p.getType().equals("2")) findProgress = p;
                }
                if (findProgress == null) {
                    DemandProgress progress = new DemandProgress();
                    progress.setDemandId(demandId);
                    progress.setUserId(userId);
                    progress.setCreatorId(creatorId);
                    progress.setType("2");
                    addDemandProgressList.add(progress);
                }
            });
        }
        // 旧进度在新进度中没有的则删除
//        if (progressList != null && progressList.size() > 0) {
//            progressList.forEach((p) -> {
//                if (p.getType().equals("1")) {
//                    if (developerIds == null || developerIds.size() == 0 || developerIds.indexOf(p.getUserId()) == -1) deleteDemandProgressList.add(p.getId());
//                }
//                if (p.getType().equals("2")) {
//                    if (devopsIds == null || devopsIds.size() == 0 || devopsIds.indexOf(p.getUserId()) == -1) deleteDemandProgressList.add(p.getId());
//                }
//            });
//        }

        if (addDemandProgressList.size() > 0) {
            this.progressService.saveBatch(addDemandProgressList);
            List<String> hasSendUsers = new ArrayList<String>();
            addDemandProgressList.forEach((progress) -> {
//                if (!hasSendUsers.contains(progress.getUserId())) {
                    hasSendUsers.add(progress.getUserId());
                    User findToUser = this.userMapper.findById(progress.getUserId());
                    User findFromUser = this.userMapper.findById(creatorId);
                    if (findToUser != null && findToUser.getEmail() != null && findFromUser != null && findFromUser.getName() != null) {
                        this.emailService.sendEmail(findToUser.getEmail(), findFromUser.getName(), demandId);
                    }
//                }
            });
        }

//        if (deleteDemandProgressList.size() > 0) {
//            this.progressService.removeByIds(deleteDemandProgressList);
//        }
    }

    private void diff4Log(RelationDemand demandInfo, DemandBodyReq params, RelationDemand newDemand) {
        List<DemandLog> logList = new ArrayList<DemandLog>();
        if (params == null) return;
        if (params.getBrokerIds() != null && this.userIdListIsChanged(params.getBrokerIds(), demandInfo.getBrokerList())) {
            String oldDetail = null;
            if (demandInfo.getBrokerList() != null && demandInfo.getBrokerList().size() > 0) {
                List<String> oldDetailList = new ArrayList<String>();
                demandInfo.getBrokerList().forEach((user) -> {
                    oldDetailList.add(user.getName());
                });
                oldDetail = StringUtils.join(oldDetailList, ',');
            }

            String newDetail = "";
            List<String> newUsers = new ArrayList<String>();
            if (params.getBrokerIds() != null && params.getBrokerIds().size() >0) {
                this.userMapper.selectBatchIds(params.getBrokerIds()).forEach((k) -> {
                    newUsers.add(k.getName());
                });
            }
            newDetail = StringUtils.join(newUsers, ',');

            DemandLog log = new DemandLog();
            log.setCreatorId(params.getCreator());
            log.setType(DemandLogTypeEnum.UPDATE.getType());
            log.setDemandId(demandInfo.getId());
            log.setProperty(DemandLogPropertyEnum.broker.getProperty());
            log.setOldDetail(oldDetail);
            log.setNewDetail(newDetail);
            logList.add(log);
        }

        if (params.getDeveloperIds() != null && this.userIdListIsChanged(params.getDeveloperIds(), demandInfo.getDeveloperList())) {
            String oldDetail = null;
            if (demandInfo.getDeveloperList() != null && demandInfo.getDeveloperList().size() > 0) {
                List<String> oldDetailList = new ArrayList<String>();
                demandInfo.getDeveloperList().forEach((user) -> {
                    oldDetailList.add(user.getName());
                });
                oldDetail = StringUtils.join(oldDetailList, ',');
            }

            String newDetail = "";
            List<String> newUsers = new ArrayList<String>();
            if (params.getDeveloperIds() != null && params.getDeveloperIds().size() >0) {
                this.userMapper.selectBatchIds(params.getDeveloperIds()).forEach((k) -> {
                    newUsers.add(k.getName());
                });
            }
            newDetail = StringUtils.join(newUsers, ',');

            DemandLog log = new DemandLog();
            log.setCreatorId(params.getCreator());
            log.setType(DemandLogTypeEnum.UPDATE.getType());
            log.setDemandId(demandInfo.getId());
            log.setProperty(DemandLogPropertyEnum.developer.getProperty());
            log.setOldDetail(oldDetail);
            log.setNewDetail(newDetail);
            logList.add(log);
        }

        if (params.getDevopsIds() != null && this.userIdListIsChanged(params.getDevopsIds(), demandInfo.getDevopsList())) {
            String oldDetail = null;
            if (demandInfo.getDevopsList() != null && demandInfo.getDevopsList().size() > 0) {
                List<String> oldDetailList = new ArrayList<String>();
                demandInfo.getDevopsList().forEach((user) -> {
                    oldDetailList.add(user.getName());
                });
                oldDetail = StringUtils.join(oldDetailList, ',');
            }

            String newDetail = "";
            List<String> newUsers = new ArrayList<String>();
            if (params.getDevopsIds() != null && params.getDevopsIds().size() >0) {
                this.userMapper.selectBatchIds(params.getDevopsIds()).forEach((k) -> {
                    newUsers.add(k.getName());
                });
            }
            newDetail = StringUtils.join(newUsers, ',');

            DemandLog log = new DemandLog();
            log.setCreatorId(params.getCreator());
            log.setType(DemandLogTypeEnum.UPDATE.getType());
            log.setDemandId(demandInfo.getId());
            log.setProperty(DemandLogPropertyEnum.devops.getProperty());
            log.setOldDetail(oldDetail);
            log.setNewDetail(newDetail);
            logList.add(log);
        }

        if (params.getProposerIds() != null && this.userIdListIsChanged(params.getProposerIds(), demandInfo.getProposerList())) {
            String oldDetail = null;
            if (demandInfo.getProposerList() != null && demandInfo.getProposerList().size() > 0) {
                List<String> oldDetailList = new ArrayList<String>();
                demandInfo.getProposerList().forEach((user) -> {
                    oldDetailList.add(user.getName());
                });
                oldDetail = StringUtils.join(oldDetailList, ',');
            }

            String newDetail = "";
            List<String> newUsers = new ArrayList<String>();
            if (params.getProposerIds() != null && params.getProposerIds().size() >0) {
                this.userMapper.selectBatchIds(params.getProposerIds()).forEach((k) -> {
                    newUsers.add(k.getName());
                });
            }
            newDetail = StringUtils.join(newUsers, ',');

            DemandLog log = new DemandLog();
            log.setCreatorId(params.getCreator());
            log.setType(DemandLogTypeEnum.UPDATE.getType());
            log.setDemandId(demandInfo.getId());
            log.setProperty(DemandLogPropertyEnum.proposer.getProperty());
            log.setOldDetail(oldDetail);
            log.setNewDetail(newDetail);
            logList.add(log);
        }
        if (params.getIsPending() != null && !params.getIsPending().equals(demandInfo.getIsPending())) {
            logList.add(this.buildCommonUpdate(DemandLogPropertyEnum.isPending.getProperty(), params.getCreator(), demandInfo.getId(), demandInfo.getIsPending().equals("2") ? "待审核" : "已审核", params.getIsPending().equals("2") ? "待审核" : "已审核"));
        }

        if (params.getName() != null && !params.getName().equals(demandInfo.getName())) {
            logList.add(this.buildCommonUpdate(DemandLogPropertyEnum.name.getProperty(), params.getCreator(), demandInfo.getId(), demandInfo.getName(), params.getName()));
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (params.getExpectDate() != null && !params.getExpectDate().equals(demandInfo.getExpectDate())) {
            String oldDate = demandInfo.getExpectDate() !=null ? formatter.format(demandInfo.getExpectDate()) : null;
            logList.add(this.buildCommonUpdate(DemandLogPropertyEnum.expectDate.getProperty(), params.getCreator(), demandInfo.getId(), oldDate, formatter.format(params.getExpectDate())));
        }
        if (params.getDemandType() != null && !params.getDemandType().equals(demandInfo.getDemandTypeId())) {
            logList.add(this.buildCommonUpdate(DemandLogPropertyEnum.demandType.getProperty(), params.getCreator(), demandInfo.getId(), demandInfo.getDemandType().getName(), newDemand.getDemandType().getName()));
        }
        if (params.getDemandStatus() != null && !params.getDemandStatus().equals(demandInfo.getDemandStatusId())) {
            logList.add(this.buildCommonUpdate(DemandLogPropertyEnum.demandStatus.getProperty(), params.getCreator(), demandInfo.getId(), demandInfo.getDemandStatus().getName(), newDemand.getDemandStatus().getName()));
        }
        if (params.getManDay() != null && !params.getManDay().equals(demandInfo.getManDay())) {
            logList.add(this.buildCommonUpdate(DemandLogPropertyEnum.manDay.getProperty(), params.getCreator(), demandInfo.getId(), demandInfo.getManDay(), newDemand.getManDay()));
        }
        if (params.getScheduleStartDate() != null && !params.getScheduleStartDate().equals(demandInfo.getScheduleStartDate())) {
            String oldDate = demandInfo.getScheduleStartDate() !=null ? formatter.format(demandInfo.getScheduleStartDate()) : null;
            logList.add(this.buildCommonUpdate(DemandLogPropertyEnum.scheduleStartDate.getProperty(), params.getCreator(), demandInfo.getId(), oldDate, formatter.format(params.getScheduleStartDate())));
        }
        if (params.getScheduleEndDate() != null && !params.getScheduleEndDate().equals(demandInfo.getScheduleEndDate())) {
            String oldDate = demandInfo.getScheduleEndDate() !=null ? formatter.format(demandInfo.getScheduleEndDate()) : null;
            logList.add(this.buildCommonUpdate(DemandLogPropertyEnum.scheduleEndDate.getProperty(), params.getCreator(), demandInfo.getId(), oldDate, formatter.format(params.getScheduleEndDate())));
        }
        if (params.getDetail() != null && !params.getDetail().equals(demandInfo.getDetail())) {
            logList.add(this.buildCommonUpdate(DemandLogPropertyEnum.detail.getProperty(), params.getCreator(), demandInfo.getId(), demandInfo.getDetail(), newDemand.getDetail()));
        }
        if (params.getComment() != null && !params.getComment().equals(demandInfo.getComment())) {
            logList.add(this.buildCommonUpdate(DemandLogPropertyEnum.comment.getProperty(), params.getCreator(), demandInfo.getId(), demandInfo.getComment(), newDemand.getComment()));
        }
        if (params.getUrl() != null && !params.getUrl().equals(demandInfo.getUrl())) {
            logList.add(this.buildCommonUpdate(DemandLogPropertyEnum.url.getProperty(), params.getCreator(), demandInfo.getId(), demandInfo.getUrl(), newDemand.getUrl()));
        }
        if (params.getIsOn() != null && !params.getIsOn().equals("1")) {
            DemandLog log = new DemandLog();
            log.setCreatorId(params.getCreator());
            log.setType(DemandLogTypeEnum.ONLOWER.getType());
            log.setDemandId(demandInfo.getId());
            log.setProperty(DemandLogPropertyEnum.demand.getProperty());
            logList.add(log);
        }
        if (params.getIsOn() != null && !params.getIsOn().equals("2")) {
            DemandLog log = new DemandLog();
            log.setCreatorId(params.getCreator());
            log.setType(DemandLogTypeEnum.ONUPPER.getType());
            log.setDemandId(demandInfo.getId());
            log.setProperty(DemandLogPropertyEnum.demand.getProperty());
            logList.add(log);
        }
        if (logList.size() > 0) this.logService.saveBatch(logList);
    }

    private boolean userIdListIsChanged(List<String> newList, List<User> userList)  {
        List<String> newUserList = newList != null ? newList : new ArrayList<String>();
        List<String> userIdList = new ArrayList<String>();
        if (userList != null && userList.size() > 0) {
            userList.forEach((user) -> {
                userIdList.add(user.getId());
            });
        }
        return !newUserList.containsAll(userIdList) || !userIdList.containsAll(newUserList);
    }

    private DemandLog buildCommonUpdate(String property, String creatorId, String damandId, String oldDetail, String newDetail) {
        DemandLog log = new DemandLog();
        log.setCreatorId(creatorId);
        log.setType(DemandLogTypeEnum.UPDATE.getType());
        log.setDemandId(damandId);
        log.setProperty(property);
        log.setOldDetail(oldDetail);
        log.setNewDetail(newDetail);
        return log;
    }
}
