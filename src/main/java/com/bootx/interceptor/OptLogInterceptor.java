package com.bootx.interceptor;

import com.bootx.audit.Audit;
import com.bootx.entity.Admin;
import com.bootx.entity.OptLog;
import com.bootx.service.AdminService;
import com.bootx.service.OptLogService;
import com.bootx.util.IPUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class OptLogInterceptor implements HandlerInterceptor {

    @Resource
    private AdminService adminService;

    @Resource
    private OptLogService optLogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Audit audit = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Audit.class);
            if(audit!=null){
                String ipAddr = IPUtils.getIpAddr(request);
                Admin admin = adminService.getCurrent();
                OptLog optLog = new OptLog();
                // 请求ip
                optLog.setIp(ipAddr);
                // 操作人
                optLog.setAdmin(admin);
                // 接口路径
                optLog.setRequestUrl(request.getRequestURI());
                // 请求参数
                optLog.setParameters(request.getParameterMap());
                // 操作名称
                optLog.setAction(audit.action());
                request.setAttribute(OptLog.AUDIT_LOG_ATTRIBUTE_NAME, optLog);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Audit audit = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Audit.class);
            if(audit!=null){
                OptLog optLog = (OptLog) request.getAttribute(OptLog.AUDIT_LOG_ATTRIBUTE_NAME);
                if(optLog!=null){
                    optLogService.save(optLog);
                }
            }
        }
    }
}
