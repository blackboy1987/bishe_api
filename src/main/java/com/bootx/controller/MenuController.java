package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Menu;
import com.bootx.service.MenuService;
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
@RequestMapping("/api/menu")
public class MenuController {

    @Resource
    private MenuService menuService;


    @PostMapping("/list")
    @JsonView({BaseEntity.PageView.class})
    public Result list() {
        return Result.success(menuService.findList(null));
    }

    @PostMapping("/save")
    public Result save(Menu menu,Long parentId) {
        if(parentId!=null){
            menu.setParent(menuService.findById(parentId));
        }
        if(menu.getName()==null){
            return Result.error("菜单名不能为空");
        }
        menuService.save(menu);
        return Result.success();
    }

    @PostMapping("/view")
    public Result view(Long id) {
        Menu menu = menuService.findById(id);
        if(menu==null){
            return Result.error("用户不存在");
        }
        return Result.success(menu);
    }

    @PostMapping("/update")
    public Result update(Menu menu,Long parentId) {
        if(parentId!=null){
            menu.setParent(menuService.findById(parentId));
        }
        if(Objects.equals(parentId, menu.getId())){
            return Result.error("参数错误");
        }
        if(menu.getId()==null){
            return Result.error("菜单不存在");
        }
        Menu byId = menuService.findById(menu.getId());
        Menu parent = menu.getParent();
        if(parent!=null){
            List<Menu> parents = parent.getParents();
            if(parents.contains(byId)){
                return Result.error("参数错误");
            }
        }
        if(byId==null){
            return Result.error("菜单不存在");
        }
        byId.setName(menu.getName());
        byId.setUrl(menu.getUrl());
        byId.setParent(menu.getParent());
        byId.setParent(menu.getParent());
        menuService.save(byId);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result delete(Long id) {
        Menu byId = menuService.findById(id);
        if(!byId.getChildren().isEmpty()){
            return Result.error("存在下级菜单，无法删除");
        }
        menuService.delete(byId);
        return Result.success();
    }


    @PostMapping("/tree")
    public Result tree() {
        return Result.success(menuService.tree());
    }
}
