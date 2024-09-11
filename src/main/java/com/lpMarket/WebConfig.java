package com.lpMarket;

import com.lpMarket.interceptor.LogInterceptor;
import com.lpMarket.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**") // 보호할 URL 패턴
                .excludePathPatterns("/", "/members/new", "/login", "/logout", "/css/**", "/*.ico", "/error");  // 제외할 URL 패턴
    }


}
