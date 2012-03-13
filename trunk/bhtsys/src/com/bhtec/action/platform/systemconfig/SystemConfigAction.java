/**
 *功能说明：
 * @author jacobliang
 * @time @Nov 21, 2010 @1:30:29 PM
 */
package com.bhtec.action.platform.systemconfig;

import java.io.IOException;

import com.bhtec.action.platform.PlatformBaseAction;
import com.bhtec.common.util.SystemConfigBean;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;
import com.bhtec.service.iface.platform.systemconfig.SystemConfigService;
import com.opensymphony.xwork2.ModelDriven;

public class SystemConfigAction extends PlatformBaseAction implements ModelDriven<SystemConfigBean>{
	private static final long serialVersionUID = 10000L;
	private SystemConfigBean systemConfigBean = new SystemConfigBean();
	private SystemConfigService systemConfigService;
	private String failMesg;
	private String userAddDelFlag;
	/**
	 * 功能说明：获得系统配置信息对象
	 * @author jacobliang
	 * @return
	 * @time Nov 21, 2010 1:41:21 PM
	 */
	public String obtainSystemConfigInfo(){
		try {
			systemConfigBean = systemConfigService.obtainSystemConfigInfo();
		} catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：设置系统配置信息
	 * @author jacobliang
	 * @param systemConfig	系统配置对象
	 * @throws ApplicationException
	 * @time Nov 21, 2010 1:47:51 PM
	 */
	public String setSystemConfigInfo(){
		try{
			systemConfigService.setHttpServletRequest(this.getHttpServletRequest());
			systemConfigService.setSystemConfigInfo(systemConfigBean,userAddDelFlag);
		} catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	public void startJconsole(){
		Runtime runtime = Runtime.getRuntime();
		try { 
			runtime.exec("cmd.exe /c jconsole ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SystemConfigBean getModel() {
		return systemConfigBean;
	}

	public void setSystemConfigService(SystemConfigService systemConfigService) {
		this.systemConfigService = systemConfigService;
	}

	public String getFailMesg() {
		return failMesg;
	}

	public void setFailMesg(String failMesg) {
		this.failMesg = failMesg;
	}

	public String getUserAddDelFlag() {
		return userAddDelFlag;
	}

	public void setUserAddDelFlag(String userAddDelFlag) {
		this.userAddDelFlag = userAddDelFlag;
	}
	
}
