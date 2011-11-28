/**
 *功能说明：对
 * @author jacobliang
 * @time @Sep 14, 2010 @11:45:55 AM
 */
package com.bhtec.common.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class AccessControlFilter implements Filter {
	Logger log = Logger.getLogger(this.getClass());
	
	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
							FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        /* 参数为true时，返回的是null;     
         * 没有参数或是false则返回新的session
         */   
        HttpSession session = httpRequest.getSession(false);
        String serveletPath = httpRequest.getServletPath();
        log.info("serveletPath---> " + serveletPath);
//        session =null;
        if(serveletPath.contains("loginSys")){
        	chain.doFilter(httpRequest, httpResponse);
        }else{
        	if(session == null){
        		  // 如果是Ext请求
        		String header = httpRequest.getHeader("x-requested-with");
        		Enumeration enumeration = httpRequest.getHeaderNames();
        		while(enumeration.hasMoreElements()){
        			String s = (String)enumeration.nextElement();
        			log.info("header name =" +s );
        		}
        		log.info("header---> " + header);
				if (header != null && header.equalsIgnoreCase("XMLHttpRequest")) {
					httpResponse.setHeader("sessionstatus", "timeout");
				}else{
					chain.doFilter(httpRequest, httpResponse);
				}
//				else {
//					RequestDispatcher rd = httpRequest.getRequestDispatcher("/login.html");
//					rd.include(httpRequest, httpResponse);
//					PrintWriter out = httpResponse.getWriter();
//					out.flush();
//					out.close();
//				}
        	}else{
        		chain.doFilter(httpRequest, httpResponse);
        	}
        }
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
