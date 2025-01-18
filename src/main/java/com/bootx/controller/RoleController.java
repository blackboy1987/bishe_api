package com.bootx.controller;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.*;
import com.bootx.security.CurrentUser;
import com.bootx.service.DepartmentService;
import com.bootx.service.PermissionService;
import com.bootx.service.RoleService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.constructor.BaseConstructor;

import java.util.*;
import java.util.stream.Collectors;

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

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private PermissionService permissionService;

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

    @PostMapping("/permission")
    public Result permission(@CurrentUser Admin admin,Long roleId) {
        Role role = roleService.find(roleId);
        Map<String,Object> data = new HashMap<>();
        if(role==null){
            return Result.error("角色不存在");
        }
        Set<Permission> permissions = role.getPermissions();
        List<Map<String, Object>> menuList = jdbcTemplate.queryForList("select id,name from menu order by orders asc ");
        menuList.forEach(menu -> {
            menu.put("permissions", jdbcTemplate.queryForList("select CONCAT(menu_id,'_',id) `id` ,name from permission where menu_id=? ", menu.get("id")));
        });
        data.put("menuList", menuList);
        data.put("selectedPermissionIds", jdbcTemplate.queryForList("select CONCAT(menu_id,'_',id) id from permission where id in (select permissions_id permissionId from role_permissions where roles_id=?)",roleId).stream().map(item->item.get("id")).collect(Collectors.toList()));
        return Result.success(data);
    }

    @PostMapping("/savePermission")
    public Result savePermission(@CurrentUser Admin admin,String[] permissionIds,Long roleId) {

        Role role = roleService.find(roleId);
        if(role==null){
            return Result.error("角色不存在");
        }
        // 权限的id：菜单_权限id
        List<Long> permissionIdList = Arrays.stream(permissionIds).filter(item -> StringUtils.indexOf(item, '_') != -1).toList().stream().map(item-> Long.valueOf(item.split("_")[1])).toList();
        role.setPermissions(new HashSet<>(permissionService.findByIds(permissionIdList)));
        roleService.update(role);
        return Result.success();
    }
}
