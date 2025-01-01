package com.bootx.controller;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.Admin;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Department;
import com.bootx.entity.Role;
import com.bootx.service.AdminService;
import com.bootx.service.DepartmentService;
import com.bootx.service.RoleService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @author blackboy1987
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private RoleService roleService;


    @PostMapping("/list")
    @JsonView({BaseEntity.PageView.class})
    public Result list(Pageable pageable,String username, Date beginDate, Date endDate,Long departmentId) {
        return Result.success(adminService.findList(pageable,username,beginDate,endDate,departmentService.findById(departmentId)));
    }

    @PostMapping("/save")
    public Result save(Admin admin,Long departmentId,Long[] roleIds) {
        List<Role> roleList = roleService.findListByIds(roleIds);
        if(roleList.isEmpty()){
            return Result.error("请设置角色");
        }
        Department department = departmentService.findById(departmentId);
        if(department==null){
            return Result.error("部门不存在");
        }
        admin.setDepartment(department);
        admin.setRoles(new HashSet<>(roleList));
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
    public Result update(Admin admin,Long departmentId,Long[] roleIds) {
        List<Role> roleList = roleService.findListByIds(roleIds);
        if(roleList.isEmpty()){
            return Result.error("请设置角色");
        }
        Department department = departmentService.findById(departmentId);
        if(department==null){
            return Result.error("部门不存在");
        }
        if(admin.getId()==null){
            return Result.error("用户不存在");
        }
        Admin byId = adminService.findById(admin.getId());
        if(byId==null){
            return Result.error("用户不存在");
        }
        byId.setDepartment(department);
        byId.setRoles(new HashSet<>(roleList));
        adminService.save(byId);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result delete(Long id) {
        adminService.delete(id);
        return Result.success();
    }
}
