package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.entity.Admin;
import com.bootx.entity.LoginLog;
import com.bootx.service.AdminService;
import com.bootx.service.LoginLogService;
import com.bootx.util.IPUtils;
import com.bootx.util.JWTUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author blackboy1987
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Resource
    private AdminService adminService;

    @Resource
    private LoginLogService loginLogService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping
    public Result login(HttpServletRequest request,String username, String password, Boolean autoLogin, String type) {
        LoginLog loginLog = new LoginLog();
        loginLog.setIp(IPUtils.getIpAddr(request));
        loginLog.setUserAgent(request.getHeader("User-Agent"));
        loginLog.setUa(request.getHeader("sec-ch-ua"));
        boolean flag = false;
        if(StringUtils.isNotBlank(username)){
            loginLog.setUsername(username);
            flag = true;
        }
        if(StringUtils.isNotBlank(password)){
            loginLog.setPassword(password);
            flag = true;
        }
        Admin admin = adminService.findByUsername(username);
        if(admin==null){
            loginLog.setResult("账号不存在");
            if(flag){
                loginLogService.save(loginLog);
            }
            return Result.error("账号不存在");
        }

        if(admin.getIsLocked()){
            return Result.error("账号已被锁定，请联系管理员");
        }


        if(!StringUtils.equalsAnyIgnoreCase(admin.getPassword(),password)){
            loginLog.setResult("用户名跟密码不匹配");
            adminService.lock(admin);
            if(flag){
                loginLogService.save(loginLog);
            }
            return Result.error("账号不存在");
        }



        loginLog.setResult("登录成功");
        if(flag){
            loginLogService.save(loginLog);
        }
        String token = JWTUtils.create(admin.getId() + "", new HashMap<>());
        stringRedisTemplate.opsForValue().set("token:"+admin.getId(),token);
        return Result.success(token);
    }

}
