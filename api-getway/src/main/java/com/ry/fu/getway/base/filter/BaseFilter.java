package com.ry.fu.getway.base.filter;

import com.netflix.zuul.ZuulFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/11/22 19:23
 * @description 基础拦截器，主要做路由转发、Token校验等
 */
public class BaseFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(BaseFilter.class);

    /**
     *
     pre：可以在请求被路由之前调用
     route：在路由请求时候被调用
     post：在route和error过滤器之后被调用
     error：处理请求时发生错误时被调用
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 拦截器优先级
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否需要拦截，此处设置为true，将会拦截
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() {
        return null;
    }
}
