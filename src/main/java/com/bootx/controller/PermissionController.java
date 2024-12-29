package com.bootx.controller;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Menu;
import com.bootx.entity.Permission;
import com.bootx.service.MenuService;
import com.bootx.service.PermissionService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author blackboy1987
 */
@RestController
@RequestMapping("/api/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @Resource
    private MenuService menuService;


    @PostMapping("/list")
    @JsonView({BaseEntity.PageView.class})
    public Result list(Pageable pageable, Long menuId) {
        return Result.success(permissionService.findPage(pageable,menuService.findById(menuId)));
    }

    @PostMapping("/save")
    public Result save(Permission permission,Long menuId) {
        Menu menu = menuService.findById(menuId);
        if(menu==null){
            return Result.error("菜单不存在");
        }
        permission.setMenu(menu);
        if(permission.getName()==null){
            return Result.error("权限名不能为空");
        }
        permissionService.save(permission);
        return Result.success();
    }

    @PostMapping("/view")
    public Result view(Long id) {
        Permission permission = permissionService.findById(id);
        if(permission==null){
            return Result.error("用户不存在");
        }
        return Result.success(permission);
    }

    @PostMapping("/update")
    public Result update(Permission permission) {
        if(permission.getId()==null){
            return Result.error("菜单不存在");
        }
        Permission byId = permissionService.findById(permission.getId());
        if(byId==null){
            return Result.error("菜单不存在");
        }
        byId.setName(permission.getName());
        byId.setAction(permission.getAction());
        permissionService.save(byId);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result delete(Long id) {
        permissionService.delete(id);
        return Result.success();
    }




    @PostMapping("/menu")
    public Result menu() {
        return Result.success(menuService.tree());
    }
}
