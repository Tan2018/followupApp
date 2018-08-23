package com.ry.fu.net.common.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 在Filter中将ServletRequest替换为ServletRequestWrapper
 */
@Component
@ServletComponentScan
@WebFilter(filterName = "httpServletRequestReplacedFilter", urlPatterns = {"/followupApp/v1/*"})
public class HttpServletRequestReplacedFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(HttpServletRequestReplacedFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if(request instanceof HttpServletRequest) {
            requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            if(null == requestWrapper) {
                chain.doFilter(request, response);
            } else {
                chain.doFilter(requestWrapper, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

}
