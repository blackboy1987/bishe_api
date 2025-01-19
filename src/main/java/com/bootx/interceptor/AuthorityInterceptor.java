package com.bootx.interceptor;

import com.bootx.common.Result;
import com.bootx.entity.Admin;
import com.bootx.service.AdminService;
import com.bootx.service.PermissionService;
import com.bootx.util.JsonUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Set;

/**
 * @author black
 */
public class AuthorityInterceptor implements HandlerInterceptor {

    @Resource
    private AdminService adminService;

    @Resource
    private PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println(request.getRequestURI());
        Admin current = adminService.getCurrent(request);
        response.setContentType("application/json");
        response.setStatus(200);
        response.setCharacterEncoding("utf-8");
        // 当前登录账号不存在
        if(current==null){
            try {
                JsonUtils.writeValue(response.getWriter(), Result.error(-2,"请先登录"));
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            return false;
        }
        Set<String> authority = permissionService.getAuthority(current);
        if(authority.contains(request.getRequestURI().replace("/api", ""))){
            return true;
        }
        try {
            JsonUtils.writeValue(response.getWriter(), Result.error("无接口权限："+request.getRequestURI()));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return false;
    }

}
