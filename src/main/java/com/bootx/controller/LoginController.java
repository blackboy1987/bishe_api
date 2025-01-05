package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.entity.Admin;
import com.bootx.service.AdminService;
import com.bootx.util.JWTUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Resource
    private AdminService adminService;

    @PostMapping
    public Result login(String username, String password,Boolean autoLogin,String type) {
        Admin admin = adminService.findByUsername(username);
        if(admin==null){
            return Result.error("账号不存在");
        }
        if(!StringUtils.equalsAnyIgnoreCase(admin.getPassword(),password)){
            return Result.error("账号不存在");
        }
        return Result.success(JWTUtils.create(admin.getId()+"",new HashMap<>()));
    }

}
