package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.entity.Admin;
import com.bootx.security.CurrentUser;
import com.bootx.service.AdminService;
import com.bootx.util.JWTUtils;
import com.bootx.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class IndexController {

    @Resource
    private JdbcTemplate jdbcTemplate;

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

    @PostMapping("/menus")
    public Result menus(@CurrentUser Admin admin){
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id,name,url `path`,component from menu order by orders asc ;");
        return Result.success(maps);
    }
}
