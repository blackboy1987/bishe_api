package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Department;
import com.bootx.service.DepartmentService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @author blackboy1987
 */
@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;


    @PostMapping("/list")
    @JsonView({BaseEntity.PageView.class})
    public Result list() {
        return Result.success(departmentService.findList(null));
    }

    @PostMapping("/save")
    public Result save(Department department,Long parentId) {
        if(parentId!=null){
            department.setParent(departmentService.findById(parentId));
        }
        if(department.getName()==null){
            return Result.error("部门名不能为空");
        }
        departmentService.save(department);
        return Result.success();
    }

    @PostMapping("/view")
    public Result view(Long id) {
        Department department = departmentService.findById(id);
        if(department==null){
            return Result.error("用户不存在");
        }
        return Result.success(department);
    }

    @PostMapping("/update")
    public Result update(Department department,Long parentId) {
        if(parentId!=null){
            department.setParent(departmentService.findById(parentId));
        }
        if(Objects.equals(parentId, department.getId())){
            return Result.error("参数错误");
        }
        if(department.getId()==null){
            return Result.error("部门不存在");
        }
        Department byId = departmentService.findById(department.getId());
        Department parent = department.getParent();
        if(parent!=null){
            List<Department> parents = parent.getParents();
            if(parents.contains(byId)){
                return Result.error("参数错误");
            }
        }
        if(byId==null){
            return Result.error("部门不存在");
        }
        byId.setName(department.getName());
        byId.setParent(department.getParent());
        byId.setParent(department.getParent());
        departmentService.save(byId);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result delete(Long id) {
        Department byId = departmentService.findById(id);
        if(!byId.getChildren().isEmpty()){
            return Result.error("存在下级部门，无法删除");
        }
        departmentService.delete(byId);
        return Result.success();
    }


    @PostMapping("/tree")
    public Result tree() {
        return Result.success(departmentService.tree());
    }
}
