package com.restapi.template.errorbot.filter;

import com.restapi.template.errorbot.util.MultiReadableHttpServletRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
/**
 * Request를 여러번 읽을 수 있도록 복사하는 필터
 *
 * @author always0ne
 * @version 1.0
 */
public class MultiReadableHttpServletRequestFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        MultiReadableHttpServletRequest multiReadRequest = new MultiReadableHttpServletRequest((HttpServletRequest) req);
        chain.doFilter(multiReadRequest, res);
    }
}
