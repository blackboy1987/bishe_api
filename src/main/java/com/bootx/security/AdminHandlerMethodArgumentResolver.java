 package com.bootx.security;

import com.bootx.entity.Admin;
import com.bootx.service.AdminService;
import com.bootx.util.JWTUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author blackboy1987
 */
public class AdminHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Resource
    private AdminService adminService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType() == Admin.class && methodParameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader("token");
        String id = JWTUtils.getId(token);
        if(StringUtils.isBlank(id)){
            return null;
        }
        return adminService.findById(Long.parseLong(id));
    }
}
