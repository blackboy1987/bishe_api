package com.bootx.interceptor;

import com.bootx.common.Result;
import com.bootx.entity.Admin;
import com.bootx.service.AdminService;
import com.bootx.util.JsonUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

public class SingleLoginInterceptor implements HandlerInterceptor {

    @Resource
    private AdminService adminService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        Admin current = adminService.getCurrent(request);
        // 当前登录账号不存在
        if(current==null){
            response.setContentType("application/json");
            response.setStatus(200);
            try {
                JsonUtils.writeValue(response.getWriter(), Result.error(-2,"请先登录"));
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }


            return false;
        }
        // 缓存里面的token
        String s = stringRedisTemplate.opsForValue().get("token:" + current.getId());
        if(!StringUtils.equalsAnyIgnoreCase(s, token)) {
            response.setContentType("application/json");
            response.setStatus(200);
            try {
                JsonUtils.writeValue(response.getWriter(), Result.error(-2,"请先登录"));
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            return false;
        }
        return true;
    }

}
