package com.demand.management.impl;

import com.demand.management.dto.demand.RelationDemand;
import com.demand.management.dto.user.RelationUser;
import com.demand.management.service.ExcelService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {
    private final String[] demandTitle = new String[]{
            "id",
            "需求名",
            "创建人",
            "线上链接",
            "需求类型",
            "需求状态",
            "开发人天",
            "需求提出人",
            "需求对接人",
            "开发者",
            "运维或其他人员",
            "期望完成时间",
            "排期开始时间",
            "排期结束时间",
            "需求创建时间",
            "需求结束时间",
            "是否已归档",
            "需求详情",
            "审批备注"
    };

    private final String[] userTitle = new String[]{
            "id",
            "角色名",
            "手机号",
            "邮箱",
            "角色",
            "权限",
            "创建者",
            "用户创建时间",
            "是否已归档",
    };

    @Override
    public HSSFSheet buildSheet(String[] title) {
        //声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        //生成一个表格
        HSSFSheet sheet = workbook.createSheet();
//        headers表示excel表中第一行的表头 在excel表中添加表头
        HSSFRow row = sheet.createRow(0);
        for(int i=0;i<title.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(title[i]);
            cell.setCellValue(text);
        }
//        可直接获得工作蒲
//        HSSFWorkbook workbook = sheet.getWorkbook();
        return sheet;
    }

    @Override
    public HSSFWorkbook buildDemandExcel(List<RelationDemand> list) {
        HSSFSheet sheet = this.buildSheet(this.demandTitle);
        HSSFWorkbook workbook = sheet.getWorkbook();
        // 新增数据行，并且设置单元格数据
        int rowNum = 1;

        SimpleDateFormat format = new SimpleDateFormat("YYYY年MM月dd日HH:mm:ss");

        //在表中存放查询到的数据放入对应的列
        for (RelationDemand item : list) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(item.getId());
            row1.createCell(1).setCellValue(item.getName());
            row1.createCell(2).setCellValue(item.getCreator().getName());
            row1.createCell(3).setCellValue(item.getUrl());
            row1.createCell(4).setCellValue(item.getDemandType().getName());
            row1.createCell(5).setCellValue(item.getDemandStatus().getName());
            row1.createCell(6).setCellValue(item.getManDay());
            StringBuilder proposerList = new StringBuilder();
            item.getProposerList().forEach((user) -> {
                proposerList.append(user.getName());
                proposerList.append(",");
            });
            row1.createCell(7).setCellValue(proposerList.toString());
            StringBuilder brokerList = new StringBuilder();
            item.getBrokerList().forEach((user) -> {
                brokerList.append(user.getName());
                brokerList.append(",");
            });
            row1.createCell(8).setCellValue(brokerList.toString());
            StringBuilder developerList = new StringBuilder();
            item.getDeveloperList().forEach((user) -> {
                developerList.append(user.getName());
                developerList.append(",");
            });
            row1.createCell(9).setCellValue(developerList.toString());
            StringBuilder devopsList = new StringBuilder();
            item.getDevopsList().forEach((user) -> {
                devopsList.append(user.getName());
                devopsList.append(",");
            });
            row1.createCell(10).setCellValue(devopsList.toString());
            row1.createCell(11).setCellValue(item.getExpectDate() != null ? format.format(item.getExpectDate()) : "");
            row1.createCell(12).setCellValue(item.getScheduleStartDate() != null ? format.format(item.getScheduleStartDate()) : "");
            row1.createCell(13).setCellValue(item.getScheduleEndDate() != null ? format.format(item.getScheduleEndDate()) : "");
            row1.createCell(14).setCellValue(item.getCreateDate() != null ? format.format(item.getCreateDate()) : "");
            row1.createCell(15).setCellValue(item.getFinishDate() != null ? format.format(item.getFinishDate()) : "");
            row1.createCell(16).setCellValue(item.getDeleteDate() != null ? "已归档" : "未归档");
            row1.createCell(17).setCellValue(item.getDetail());
            row1.createCell(18).setCellValue(item.getComment());

            rowNum++;
        }

        return workbook;
    }

    @Override
    public HSSFWorkbook buildUserExcel(List<RelationUser> list) {
        HSSFSheet sheet = this.buildSheet(this.userTitle);
        HSSFWorkbook workbook = sheet.getWorkbook();
        // 新增数据行，并且设置单元格数据
        int rowNum = 1;

        SimpleDateFormat format = new SimpleDateFormat("YYYY年MM月dd日HH:mm:ss");

        //在表中存放查询到的数据放入对应的列
        for (RelationUser item : list) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(item.getId());
            row1.createCell(1).setCellValue(item.getName());
            row1.createCell(2).setCellValue(item.getMobile());
            row1.createCell(3).setCellValue(item.getEmail());
            row1.createCell(4).setCellValue(item.getRole() != null ? item.getRole().getName() : "");
            StringBuilder permissionList = new StringBuilder();
            item.getPermissionList().forEach((p) -> {
                permissionList.append(p.getName());
                permissionList.append(",");
            });
            row1.createCell(5).setCellValue(permissionList.toString());
            row1.createCell(6).setCellValue(item.getCreator() != null ? item.getCreator().getName() : "");
            row1.createCell(7).setCellValue(item.getCreateDate() != null ? format.format(item.getCreateDate()) : "");
            row1.createCell(8).setCellValue(item.getDeleteDate() != null ? "已归档" : "未归档");

            rowNum++;
        }

        return workbook;
    }
}
