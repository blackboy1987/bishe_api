package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.entity.Admin;
import com.bootx.security.CurrentUser;
import com.bootx.service.AdminService;
import com.bootx.util.JWTUtils;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class IndexController {

    @PostMapping("/index")
    public String index(){
        return "index";
    }

    @PostMapping("/currentUser")
    public Result currentUser(@CurrentUser Admin admin){
        HashMap<Object, Object> data = new HashMap<>();
        data.put("username",admin.getUsername());
        return Result.success(data);
    }
}
