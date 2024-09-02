package com.villege.dade.face;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

public class UrlFilter implements Filter {

    protected String encoding = null;
    private final Log log = LogFactory.getLog(this.getClass());
    public void destroy() {

    }

	public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain chain) throws IOException, ServletException {

        //zhangshihuo test begin
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String queryString = httpRequest.getQueryString();
        String outStr = null;;
        //log.debug(outStr);
        if(queryString!=null) {
        	outStr = httpRequest.getRequestURI()+"?"+queryString;
        }
        else if(!httpRequest.getParameterMap().isEmpty()){
        	StringBuffer strBuffer = new StringBuffer();
        	@SuppressWarnings("rawtypes")
			Enumeration enumeration = httpRequest.getParameterNames();
        	while (enumeration.hasMoreElements()) {
        		String paramName = (String) enumeration.nextElement();
        		String paramValue = httpRequest.getParameter(paramName);
        		if(strBuffer.length()==0) {
        			strBuffer.append(paramName).append("=").append(paramValue);
        		}
        		else{
        			strBuffer.append("&").append(paramName).append("=").append(paramValue);
        		}
        	}
        	outStr = httpRequest.getRequestURI()+"?{"+strBuffer.toString()+"}";
        }
        else{
        	outStr = httpRequest.getRequestURI();
        }
        log.debug(outStr);
        //System.out.println(outStr);
        //zhangshihuo test end
        
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
