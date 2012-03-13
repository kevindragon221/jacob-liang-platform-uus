/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 23, 2010 @3:33:09 PM
 */
package com.bhtec.action.uum.user;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.constant.Common.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bhtec.action.uum.UumBaseAction;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.domain.pojo.uum.UumUserAgent;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;
import com.bhtec.service.iface.uum.user.UserAgentService;
import com.opensymphony.xwork2.ModelDriven;

public class UserAgentAction extends UumBaseAction  implements ModelDriven<UumUserAgent> {
	private static final long serialVersionUID = 1000000L;
	Logger log = Logger.getLogger(this.getClass());
	private UumUserAgent uumUserAgent = new UumUserAgent();
	private UserAgentService userAgentService;
	private List<UumUserAgent> userAgentList;
	private int count;
	private String failMesg;
	private List<Long> agentIdArray = new ArrayList<Long>();
	private boolean hasUserAgentBol;
	private List<String> modOptIdList;
	private List<SysplModuleMemu> agentModuleMenuList;
	/**
	 * 功能说明：保存用户代理
	 * @author jacobliang
	 * @throws 
	 * @time Sep 23, 2010 6:03:41 PM
	 */
	public void saveUserAgent(){
		try {
			userAgentService.setHttpServletRequest(this.getHttpServletRequest());
			Long userId = (Long)getHttpServletRequest().getSession().getAttribute(USER_ID);
			uumUserAgent.setUserId(userId);
			Long orgRoleId = (Long)getHttpServletRequest().getSession().getAttribute(ORGAN_ROLE_ID);
			uumUserAgent.setOrgRoleId(orgRoleId);
			userAgentService.saveUserAgent(uumUserAgent,modOptIdList);
			this.returnSuccess();
		} catch (ApplicationException e) {
			e.printStackTrace();
			this.returnFailur(e.toString());
		}catch (SystemException e) {
			e.printStackTrace();
			this.returnFailur(this.ERROR);
		}
	}
	
	
	
	/**
	 * 功能说明：查询用户代理信息
	 * @author jacobliang
	 * @throws 
	 * @time Sep 23, 2010 
	 */
	public String findUserAgentByCon(){
		Long userId = (Long)getHttpServletRequest().getSession().getAttribute(USER_ID);
		Map map = userAgentService.findUserAgentByCon(getStart(),getLimit(),userId);
		userAgentList = (List<UumUserAgent>)map.get(BUSI_LIST);
		count = (Integer)map.get(TOTAL_PROPERTY);
		return SUCCESS;
	}
	
	/**
	 * 功能说明：判断是否已经为此用户授过权
	 * @author jacobliang
	 * @return
	 * @time Feb 21, 2012 4:13:57 PM
	 */
	public String hasAgentUser(){
		Long userId = (Long)getHttpServletRequest().getSession().getAttribute(USER_ID);
		hasUserAgentBol = userAgentService.hasAgentUser(userId, uumUserAgent.getAgentUserId());
		return SUCCESS;
	}
	
	/**
	 * 功能说明：修改用户代理
	 * @author jacobliang
	 * @param moduleId
	 * @return
	 * @throws 
	 * @time Jul 27, 2010 9:43:55 AM
	 */
	public void modifyUserAgent(){
		try {
			userAgentService.setHttpServletRequest(this.getHttpServletRequest());
			userAgentService.modifyUserAgent(uumUserAgent);
			this.returnSuccess();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error("modifyUser() is not number. ", e);
			this.returnFailur(this.ERROR);
		} catch (ApplicationException e) {
			e.printStackTrace();
			this.returnFailur(e.toString());
		} catch (SystemException e) {
			e.printStackTrace();
			this.returnFailur(this.ERROR);
		}
	}
	
	
	/**
	 * 功能说明：删除用户代理
	 * @author jacobliang
	 * @return
	 * @time Oct 20, 2010 11:01:26 AM
	 */
	public String deleteUserAgent(){
		try{
			userAgentService.setHttpServletRequest(this.getHttpServletRequest());
			userAgentService.deleteUserAgent(agentIdArray);
		}catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 功能说明：查找用户分配及未分配的模块操作代理权限
	 * @author jacobliang
	 * @return
	 * @time Feb 22, 2012 9:53:18 AM
	 */
	public String userAssignAgentOptPrivilege(){
		agentModuleMenuList = 
			this.userAgentService.userAssignAgentOptPrivilege(uumUserAgent.getAgentId().toString());
		return SUCCESS;
	}
	
	public UumUserAgent getModel() {
		return uumUserAgent;
	}

	public List<UumUserAgent> getUserAgentList() {
		return userAgentList;
	}

	public void setUserAgentList(List<UumUserAgent> userAgentList) {
		this.userAgentList = userAgentList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getFailMesg() {
		return failMesg;
	}

	public void setFailMesg(String failMesg) {
		this.failMesg = failMesg;
	}

	public void setUserAgentService(UserAgentService userAgentService) {
		this.userAgentService = userAgentService;
	}



	public List<Long> getAgentIdArray() {
		return agentIdArray;
	}



	public void setAgentIdArray(List<Long> agentIdArray) {
		this.agentIdArray = agentIdArray;
	}



	public boolean getHasUserAgentBol() {
		return hasUserAgentBol;
	}



	public void setHasUserAgentBol(boolean hasUserAgentBol) {
		this.hasUserAgentBol = hasUserAgentBol;
	}



	public List<String> getModOptIdList() {
		return modOptIdList;
	}



	public void setModOptIdList(List<String> modOptIdList) {
		this.modOptIdList = modOptIdList;
	}



	public List<SysplModuleMemu> getAgentModuleMenuList() {
		return agentModuleMenuList;
	}



	public void setAgentModuleMenuList(List<SysplModuleMemu> agentModuleMenuList) {
		this.agentModuleMenuList = agentModuleMenuList;
	}
		
}
