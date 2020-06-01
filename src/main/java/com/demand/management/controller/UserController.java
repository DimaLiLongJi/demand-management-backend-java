package com.demand.management.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demand.management.dto.response.BaseResponse;
import com.demand.management.dto.response.DataResponse;
import com.demand.management.dto.response.PageDataResponse;
import com.demand.management.dto.user.UserBodyReq;
import com.demand.management.dto.user.RelationUser;
import com.demand.management.entity.User;
import com.demand.management.service.ExcelService;
import com.demand.management.service.UserService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService service;
    @Autowired
    ExcelService excelService;

    @GetMapping("/all")
    public DataResponse<List<RelationUser>> getAll() {
        Page<RelationUser> userList = this.service.findAll(null, null, null, null);
        return new DataResponse<List<RelationUser>>("获取全部用户成功", true, userList.getRecords());
    }

    @GetMapping("/find")
    public PageDataResponse find(
            @RequestParam(required = false) String pageIndex,
            @RequestParam(required = false) String pageSize,
            @RequestParam(required = false) String isOn,
            @RequestParam(required = false) String keyword
    ) {
        Page<RelationUser> userList = this.service.findAll(pageIndex, pageSize, isOn, keyword);
        return new PageDataResponse("获取用户列表成功", true, userList.getRecords(), userList.getTotal());
    }

    @GetMapping("/download")
    public void download(
            @RequestParam(required = false) String isOn,
            @RequestParam(required = false) String keyword,
            HttpServletResponse response
    ) throws IOException {
        Page<RelationUser> list = this.service.findAll(null, null, isOn, keyword);
        HSSFWorkbook workbook = this.excelService.buildUserExcel(list.getRecords());

        SimpleDateFormat format = new SimpleDateFormat("YYYY年MM月dd日HH时mm分ss秒");
        // TODO 文件名需要用URLEncoder.encode
        String fileName = URLEncoder.encode("用户列表" + format.format(new Date()) + ".xlsx", "UTF-8");

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
    public DataResponse<RelationUser> getById(@PathVariable String id) {
        RelationUser user = this.service.findById(id);
        if (user != null) return new DataResponse<RelationUser>("获取id为" +id + "的用户成功", true, user);
        else return new DataResponse<RelationUser>("id为" +id + "的用户不存在", false);
    }

    @Transactional
    @PostMapping("")
    public DataResponse<RelationUser> create(@RequestBody UserBodyReq userBody, HttpServletRequest request) {
        userBody.setCreator(request.getAttribute("authId").toString());
        RelationUser user = this.service.create(userBody);
        if (user != null) return new DataResponse<RelationUser>("创建用户成功", true, user);
        else return new DataResponse<RelationUser>("创建用户失败", false);
    }

    @Transactional
    @PutMapping("/{id}/password")
    public BaseResponse updatePassword(@PathVariable String id, @RequestBody() UserBodyReq userBody) {
        boolean hasUpdatePassword = this.service.updatePassword(id, userBody.getPassword());
        if (hasUpdatePassword) return new BaseResponse("id 为 " + id + "的用户更新密码成功", true);
        return new BaseResponse("id 为 " + id + "的用户更新密码失败", false);
    }

    @Transactional
    @PutMapping("/{id}")
    public DataResponse<RelationUser> update(@PathVariable String id, @RequestBody UserBodyReq userBody) {
        if (!this.service.updateById(id, userBody)) return new DataResponse<RelationUser>("更新id为" +id + "的用户失败", false);
        RelationUser user = this.service.findById(id);
        if (user != null) return new DataResponse<RelationUser>("更新id为" +id + "的用户成功", true, user);
        else return new DataResponse<RelationUser>("id为" +id + "的用户不存在", false);
    }
}