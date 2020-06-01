package com.demand.management.service;

import com.demand.management.dto.demand.RelationDemand;
import com.demand.management.dto.user.RelationUser;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;

public interface ExcelService {
    HSSFSheet buildSheet(String[] title);
    HSSFWorkbook buildDemandExcel(List<RelationDemand> list);
    HSSFWorkbook buildUserExcel(List<RelationUser> list);
}
