package com.bootx.controller;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Department;
import com.bootx.entity.Role;
import com.bootx.service.DepartmentService;
import com.bootx.service.RoleService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author blackboy1987
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private DepartmentService departmentService;

    @PostMapping("/list")
    @JsonView(BaseEntity.PageView.class)
    public Result list(Pageable pageable, String name, String memo, Date beginDate , Date endDate,Long departmentId) {
        return Result.success(roleService.findPage(pageable,name,memo,beginDate,endDate,departmentService.findById(departmentId)));
    }

    @PostMapping("/save")
    public Result save(Role role,Long departmentId) {
        Department department = departmentService.findById(departmentId);
        if(department==null){
            return Result.error("部门不存在");
        }
        role.setDepartment(department);
        if(StringUtils.isBlank(role.getName())) {
            return Result.error("请输入角色名称");
        }
        roleService.save(role);
        return Result.success();
    }

    @PostMapping("/update")
    public Result update(Role role,Long departmentId) {
        Department department = departmentService.findById(departmentId);
        if(department==null){
            return Result.error("部门不存在");
        }
        if(StringUtils.isBlank(role.getName())) {
            return Result.error("请输入角色名称");
        }
        if(role.getId() == null) {
            return Result.error("角色不存在");
        }
        Role pRole = roleService.find(role.getId());
        pRole.setName(role.getName());
        pRole.setMemo(role.getMemo());
        pRole.setDepartment(department);
        roleService.update(pRole);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result delete(Long[] ids) {
        roleService.delete(ids);
        return Result.success();
    }
}
