package com.bootx.controller;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.Admin;
import com.bootx.entity.BaseEntity;
import com.bootx.service.AdminService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author blackboy1987
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Resource
    private AdminService adminService;


    @PostMapping("/list")
    @JsonView({BaseEntity.PageView.class})
    public Result list(Pageable pageable,String username, Date beginDate, Date endDate) {
        return Result.success(adminService.findList(pageable,username,beginDate,endDate));
    }

    @PostMapping("/save")
    public Result save(Admin admin) {
        // 查询数据库中，用户名是否存在
        if(adminService.existUsername(admin.getUsername())) {
            return Result.error("用户名已存在");
        }
        if(admin.getUsername()==null){
            return Result.error("用户名不能为空");
        }
        admin.setPassword("123456");
        adminService.save(admin);
        return Result.success();
    }

    @PostMapping("/view")
    public Result view(Long id) {
        Admin admin = adminService.findById(id);
        if(admin==null){
            return Result.error("用户不存在");
        }
        return Result.success(admin);
    }

    @PostMapping("/update")
    public Result update(Admin admin) {
        if(admin.getId()==null){
            return Result.error("用户不存在");
        }
        Admin byId = adminService.findById(admin.getId());
        if(byId==null){
            return Result.error("用户不存在");
        }
        adminService.save(byId);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result delete(Long id) {
        adminService.delete(id);
        return Result.success();
    }
}
