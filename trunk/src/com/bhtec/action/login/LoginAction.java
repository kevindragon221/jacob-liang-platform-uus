/**
 *功能说明：登录用户合法检验用户相关配置信息加载权限过滤
 * @author jacobliang
 * @time @Sep 14, 2010 @11:00:09 AM
 */
package com.bhtec.action.login;

import static com.bhtec.common.constant.ActionVariable.ADMIN_ORGNAME;
import static com.bhtec.common.constant.ActionVariable.ADMIN_ROLENAME;
import static com.bhtec.common.constant.Common.AGENT_ID;
import static com.bhtec.common.constant.Common.AGENT_LOGIN;
import static com.bhtec.common.constant.Common.AGENT_USER;
import static com.bhtec.common.constant.Common.COMMON_FUN;
import static com.bhtec.common.constant.Common.DISABLE;
import static com.bhtec.common.constant.Common.FIRST;
import static com.bhtec.common.constant.Common.FOURTH;
import static com.bhtec.common.constant.Common.LOG_LEVEL_THIRD;
import static com.bhtec.common.constant.Common.NORMAL_LOGIN;
import static com.bhtec.common.constant.Common.ORGAN_ID;
import static com.bhtec.common.constant.Common.ORGAN_NAME;
import static com.bhtec.common.constant.Common.ORGAN_ROLE_ID;
import static com.bhtec.common.constant.Common.PASSWORD;
import static com.bhtec.common.constant.Common.PRIVILEGE_TYPE_ROW;
import static com.bhtec.common.constant.Common.ROLE_ID;
import static com.bhtec.common.constant.Common.ROLE_LIST;
import static com.bhtec.common.constant.Common.ROLE_NAME;
import static com.bhtec.common.constant.Common.SECOND;
import static com.bhtec.common.constant.Common.THIRD;
import static com.bhtec.common.constant.Common.USER_CODE;
import static com.bhtec.common.constant.Common.USER_ID;
import static com.bhtec.common.constant.Common.USER_LOGIN;
import static com.bhtec.common.constant.Common.USER_NAME;
import static com.bhtec.common.tools.UtilTools.isNull;
import static com.bhtec.common.tools.UtilTools.isNullOrEmpty;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.bhtec.action.BaseAction;
import com.bhtec.common.listener.OnlineCounter;
import com.bhtec.common.tools.JSONUtil;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.domain.pojo.uum.UumOrgRoleRef;
import com.bhtec.domain.pojo.uum.UumOrgan;
import com.bhtec.domain.pojo.uum.UumRole;
import com.bhtec.domain.pojo.uum.UumRoleUserRef;
import com.bhtec.domain.pojo.uum.UumUser;
import com.bhtec.domain.pojo.uum.UumUserAgent;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;
import com.bhtec.service.iface.login.LoginService;

public class LoginAction extends BaseAction {
	private static final long serialVersionUID = 100000000L;
	Logger log = Logger.getLogger(getClass());
	private LoginService loginService;
	private String returnMesg;
	private String failMesg;
	private List<UumRoleUserRef> uumRoleUserRefList = new ArrayList<UumRoleUserRef>();//角色用户关系
	private List<SysplModuleMemu> firstList = new ArrayList<SysplModuleMemu>();//第一级
	private Map secondMap = new HashMap();//第二级
	private Map thirdMap = new HashMap();//第三级
	private Map fourthMap = new HashMap();//第四级操作
	private String userCode;
	private List<SysplModuleMemu> assignUumUserCommfunList = new ArrayList<SysplModuleMemu>();
	private List<UumUserAgent> userAgentList;//代理用户
	private String userInfomationJson;
	private String agentUserInfomationJson;
	/**
	 * 功能说明：预登录 代理 多角色
	 * @author jacobliang
	 * @return
	 * @time Feb 23, 2012 10:03:26 AM
	 */
	public String preLogin(){
		try {
			UumUser uumUser = loginService.obtainUserByUserCodePwd(userCode, "");
			if(uumUser == null)return SUCCESS;
			Map map = loginService.preLogin(uumUser);
			userAgentList = map.get(AGENT_USER)==null?null:(List<UumUserAgent>)map.get(AGENT_USER);
			uumRoleUserRefList = map.get(ROLE_LIST)==null?null:(List<UumRoleUserRef>)map.get(ROLE_LIST);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 功能说明：登录系统一阶段
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Sep 14, 2010 11:05:04 AM
	 */
	public String loginSysFirstDo(){
		/*
		 * 1、验证用户是否存在 并得到用户对象
		 * 2 用户没有相应角色则提示没有使用系统权限
		 * 3 用户状态是否可用
		 * 4 用户有效期是否已到
		 * 5、存在并已经登录则踢出
		 * 6 获得系统定义的角色切换方式，如果用户拥有多个角色则提示用户选择角色
		 * 7、用户机构名称及ID、用户角色名称及ID角色级别、用户名称及ID、放入session
		 */
		UumRoleUserRef uumRoleUserRef = (UumRoleUserRef)JSONUtil
							.getObject4JsonString(userInfomationJson, UumRoleUserRef.class);
		String password = this.getHttpServletRequest().getParameter(PASSWORD);
		String userName = "";
		Long userId = 0L;
		try {
			if (!loginService.isAdministrator(userCode)) {//非管理员
				if (uumRoleUserRef == null) {
					this.setReturnMesg("2");// 用户没有权限
					return ERROR;
				}
				if (uumRoleUserRef.getOrgRoleId() == 0) {
					this.setReturnMesg("2");// 用户为无角色用户没有使用权限
					return ERROR;
				}
				UumUser uumUser = loginService.obtainUserByUserCodePwd(
						userCode, password);
				if (uumUser == null) {
					this.setReturnMesg("1");// 用户不存在
					return ERROR;
				}
				if (DISABLE.equals(uumUser.getStatus())) {
					this.setReturnMesg("3");// 用户已被停用
					return ERROR;
				}
				if (uumUser.getUserAvidate() == null
						|| (new Date(System.currentTimeMillis())).after(uumUser
								.getUserAvidate())) {
					this.setReturnMesg("4");// 用户过期
					return ERROR;
				}
			}
			userName = uumRoleUserRef.getUumUser().getUserName();
			userId = uumRoleUserRef.getUumUser().getUserId();
			if(!isNullOrEmpty(agentUserInfomationJson)){//有代理
				UumUserAgent uumUserAgent = (UumUserAgent)JSONUtil
								.getObject4JsonString(agentUserInfomationJson, UumUserAgent.class);
				BeanUtils.copyProperties(uumRoleUserRef, uumUserAgent);
				userName = uumUserAgent.getUserName()+"("+userName+"D)";
				userId = uumUserAgent.getUserId();
				userCode = uumUserAgent.getUserCode();
				getSession().put(AGENT_ID, uumUserAgent.getAgentId());//用户代理主键ID
			}else{
				getSession().remove(AGENT_ID);//清除用户代理主键ID
			}
		} catch (ApplicationException e) {
			log.error("login occur error. ", e);
			e.printStackTrace();
			failMesg = e.toString();
		} catch (SystemException e) {
			log.error("login occur error. ", e);
			e.printStackTrace();
			failMesg = e.toString();
		}catch (Exception e) {
			log.error("login occur error. ", e);
			e.printStackTrace();
			failMesg = e.toString();
		}
		/*
		 * 用户机构名称及ID、用户角色名称及ID角色级别、用户名称及ID、放入session
		 * ID 均为Long
		 */ 
		String organName = uumRoleUserRef.getOrganName()==null?ADMIN_ORGNAME:uumRoleUserRef.getOrganName();
		Long organId = uumRoleUserRef.getOrganId()==null?-1:uumRoleUserRef.getOrganId();
		String roleName = uumRoleUserRef.getRoleName()==null?ADMIN_ROLENAME:uumRoleUserRef.getRoleName();
		Long roleId = uumRoleUserRef.getRoleId()==null?-1:uumRoleUserRef.getRoleId();
		Long organRoleId = uumRoleUserRef.getOrgRoleId()==null?-1:uumRoleUserRef.getOrgRoleId();
		
		getSession().put(ORGAN_NAME, organName);
		getSession().put(ORGAN_ID, organId);
		getSession().put(ROLE_NAME, roleName);
		getSession().put(ROLE_ID, roleId);
		getSession().put(ORGAN_ROLE_ID, organRoleId);
		getSession().put(USER_NAME, userName);
		getSession().put(USER_CODE, userCode);
		getSession().put(USER_ID, userId);
		
		log.info("organName:"+organName+"    -    organId : "+organId);
		log.info("roleName :"+roleName+ "    -    roleId  : "+roleId+  "    -    organRoleId  : "+organRoleId);
		log.info("userName : "+userName+"    -    userCode: "+userCode+"    -    userId   : "+userId);
		return SUCCESS;
	}
	/**
	 * 功能说明：切换角色
	 * @author jacobliang
	 * @return
	 * @time Nov 5, 2010 9:48:29 AM
	 */
	public String changeRole(){
		String userCode = this.getHttpServletRequest().getParameter(USER_CODE);
		String userName = this.getHttpServletRequest().getParameter(USER_NAME);
		Long userId = this.getHttpServletRequest().getParameter(USER_ID)==null
					  ?0
					  :new Long(this.getHttpServletRequest().getParameter(USER_ID));
		String roleName = this.getHttpServletRequest().getParameter(ROLE_NAME);
		Long roleId = this.getHttpServletRequest().getParameter(ROLE_ID)==null
					  ?0
					  :new Long(this.getHttpServletRequest().getParameter(ROLE_ID));
		Long organRoleId = this.getHttpServletRequest().getParameter(ORGAN_ROLE_ID)==null
					  ?0
					  :new Long(this.getHttpServletRequest().getParameter(ORGAN_ROLE_ID));
		String organName = this.getHttpServletRequest().getParameter(ORGAN_NAME);
		Long organId = this.getHttpServletRequest().getParameter(ORGAN_ID)==null
					  ?0
					  :new Long(this.getHttpServletRequest().getParameter(ORGAN_ID));
		getSession().put(ORGAN_NAME, organName);
		getSession().put(ORGAN_ID, organId);
		getSession().put(ROLE_NAME, roleName);
		getSession().put(ROLE_ID, roleId);
		userName = userName==null?(String)getSession().get(USER_NAME):userName;
		userCode = userCode==null?(String)getSession().get(USER_CODE):userCode;
		userId = userId==0?(Long)getSession().get(USER_ID):userId;
		getSession().put(USER_CODE, userCode);
		getSession().put(USER_NAME, userName);
		getSession().put(USER_ID, userId);
		return this.SUCCESS;
	}
	/**
	 * 功能说明：登录系统二阶段
	 * @author jacobliang
	 * @return
	 * @time Nov 3, 2010 9:12:08 PM
	 */
	public String loginSysSecondDo(){
		/*
		 * 8 得到用户自定义的页面布局模板 -- viewportfactory implement
		 * 9 通过配置XML获得管理员用户，管理员则不需要权限过滤
		 * 10、模块权限过滤，用户是否有特殊权限，并过滤特殊权限
		 * 11、用户代理模块操作 
		 * 12、常用功能		
		 * 13、portal		?
		 */
		try{
			Long userId = (Long)getSession().get(USER_ID);
			Long roleId = (Long)getSession().get(ROLE_ID);
			Long organRoleId = (Long)getSession().get(ORGAN_ROLE_ID);
			Long organId = (Long)getSession().get(ORGAN_ID);
			String userCode = (String)getSession().get(USER_CODE);
			Long agentId = (Long)getSession().get(AGENT_ID);
			
			//加载用户模块和菜单
			Map map = loginService.privilegeFilter(userId, roleId, organId, organRoleId, userCode, agentId);
			firstList = (List<SysplModuleMemu>)map.get(FIRST);
			secondMap = (Map)map.get(SECOND);
			thirdMap = (Map)map.get(THIRD);
			fourthMap = (Map)map.get(FOURTH);
			String userRowPrivilege = (String)map.get(PRIVILEGE_TYPE_ROW);
			log.info("user row privilege : 【"+userRowPrivilege+"】");
			assignUumUserCommfunList = (List<SysplModuleMemu>)map.get(COMMON_FUN);
			
			//记录登录日志
			loginService.setHttpServletRequest(this.getHttpServletRequest());
			StringBuffer optContent = new StringBuffer();
			optContent.append(getSession().get(ORGAN_NAME)+"-")
			.append(getSession().get(ROLE_NAME)+"-")
			.append(getSession().get(USER_NAME));
			loginService.saveLog(LOG_LEVEL_THIRD, USER_LOGIN, USER_LOGIN, optContent.toString(), "");
		}catch (ApplicationException e) {
			log.error("login occur error. ", e);
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			log.error("login occur error. ", e);
			e.printStackTrace();
			failMesg = e.toString();
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：用户多次登录踢出
	 * @author jacobliang
	 * @param userCode
	 * @time Nov 3, 2010 1:28:53 PM
	 */
	private void userRepeatLogin(String userCode){
		Map sessionMap = OnlineCounter.sessionMap;
		Object sessionObj = sessionMap.get(userCode);
		if(sessionObj == null){
			OnlineCounter.getInstance().addUserSession(userCode, getHttpServletRequest().getSession());
			log.info("add=== "+getHttpServletRequest().getSession().getId());
		}else{
			HttpSession session = (HttpSession)sessionObj;
			log.info("destroy==="+session.getId());
			session.invalidate();
		}
	}
	
	/**
	 * 功能说明：login系统跳转
	 * @author jacobliang
	 * @return
	 * @time Nov 3, 2010 9:11:25 PM
	 */
	public String loginActionForword(){
		return this.SUCCESS;
	}
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public List<UumRoleUserRef> getUumRoleUserRefList() {
		return uumRoleUserRefList;
	}

	public void setUumRoleUserRefList(List<UumRoleUserRef> uumRoleUserRefList) {
		this.uumRoleUserRefList = uumRoleUserRefList;
	}

	public String getReturnMesg() {
		return returnMesg;
	}

	public void setReturnMesg(String returnMesg) {
		this.returnMesg = returnMesg;
	}

	public String getFailMesg() {
		return failMesg;
	}

	public void setFailMesg(String failMesg) {
		this.failMesg = failMesg;
	}

	public List<SysplModuleMemu> getFirstList() {
		return firstList;
	}

	public void setFirstList(List<SysplModuleMemu> firstList) {
		this.firstList = firstList;
	}

	public Map getSecondMap() {
		return secondMap;
	}

	public void setSecondMap(Map secondMap) {
		this.secondMap = secondMap;
	}

	public Map getThirdMap() {
		return thirdMap;
	}

	public void setThirdMap(Map thirdMap) {
		this.thirdMap = thirdMap;
	}

	public Map getFourthMap() {
		return fourthMap;
	}

	public void setFourthMap(Map fourthMap) {
		this.fourthMap = fourthMap;
	}

	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public List<SysplModuleMemu> getAssignUumUserCommfunList() {
		return assignUumUserCommfunList;
	}
	public void setAssignUumUserCommfunList(
			List<SysplModuleMemu> assignUumUserCommfunList) {
		this.assignUumUserCommfunList = assignUumUserCommfunList;
	}
	public List<UumUserAgent> getUserAgentList() {
		return userAgentList;
	}
	public void setUserAgentList(List<UumUserAgent> userAgentList) {
		this.userAgentList = userAgentList;
	}

	public String getUserInfomationJson() {
		return userInfomationJson;
	}

	public void setUserInfomationJson(String userInfomationJson) {
		this.userInfomationJson = userInfomationJson;
	}

	public String getAgentUserInfomationJson() {
		return agentUserInfomationJson;
	}

	public void setAgentUserInfomationJson(String agentUserInfomationJson) {
		this.agentUserInfomationJson = agentUserInfomationJson;
	}

}
