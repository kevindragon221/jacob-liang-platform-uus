package com.bhtec.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import static com.bhtec.common.constant.ActionVariable.*;
import com.opensymphony.xwork2.ActionSupport;

/**
 *功能描述：BaseAction提供一些常用参数供其它action继承调用
 *创建时间：Feb 24, 2010 10:58:25 AM 
 *@author lianglp
 *@version 1.0
 */

public class BaseAction extends ActionSupport implements ParameterAware,RequestAware,
														 SessionAware,ApplicationAware,
														 ServletContextAware,ServletRequestAware,
														 ServletResponseAware{
	
	private static final long serialVersionUID = 1000000L;
	private Map parameters;
	private Map request;
	private Map session;
	private Map application;
	private ServletContext servletContext;
	private HttpServletRequest httpServletRequest;
	private HttpServletResponse httpServletResponse;
	
	//删除记录的主键ids
	private String delRecIds;
	//修改查看某一记录的主键id
	private String modViewRecId;
	// 分页参数
	private int start = 0;
	private int limit = LIMIT;
	//树结点 
	private long treeId = -1;
	
	public void setParameters(Map parameters) {
		this.parameters = parameters;		
	}

	public void setRequest(Map request) {
		this.request = request;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	public void setApplication(Map application) {
		this.application = application;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void setServletRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	public void setServletResponse(HttpServletResponse httpServletResponse) {
		this.httpServletResponse = httpServletResponse;
	}
	
	public String getDelRecIds() {
		return delRecIds;
	}

	public void setDelRecIds(String delRecIds) {
		this.delRecIds = delRecIds;
	}

	public String getModViewRecId() {
		return modViewRecId;
	}

	public void setModViewRecId(String modViewRecId) {
		this.modViewRecId = modViewRecId;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}

	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	public HttpServletResponse getHttpServletResponse() {
		return httpServletResponse;
	}

	public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
		this.httpServletResponse = httpServletResponse;
	}

	public Map getParameters() {
		return parameters;
	}

	public Map getRequest() {
		return request;
	}

	public Map getSession() {
		return session;
	}

	public Map getApplication() {
		return application;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}
	
	/**
	 * 
	  *功能描述：submit提交返回函数
	  *@author lianglp
	  *@param
	  *@return
	  *@throws
	  *@version 
	  *@Jul 1, 2010 3:52:47 PM
	 */
	public void returnSuccess(){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write("{success:true}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 功能说明：submit失败返回到客户信息
	 * @author jacobliang
	 * @param
	 * @param
	 * @param failMesg
	 * @throws 
	 * @time Sep 11, 2010 10:49:24 PM
	 */
	public void returnFailur(String failMesg){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write("{failure:true,failMesg:'"+failMesg+"'}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public long getTreeId() {
		return treeId;
	}

	public void setTreeId(long treeId) {
		this.treeId = treeId;
	}
}
