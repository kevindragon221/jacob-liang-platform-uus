/**
 *功能说明：拦截EXT的action请求
 * @author jacobliang
 * @time @Sep 14, 2010 @5:50:42 PM
 */
package com.bhtec.common.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SessionInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1000000L;
	
	
	/**
	 * 拦截EXT的action请求，注意拦截后的返回JSON数据处理
	 */
	@Override
	public String intercept(ActionInvocation actioninvocation) throws Exception {
		ActionContext context = actioninvocation.getInvocationContext();   
        Map session = context.getSession();
        Object userId = session.get("userId");
        if(userId != null){
        	return actioninvocation.invoke();
        }else{
            HttpServletResponse response = ServletActionContext.getResponse();
            PrintWriter pw = response.getWriter();
    		pw.write("{sessionstatus:'timeout'}");//JS接收  
        	return Action.NONE;
        }
	}
	

}
