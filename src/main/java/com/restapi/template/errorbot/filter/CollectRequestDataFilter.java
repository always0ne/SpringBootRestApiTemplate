package com.restapi.template.errorbot.filter;

import static com.restapi.template.errorbot.util.AgentUtils.getAgentDetail;
import static com.restapi.template.errorbot.util.MDCUtil.AGENT_DETAIL_MDC;
import static com.restapi.template.errorbot.util.MDCUtil.BODY_MDC;
import static com.restapi.template.errorbot.util.MDCUtil.HEADER_MAP_MDC;
import static com.restapi.template.errorbot.util.MDCUtil.PARAMETER_MAP_MDC;
import static com.restapi.template.errorbot.util.MDCUtil.REQUEST_URI_MDC;
import static com.restapi.template.errorbot.util.MDCUtil.clear;
import static com.restapi.template.errorbot.util.MDCUtil.putMDC;
import static com.restapi.template.errorbot.util.MDCUtil.setJsonValueAndPutMDC;

import com.restapi.template.errorbot.util.RequestWrapper;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Request 정보들을 수집하여 MDC에 보관하는 필터.
 *
 * @author always0ne
 * @version 1.0
 */
public class CollectRequestDataFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    RequestWrapper requestWrapper = RequestWrapper.of(request);

    setJsonValueAndPutMDC(HEADER_MAP_MDC, requestWrapper.headerMap());
    setJsonValueAndPutMDC(PARAMETER_MAP_MDC, requestWrapper.parameterMap());
    setJsonValueAndPutMDC(BODY_MDC, requestWrapper.body());
    setJsonValueAndPutMDC(AGENT_DETAIL_MDC, getAgentDetail((HttpServletRequest) request));
    putMDC(REQUEST_URI_MDC, requestWrapper.getRequestUri());

    try {
      chain.doFilter(request, response);
    } finally {
      clear();
    }
  }
}