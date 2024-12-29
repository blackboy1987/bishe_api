package com.bootx.config;

import com.bootx.interceptor.CorsInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author blackboy1987
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                // 所有的请求都允许跨域
                .addMapping("/**")
                // 允许所有的域
                .allowedOrigins("*")
                // 允许所有的方法
                .allowedMethods("*")
                // 允许所有的请求头
                .allowedHeaders("*");
    }*/

    @Bean
    public CorsInterceptor corsInterceptor(){
        return new CorsInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsInterceptor()).addPathPatterns("/**");
    }
}
