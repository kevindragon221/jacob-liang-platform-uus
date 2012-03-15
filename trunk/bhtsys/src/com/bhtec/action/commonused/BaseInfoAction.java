/**
 *功能说明：主要通过dwr调用准备页面一些显示信息
 * @author jacobliang
 * @time @Nov 7, 2010 @1:31:12 PM
 */
package com.bhtec.action.commonused;

import static com.bhtec.common.constant.Common.ORGAN_NAME;
import static com.bhtec.common.constant.Common.ROLE_NAME;
import static com.bhtec.common.constant.Common.USER_CODE;
import static com.bhtec.common.constant.Common.USER_ID;
import static com.bhtec.common.constant.Common.USER_NAME;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bhtec.action.BaseAction;
import com.bhtec.common.tools.SpringContextUtil;
import com.bhtec.common.util.SystemConifgXmlParse;
import com.bhtec.domain.pojo.platform.SysplAffiche;
import com.bhtec.domain.pojo.uum.UumUser;
import com.bhtec.service.iface.platform.affiche.AfficheService;
import com.googlecode.jsonplugin.annotations.JSON;

public class BaseInfoAction extends BaseAction {
	private static final long serialVersionUID = 10000L;
	private UumUser uumUser = new UumUser();
	/**
	 * 功能说明：准备登录用户信息
	 * @author jacobliang
	 * @param request
	 * @return
	 * @time Nov 13, 2010 11:52:33 AM
	 */
	@JSON(serialize = false)
	public String getUserRoleOrgan(HttpServletRequest request){
		HttpSession session = request.getSession();
		String userName = (String)session.getAttribute(USER_NAME);
		String roleName = (String)session.getAttribute(ROLE_NAME);
		String organName = (String)session.getAttribute(ORGAN_NAME);
		return organName+"-"+roleName+"-"+userName;
	}
	
	/**
	 * 功能说明：获得要修改的用户信息
	 * @author jacobliang
	 * @return
	 * @time Nov 18, 2010 4:24:54 PM
	 */
	@JSON(serialize = false)
	public String getUserInfoForModify(){
		Long userId = (Long)getSession().get(USER_ID);
		String userCode = (String)getSession().get(USER_CODE);
		String userName = (String)getSession().get(USER_NAME);
		
		uumUser.setUserId(userId);
		uumUser.setUserCode(userCode);
		uumUser.setUserName(userName);
		return SUCCESS;
	}
	/**
	 * 功能说明：获得系统名称
	 * @author jacobliang
	 * @return
	 * @time Nov 19, 2010 11:28:01 AM
	 */
	@JSON(serialize = false)
	public String getSystemName(){
		String systemName = SystemConifgXmlParse.getInstance().getSystemConfigBean().getSystemName();
		return systemName;
	}
	
	/**
	 * 功能说明：查询所有有效期公告信息
	 * @author jacobliang
	 * @return List<SysplAffiche>	所有公告信息
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public List<SysplAffiche> findAllAfficheBeforeValidate(){
		AfficheService afficheService = (AfficheService)SpringContextUtil.getBean("afficheService");
		List<SysplAffiche> afficheList = afficheService.findAllAfficheBeforeValidate();
		return afficheList;
	}

	public UumUser getUumUser() {
		return uumUser;
	}

	public void setUumUser(UumUser uumUser) {
		this.uumUser = uumUser;
	}
}
