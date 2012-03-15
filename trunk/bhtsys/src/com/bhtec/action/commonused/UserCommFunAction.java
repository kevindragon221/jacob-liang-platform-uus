/**
 *功能说明：用户自定义系统页面布局类
 * @author jacobliang
 * @time @Nov 4, 2010 @10:00:44 AM
 */
package com.bhtec.action.commonused;

import static com.bhtec.common.constant.Common.USER_ID;

import java.util.List;

import com.bhtec.action.BaseAction;
import com.bhtec.domain.pojo.uum.UumUserCommfun;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;
import com.bhtec.service.iface.commonused.UserCommFunService;
import com.opensymphony.xwork2.ModelDriven;

public class UserCommFunAction extends BaseAction implements ModelDriven<UumUserCommfun> {
	private static final long serialVersionUID = 100000L;
	private UumUserCommfun uumUserCommfun = new UumUserCommfun();
	private UserCommFunService userCommFunService;
	private String failMesg;
	private List<Long> moduleIdList;
	/**
	 * 功能说明：保存用户设置的页面布局
	 * @author jacobliang
	 * @param uumUserPageLayout
	 * @time Nov 4, 2010 9:47:48 AM
	 */
	public String saveUserCommFun(){
		try {
			userCommFunService.setHttpServletRequest(this.getHttpServletRequest());
			long userId = (Long)this.getSession().get(USER_ID);
			this.userCommFunService.saveUserCommFun(userId, moduleIdList);
		} catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	public UumUserCommfun getModel() {
		return uumUserCommfun;
	}
	
	
	public String getFailMesg() {
		return failMesg;
	}
	public void setFailMesg(String failMesg) {
		this.failMesg = failMesg;
	}
	public void setUserCommFunService(UserCommFunService userCommFunService) {
		this.userCommFunService = userCommFunService;
	}
	public List<Long> getModuleIdList() {
		return moduleIdList;
	}
	public void setModuleIdList(List<Long> moduleIdList) {
		this.moduleIdList = moduleIdList;
	}
}
