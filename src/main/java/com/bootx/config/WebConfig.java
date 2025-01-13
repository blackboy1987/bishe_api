package com.bootx.config;

import com.bootx.audit.OptLogHandlerMethodArgumentResolver;
import com.bootx.interceptor.CorsInterceptor;
import com.bootx.interceptor.OptLogInterceptor;
import com.bootx.interceptor.SingleLoginInterceptor;
import com.bootx.security.AdminHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author blackboy1987
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }

    @Bean
    public OptLogInterceptor optLogInterceptor() {
        return new OptLogInterceptor();
    }
    @Bean
    public SingleLoginInterceptor singleLoginInterceptor() {
        return new SingleLoginInterceptor();
    }
    @Bean
    public CorsInterceptor corsInterceptor() {
        return new CorsInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsInterceptor())
                .addPathPatterns("/**");
        registry.addInterceptor(singleLoginInterceptor()).addPathPatterns("/api/**").excludePathPatterns("/api/login");
        registry.addInterceptor(optLogInterceptor()).addPathPatterns("/api/**");
    }

    @Bean
    public AdminHandlerMethodArgumentResolver adminHandlerMethodArgumentResolver() {
        return new AdminHandlerMethodArgumentResolver();
    }

    @Bean
    public OptLogHandlerMethodArgumentResolver optLogHandlerMethodArgumentResolver() {
        return new OptLogHandlerMethodArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(adminHandlerMethodArgumentResolver());
        resolvers.add(optLogHandlerMethodArgumentResolver());
    }
}
