 package com.bootx.audit;

 import com.bootx.entity.OptLog;
 import org.springframework.core.MethodParameter;
 import org.springframework.core.annotation.AnnotationUtils;
 import org.springframework.web.bind.support.WebDataBinderFactory;
 import org.springframework.web.context.request.NativeWebRequest;
 import org.springframework.web.context.request.RequestAttributes;
 import org.springframework.web.method.support.HandlerMethodArgumentResolver;
 import org.springframework.web.method.support.ModelAndViewContainer;

 /**
  * @author blackboy1987
  */
 public class OptLogHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

     @Override
     public boolean supportsParameter(MethodParameter methodParameter) {
         return OptLog.class.isAssignableFrom(methodParameter.getParameterType());
     }

     @Override
     public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
         return webRequest.getAttribute(OptLog.AUDIT_LOG_ATTRIBUTE_NAME, RequestAttributes.SCOPE_REQUEST);
     }
 }
