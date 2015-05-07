package com.likeyichu.webservice;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 *用于执行response.addHeader("Access-Control-Allow-Origin", "*");
 *以便跨域服务
 */
public class WebServiceFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String path = ((HttpServletRequest)request).getRequestURI();
       
        if (path != null && (path.contains("/webService/"))) {
             HttpServletResponse res = (HttpServletResponse) response;
             res.addHeader("Access-Control-Allow-Origin", "*");
             res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
             res.addHeader("Access-Control-Allow-Headers", "Content-Type");
             //对结果的过滤也要放在doFilter()前面
             chain.doFilter(request, response);
        } 
    }

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
