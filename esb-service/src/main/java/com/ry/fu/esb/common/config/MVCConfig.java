package com.ry.fu.esb.common.config;

import com.ry.fu.esb.token.interceptor.TokenAuthInterceptor;
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
public class MVCConfig extends WebMvcConfigurerAdapter {

    /**
     * 多个拦截器组成一个拦截器链
     * addPathPatterns 用于添加拦截规则
     * excludePathPatterns 用户排除拦截
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
     /*   //配置拦截器
        registry.addInterceptor(getTokenAuthInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("*.html")
                .excludePathPatterns("/followupApp/v1/medicalPatron/medicalFile/upload")    //随医拍，需要上传，排除
                .excludePathPatterns("/followupApp/v1/docBook/ShiftWorkerInfo/upload")//添加交接班日志
                .excludePathPatterns("/followupApp/v1/medicalJournal/notify/**")    //收银台，需要通知
                .excludePathPatterns("/followupApp/v1/medicalJournal/medNotify/**") //医保支付，需要提供医程通异步通知
                .excludePathPatterns("/followupApp/v1/docBook/Departments/addDepartmentsLog")   //添加交接班值班日志
                .excludePathPatterns("/followupApp/v1/jpush/dm/**")    //危机值，需要供平台调用，所以排除
                .excludePathPatterns("/followupApp/v1/instantmessaging/netEaseCloudLetter/**")  //网易云通讯，排除掉，允许网易云请求过来
                .excludePathPatterns("/followupApp/v1/medicalJournal/op/show/**");    //获取图片,get请求*/
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
