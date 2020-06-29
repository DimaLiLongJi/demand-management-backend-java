package com.demand.management.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demand.management.dto.demand.DemandBodyReq;
import com.demand.management.dto.demand.DemandFilesBodyReq;
import com.demand.management.dto.demand.DemandPendingEnum;
import com.demand.management.dto.demand.RelationDemand;
import com.demand.management.dto.response.BaseResponse;
import com.demand.management.dto.response.DataResponse;
import com.demand.management.dto.response.PageDataResponse;
import com.demand.management.entity.File;
import com.demand.management.service.DemandService;
import com.demand.management.service.ExcelService;
import com.demand.management.utils.CommonUtil;
import com.demand.management.utils.ManagementResponseUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@RestController
@RequestMapping("/api/demand")
public class DemandController {
    @Autowired
    DemandService service;
    @Autowired
    ExcelService excelService;

    @PostMapping("/addFile")
    public DataResponse<File> addFile(
            @RequestParam(name = "file", required = false) MultipartFile file,
            @RequestParam(name = "demandId", required = false) String demandId,
            HttpServletRequest request
    ) {
        if (file.isEmpty()) return ManagementResponseUtil.<File>buildDataResponse("上传失败", false);
        File saveFile = this.service.addFile(file, request.getAttribute("authId").toString(), demandId);
        if (saveFile != null) return ManagementResponseUtil.<File>buildDataResponse("创建需求附件成功", true, saveFile);
        else return ManagementResponseUtil.<File>buildDataResponse("创建需求附件失败", false);
    }

    @PostMapping("")
    public DataResponse<RelationDemand> create(@RequestBody DemandBodyReq body, HttpServletRequest request) {
        body.setCreator(request.getAttribute("authId").toString());
        RelationDemand module = this.service.create(body);
        if (module != null) return ManagementResponseUtil.<RelationDemand>buildDataResponse("创建需求成功", true, module);
        else return ManagementResponseUtil.<RelationDemand>buildDataResponse("创建需求失败", false);
    }

    @GetMapping("/find")
    public PageDataResponse<RelationDemand> find(
            @RequestParam(required = false) String pageIndex,
            @RequestParam(required = false) String pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String isOn,
            @RequestParam(required = false) String creator,
            @RequestParam(required = false) String proposer,
            @RequestParam(required = false) String broker,
            @RequestParam(required = false) String devops,
            @RequestParam(required = false) String developer,
            @RequestParam(required = false) String demandType,
            @RequestParam(required = false) String demandStatus,
            @RequestParam(required = false) String timeout,
            @RequestParam(required = false) String deleteDate,
            @RequestParam(required = false) String demandCreateFromDate,
            @RequestParam(required = false) String demandCreateToDate,
            @RequestParam(required = false) String demandEndFromDate,
            @RequestParam(required = false) String demandEndToDate,
            @RequestParam(required = false) String scheduleStartFromDate,
            @RequestParam(required = false) String scheduleStartToDate,
            @RequestParam(required = false) String scheduleEndFromDate,
            @RequestParam(required = false) String scheduleEndToDate
    ) throws ParseException {
        Page<RelationDemand> list = this.service.findAll(
                pageIndex,
                pageSize,
                keyword,
                isOn,
                creator,
                proposer,
                broker,
                devops,
                developer,
                null,
                demandType,
                demandStatus,
                timeout,
                CommonUtil.buildDateParams(deleteDate),
                CommonUtil.buildDateParams(demandCreateFromDate),
                CommonUtil.buildDateParams(demandCreateToDate),
                CommonUtil.buildDateParams(demandEndFromDate),
                CommonUtil.buildDateParams(demandEndToDate),
                CommonUtil.buildDateParams(scheduleStartFromDate),
                CommonUtil.buildDateParams(scheduleStartToDate),
                CommonUtil.buildDateParams(scheduleEndFromDate),
                CommonUtil.buildDateParams(scheduleEndToDate)
        );
        return ManagementResponseUtil.<RelationDemand>buildPageDataResponse("获取需求列表成功", true, list.getRecords(), list.getTotal());
    }

    @GetMapping("/find-self")
    public PageDataResponse<RelationDemand> findSelf(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String pageIndex,
            @RequestParam(required = false) String pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String isOn,
            @RequestParam(required = false) String demandType,
            @RequestParam(required = false) String demandStatus,
            @RequestParam(required = false) String timeout,
            @RequestParam(required = false) String deleteDate,
            @RequestParam(required = false) String demandCreateFromDate,
            @RequestParam(required = false) String demandCreateToDate,
            @RequestParam(required = false) String demandEndFromDate,
            @RequestParam(required = false) String demandEndToDate,
            @RequestParam(required = false) String scheduleStartFromDate,
            @RequestParam(required = false) String scheduleStartToDate,
            @RequestParam(required = false) String scheduleEndFromDate,
            @RequestParam(required = false) String scheduleEndToDate,
            HttpServletRequest request
    ) throws ParseException {
        Page<RelationDemand> list = this.service.findSelfAll(
                request.getAttribute("authId").toString(),
                type,
                pageIndex,
                pageSize,
                keyword,
                isOn,
                demandType,
                demandStatus,
                timeout,
                CommonUtil.buildDateParams(deleteDate),
                CommonUtil.buildDateParams(demandCreateFromDate),
                CommonUtil.buildDateParams(demandCreateToDate),
                CommonUtil.buildDateParams(demandEndFromDate),
                CommonUtil.buildDateParams(demandEndToDate),
                CommonUtil.buildDateParams(scheduleStartFromDate),
                CommonUtil.buildDateParams(scheduleStartToDate),
                CommonUtil.buildDateParams(scheduleEndFromDate),
                CommonUtil.buildDateParams(scheduleEndToDate)
        );
        return ManagementResponseUtil.<RelationDemand>buildPageDataResponse("获取需求列表成功", true, list.getRecords(), list.getTotal());
    }

    @GetMapping("/download")
    public void download(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String isOn,
            @RequestParam(required = false) String creator,
            @RequestParam(required = false) String proposer,
            @RequestParam(required = false) String broker,
            @RequestParam(required = false) String devops,
            @RequestParam(required = false) String developer,
            @RequestParam(required = false) String demandType,
            @RequestParam(required = false) String demandStatus,
            @RequestParam(required = false) String timeout,
            // TODO query来的时间要转换
            @RequestParam(required = false) String deleteDate,
            @RequestParam(required = false) String demandCreateFromDate,
            @RequestParam(required = false) String demandCreateToDate,
            @RequestParam(required = false) String demandEndFromDate,
            @RequestParam(required = false) String demandEndToDate,
            @RequestParam(required = false) String scheduleStartFromDate,
            @RequestParam(required = false) String scheduleStartToDate,
            @RequestParam(required = false) String scheduleEndFromDate,
            @RequestParam(required = false) String scheduleEndToDate,
            HttpServletResponse response
    ) throws IOException, ParseException {
        Page<RelationDemand> list = this.service.findAll(
                null,
                null,
                keyword,
                isOn,
                creator,
                proposer,
                broker,
                devops,
                developer,
                null,
                demandType,
                demandStatus,
                timeout,
                // TODO query来的时间要转换
                CommonUtil.buildDateParams(deleteDate),
                CommonUtil.buildDateParams(demandCreateFromDate),
                CommonUtil.buildDateParams(demandCreateToDate),
                CommonUtil.buildDateParams(demandEndFromDate),
                CommonUtil.buildDateParams(demandEndToDate),
                CommonUtil.buildDateParams(scheduleStartFromDate),
                CommonUtil.buildDateParams(scheduleStartToDate),
                CommonUtil.buildDateParams(scheduleEndFromDate),
                CommonUtil.buildDateParams(scheduleEndToDate)
        );
        HSSFWorkbook workbook = this.excelService.buildDemandExcel(list.getRecords());

        SimpleDateFormat format = new SimpleDateFormat("YYYY年MM月dd日HH时mm分ss秒");
        // TODO 文件名需要用URLEncoder.encode
        String fileName = URLEncoder.encode("需求列表" + format.format(new Date()) + ".xlsx", "UTF-8");

        OutputStream out = response.getOutputStream();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.flushBuffer();
        workbook.write(out);
        workbook.close();
        out.close();
    }

    @GetMapping("/{id}")
    public DataResponse<RelationDemand> getById(@PathVariable String id) {
        RelationDemand find = this.service.findById(id);
        if (find != null) return ManagementResponseUtil.<RelationDemand>buildDataResponse("获取id为" +id + "的需求成功", true, find);
        else return ManagementResponseUtil.<RelationDemand>buildDataResponse("id为" +id + "的需求不存在", false);
    }

    @PutMapping("/pass/{id}")
    public BaseResponse pass(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        DemandBodyReq body = new DemandBodyReq();
        body.setCreator(request.getAttribute("authId").toString());
        body.setIsPending(DemandPendingEnum.notPending.getType());
        if (this.service.updateById(id, body)) return ManagementResponseUtil.buildBaseResponse("更新需求成功", true);
        else return ManagementResponseUtil.buildBaseResponse("更新需求失败", false);
    }

    @PutMapping("/deleteFile")
    public BaseResponse deleteFile(@RequestBody DemandFilesBodyReq body, HttpServletRequest request) {
        body.setCreator(request.getAttribute("authId").toString());
        if (this.service.deleteFile(body)) return ManagementResponseUtil.buildBaseResponse("删除需求附件成功", true);
        else return ManagementResponseUtil.buildBaseResponse("删除需求附件失败", false);
    }

    @PutMapping("/{id}")
    public BaseResponse update(
            @PathVariable String id,
            @RequestBody DemandBodyReq body,
            HttpServletRequest request
    ) {
        body.setCreator(request.getAttribute("authId").toString());
        if (this.service.updateById(id, body)) return ManagementResponseUtil.buildBaseResponse("更新需求成功", true);
        else return ManagementResponseUtil.buildBaseResponse("id 为 " + id + " 的需求无法审核，原因：id为 " + request.getAttribute("authId").toString() + " 的用户无审核权限！", false);
    }
}
