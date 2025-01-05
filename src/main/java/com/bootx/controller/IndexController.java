package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.util.JWTUtils;
import io.jsonwebtoken.Claims;
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
    public Result currentUser(HttpServletRequest request){
        String token = request.getHeader("token");

        Claims claims = JWTUtils.parseToken(token);
        HashMap<Object, Object> data = new HashMap<>();
        data.put("username","1");
        data.put("id",1);
        return Result.success(data);
    }
}
