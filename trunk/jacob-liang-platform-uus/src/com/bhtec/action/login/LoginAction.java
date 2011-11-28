/**
 *功能说明：登录用户合法检验用户相关配置信息加载权限过滤
 * @author jacobliang
 * @time @Sep 14, 2010 @11:00:09 AM
 */
package com.bhtec.action.login;

import static com.bhtec.common.constant.ActionVariable.ADMIN_ORGNAME;
import static com.bhtec.common.constant.ActionVariable.ADMIN_ROLENAME;
import static com.bhtec.common.constant.ActionVariable.CONF_ADMIN_XPATH;
import static com.bhtec.common.constant.ActionVariable.CONF_ROLECHANGE_XPATH;
import static com.bhtec.common.constant.Common.CHANGE_ROLE_FLAG;
import static com.bhtec.common.constant.Common.DISABLE;
import static com.bhtec.common.constant.Common.FIRST;
import static com.bhtec.common.constant.Common.FOURTH;
import static com.bhtec.common.constant.Common.IN_SYSTEM;
import static com.bhtec.common.constant.Common.LOG_LEVEL_THIRD;
import static com.bhtec.common.constant.Common.MULTI_ROLE_LIST;
import static com.bhtec.common.constant.Common.ORGAN_ID;
import static com.bhtec.common.constant.Common.ORGAN_NAME;
import static com.bhtec.common.constant.Common.PASSWORD;
import static com.bhtec.common.constant.Common.ROLE_ID;
import static com.bhtec.common.constant.Common.ROLE_NAME;
import static com.bhtec.common.constant.Common.SECOND;
import static com.bhtec.common.constant.Common.THIRD;
import static com.bhtec.common.constant.Common.USER_CODE;
import static com.bhtec.common.constant.Common.USER_ID;
import static com.bhtec.common.constant.Common.USER_LOGIN;
import static com.bhtec.common.constant.Common.USER_NAME;
import static com.bhtec.common.constant.ServiceVariable.Y;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.bhtec.action.BaseAction;
import com.bhtec.common.listener.OnlineCounter;
import com.bhtec.common.util.XMLSystemConifg;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.domain.pojo.uum.UumRoleUserRef;
import com.bhtec.domain.pojo.uum.UumUser;
import com.bhtec.domain.pojo.uum.UumUserCommfun;
import com.bhtec.domain.pojo.uum.UumUserPageLayout;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;
import com.bhtec.service.iface.login.LoginService;

public class LoginAction extends BaseAction {
	Logger log = Logger.getLogger(getClass());
	private LoginService loginService;
	private String returnMesg;
	private String failMesg;
	private List<UumRoleUserRef> uumRoleUserRefList = new ArrayList<UumRoleUserRef>();//角色用户关系
	private UumUserPageLayout uumUserPageLayout;
	private List<SysplModuleMemu> firstList = new ArrayList<SysplModuleMemu>();//第一级
	private Map secondMap = new HashMap();//第二级
	private Map thirdMap = new HashMap();//第三级
	private Map fourthMap = new HashMap();//第四级操作
	private Long userId;
	private String userCode;
	private String changeRoleType = IN_SYSTEM;
	private List<SysplModuleMemu> assignUumUserCommfunList = new ArrayList<SysplModuleMemu>();
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
		 * 
		 */
		userCode = this.getHttpServletRequest().getParameter(USER_CODE);
		String password = this.getHttpServletRequest().getParameter(PASSWORD);
		log.info("login code is: " + userCode);
		log.info("password is: " + password);
		try{
			UumUser uumUser = loginService.obtainUserByUserCodePwd(userCode, password);
			if(uumUser == null){
				returnMesg = "1";//用户不存在
				return this.ERROR;
			}
			uumRoleUserRefList = loginService.obtainRoleUserRefByUserId(uumUser.getUserId());
			UumRoleUserRef uumRoleUserRef = new UumRoleUserRef();
			if(isAdministrator(userCode)){//管理员
				if(uumRoleUserRefList != null){//具有相应的角色
					uumRoleUserRef = uumRoleUserRefList.get(0);
					getSession().remove(CHANGE_ROLE_FLAG);
					getSession().remove(MULTI_ROLE_LIST);
				}
			}else{
				if(uumRoleUserRefList == null){
					returnMesg = "2";//用户没有模块权限
					return this.ERROR;
				}
				if(uumRoleUserRefList.get(0).getOrgRoleId() == 0){
					returnMesg = "2";//用户为无角色用户没有使用权限
					return this.ERROR;
				}
				if(DISABLE.equals(uumUser.getStatus())){
					returnMesg = "3";//用户已被停用
					return this.ERROR;
				}
				if(uumUser.getUserAvidate() == null
					||(new Date(System.currentTimeMillis()))
					.after(uumUser.getUserAvidate())){
					returnMesg = "4";//用户过期
					return this.ERROR;
				}
				String chageRoleType = XMLSystemConifg.getInstance().getSystemConfigText(CONF_ROLECHANGE_XPATH);
				if(LOGIN.equalsIgnoreCase(chageRoleType) //登录类型login
						&& uumRoleUserRefList.size()>1){//角色切换
					userId = uumUser.getUserId();//角色切换设置userId
					returnMesg = "5";//用户多角色
					getSession().put(CHANGE_ROLE_FLAG, LOGIN);
					getSession().put(USER_NAME, uumUser.getUserName());
					return this.SUCCESS;
				}else{
					for(UumRoleUserRef uumRoleUserReff:uumRoleUserRefList){
						if(Y.equalsIgnoreCase(uumRoleUserReff.getIsDefault())){
							uumRoleUserRef = uumRoleUserReff;//按用户默认角色登录
						}
					}
					getSession().put(CHANGE_ROLE_FLAG, IN_SYSTEM);
					getSession().put(MULTI_ROLE_LIST, uumRoleUserRefList);
				}
			}
			
//			userRepeatLogin(userCode);//踢出
			
			/*
			 * 用户机构名称及ID、用户角色名称及ID角色级别、用户名称及ID、放入session
			 * ID 均为Long
			 */ 
			String organName = uumRoleUserRef.getOrganName()==null?ADMIN_ORGNAME:uumRoleUserRef.getOrganName();
			Long organId = uumRoleUserRef.getOrganId()==null?-1:uumRoleUserRef.getOrganId();
			String roleName = uumRoleUserRef.getRoleName()==null?ADMIN_ROLENAME:uumRoleUserRef.getRoleName();
			Long roleId = uumRoleUserRef.getRoleId()==null?-1:uumRoleUserRef.getRoleId();
			
			getSession().put(ORGAN_NAME, organName);
			getSession().put(ORGAN_ID, organId);
			getSession().put(ROLE_NAME, roleName);
			getSession().put(ROLE_ID, roleId);
			getSession().put(USER_NAME, uumUser.getUserName());
			getSession().put(USER_CODE, userCode);
			getSession().put(USER_ID, uumUser.getUserId());
		}catch (ApplicationException e) {
			log.error("login occur error. ", e);
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			log.error("login occur error. ", e);
			e.printStackTrace();
			failMesg = e.toString();
		}
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
		 * 11、用户代理模块操作 ?
		 * 12、常用功能		*
		 * 13、portal		?
		 */
		try{
			Long userId = (Long)getSession().get(USER_ID);
			Long roleId = (Long)getSession().get(ROLE_ID);
			String userCode = (String)getSession().get(USER_CODE);
			
			Object objRoleList = getSession().get(MULTI_ROLE_LIST);//多角色列表
			if(objRoleList != null)
				uumRoleUserRefList = (List<UumRoleUserRef>)objRoleList;
			Object objRoleType = getSession().get(CHANGE_ROLE_FLAG);//切换角色标志
			if(objRoleType != null)
				changeRoleType = (String)objRoleType;
			Map map = null;
			if(isAdministrator(userCode)){//是管理员
				//加载全部权限
				map = loginService.findAllModuleOptForAdmin();
			}else{//非管理员
				//模块权限过滤，用户是否有特殊权限，并过滤特殊权限
				map = loginService.findFilterUserModOptPrivilege(userId, roleId);
			}
			//加载用户模块和菜单
			firstList = (List<SysplModuleMemu>)map.get(FIRST);
			secondMap = (Map)map.get(SECOND);
			thirdMap = (Map)map.get(THIRD);
			fourthMap = (Map)map.get(FOURTH);
			//当前用户常用功能
			List<UumUserCommfun> uumUserCommfunList = loginService.findUserCommFunByUserId(userId);
			//有效的常用功能
			List<UumUserCommfun> availabilityCommFunList = new ArrayList<UumUserCommfun>();
			if(uumUserCommfunList != null && uumUserCommfunList.size()>0){
				Iterator iterator = thirdMap.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry entry = (Map.Entry) iterator.next();
					List<SysplModuleMemu> secondMenuList = (List<SysplModuleMemu>)thirdMap.get(entry.getKey());
					for(UumUserCommfun uumUserCommfun:uumUserCommfunList){
						for(SysplModuleMemu sysplModuleMemu:secondMenuList){
							if(uumUserCommfun.getId().getModuleId().longValue()
									== sysplModuleMemu.getModuleId().longValue()){
								assignUumUserCommfunList.add(sysplModuleMemu);
								availabilityCommFunList.add(uumUserCommfun);
								break;
							}
						}
					}
				}
			}
			//清除无用的常用功能数据
			uumUserCommfunList.removeAll(availabilityCommFunList);
			loginService.deleteUserCommFunById(uumUserCommfunList);
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
	 * 功能说明：判断用户是否为管理员,管理员信息指定在配置文件中
	 * @author jacobliang
	 * @return
	 * @throws ApplicationException
	 * @time Nov 4, 2010 2:13:53 PM
	 */
	private boolean isAdministrator(String userCode) throws ApplicationException{
		//通过配置XML获得管理员用户，管理员则不需要权限过滤
		XMLSystemConifg xMLSystemConifg = XMLSystemConifg.getInstance();
		List<String> adminList = xMLSystemConifg.getSysytemConfigTextList(CONF_ADMIN_XPATH);
		if(adminList.contains(userCode)){//管理员
			return true;
		}
		return false;
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

	public UumUserPageLayout getUumUserPageLayout() {
		return uumUserPageLayout;
	}

	public void setUumUserPageLayout(UumUserPageLayout uumUserPageLayout) {
		this.uumUserPageLayout = uumUserPageLayout;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getChangeRoleType() {
		return changeRoleType;
	}
	public void setChangeRoleType(String changeRoleType) {
		this.changeRoleType = changeRoleType;
	}
	public List<SysplModuleMemu> getAssignUumUserCommfunList() {
		return assignUumUserCommfunList;
	}
	public void setAssignUumUserCommfunList(
			List<SysplModuleMemu> assignUumUserCommfunList) {
		this.assignUumUserCommfunList = assignUumUserCommfunList;
	}
}
