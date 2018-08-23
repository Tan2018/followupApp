package com.ry.fu.followup.config;

import com.ry.fu.followup.properties.SystemProperties;
import com.ry.fu.followup.token.interceptor.TokenAuthInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/12/5 8:22
 * @description MVC配置
 */
@Configuration
@EnableConfigurationProperties(SystemProperties.class)
public class MVCConfig extends WebMvcConfigurerAdapter {

    /**
     * 多个拦截器组成一个拦截器链
     * addPathPatterns 用于添加拦截规则
     * excludePathPatterns 用户排除拦截
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //配置拦截器
        //registry.addInterceptor(getTokenAuthInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    /**
     * 开放跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
        super.addCorsMappings(registry);
    }

    /**
     * 解决同源策略问题的filter
     * @return
     */
    @Bean
    public Filter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    /**
     * 注入Bean，使Interceptor可以使用Bean
     * @return
     */
    @Bean
    public TokenAuthInterceptor getTokenAuthInterceptor() {
        return new TokenAuthInterceptor();
    }

}
