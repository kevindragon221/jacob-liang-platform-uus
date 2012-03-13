/**
 *功能说明：角色管理
 * @author jacobliang
 * @time @Sep 27, 2010 @3:33:09 PM
 */
package com.bhtec.action.uum.role;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.*;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.constant.Common.USER_CODE;
import static com.bhtec.common.constant.Common.USER_ID;
import static com.bhtec.common.constant.ServiceVariable.ASSIGNED_MODOPTS;
import static com.bhtec.common.constant.ServiceVariable.UNASSIGNED_MODOPTS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bhtec.action.uum.UumBaseAction;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.domain.pojo.uum.UumRole;
import com.bhtec.domain.pojo.uum.UumRoleUserRef;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;
import com.bhtec.service.iface.uum.privilege.PrivilegeService;
import com.bhtec.service.iface.uum.role.RoleService;
import com.bhtec.service.iface.uum.role.RoleUserService;
import com.opensymphony.xwork2.ModelDriven;

public class RoleAction extends UumBaseAction  implements ModelDriven<UumRole> {
	private static final long serialVersionUID = 1000000L;
	private Logger log = Logger.getLogger(this.getClass());
	private UumRole uumRole = new UumRole();
	private List<UumRole> roleList;
	private String roleName;
	private boolean existRole;
	private int count;
	private String failMesg;
	private String disEnableFlag;
	private int disEnableResultFlag;
	private RoleService roleService;
	private RoleUserService roleUserService;
	
	private PrivilegeService privilegeService;
	private List<SysplModuleMemu> assignedModMenuList;
	private List<SysplModuleMemu> unassignedModMenuList;
	private List<String> modOptIdList;
	private List<UumRoleUserRef> uumRoleUserRefList = new ArrayList<UumRoleUserRef>();//角色用户关系
	private String privilegeScope;
	private String roleRowPrivilege;
	/**
	 * 功能说明：保存角色
	 * @author jacobliang
	 * @param
	 * @param
	 * @throws 
	 * @time Sep 27, 2010 6:03:41 PM
	 */
	public void saveRole(){
		try {
			roleService.setHttpServletRequest(this.getHttpServletRequest());
			uumRole.setCreateDate(new Date());
			uumRole.setCreator((String)getHttpServletRequest().getSession().getAttribute(USER_CODE));
			roleService.saveRole(uumRole);
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
	 * 功能说明：查询所有角色
	 * @author jacobliang
	 * @param
	 * @param
	 * @return
	 * @throws 
	 * @time Sep 27, 2010 6:47:32 PM
	 */
	public String findAllRole(){
		try {
			roleList = this.roleService.findAllRole();
		}catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("findAllRole() occur error. ", e);
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：查询所有模块信息
	 * @author jacobliang
	 * @throws 
	 * @time Sep 27, 2010 
	 */
	public String findRoleByCon(){
		Map map = roleService.findRoleByCon(getStart(),getLimit(),this.uumRole.getRoleName());
		roleList = (List<UumRole>)map.get(BUSI_LIST);
		count = (Integer)map.get(TOTAL_PROPERTY);
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：根据角色ID查询某个角色
	 * @author jacobliang
	 * @param RoleId
	 * @return
	 * @throws 
	 * @time Sep 27, 2010 3:11:23 PM
	 */
	public String findRoleByRoleId(){
		try {
			uumRole = roleService.findRoleByRoleId(new Long(getModViewRecId()));
		}catch (NumberFormatException e) {
			e.printStackTrace();
			log.error("findRoleByRoleId() is not number. ", e);
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：查询角色名称是否重复
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Jul 27, 2010 11:11:39 AM
	 */
	public String findRoleByRoleName(){
		try {
			existRole = roleService.findRoleByRoleName(this.getHttpServletRequest().getParameter("roleName"));
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：修改角色
	 * @author jacobliang
	 * @param moduleId
	 * @return
	 * @throws 
	 * @time Jul 27, 2010 9:43:55 AM
	 */
	public void modifyRole(){
		try {
			roleService.setHttpServletRequest(this.getHttpServletRequest());
			roleService.modifyRole(uumRole);
			this.returnSuccess();
		}catch (ApplicationException e) {
			e.printStackTrace();
			this.returnFailur(e.toString());
		}catch (SystemException e) {
			e.printStackTrace();
			this.returnFailur(this.ERROR);
		}
	}
	
	/**
	 * 功能说明：显示隐藏角色
	 * @author jacobliang
	 * @param  disEnableFlag	停用启用标志
	 * @return
	 * @throws 
	 * @time Sep 22, 2010 1:29:34 PM
	 */
	public String disEnableRole(){
		try {
			roleService.setHttpServletRequest(this.getHttpServletRequest());
			roleService.disEnableRole(new Long(getModViewRecId()), disEnableFlag);
		} catch (NumberFormatException e) {
			e.printStackTrace();		
			log.error("disEnableRole() is not number. ", e);
			failMesg = this.ERROR;
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
	 * 功能说明：角色分配操作权限
	 * @author jacobliang
	 * @return
	 * @time Nov 1, 2010 4:28:48 PM
	 */
	public String roleAssignOptPrivilege(){
		Map moduleMemuMap = privilegeService.findSeledAUnseledModOptByOwnIdAPriType(uumRole.getRoleId(), OWNER_TYPE_ROL);
		assignedModMenuList = (List<SysplModuleMemu>)moduleMemuMap.get(ASSIGNED_MODOPTS);
		unassignedModMenuList = (List<SysplModuleMemu>)moduleMemuMap.get(UNASSIGNED_MODOPTS);
		privilegeScope = (String)moduleMemuMap.get(PRIVILEGE_SCOPE);
		roleRowPrivilege = privilegeService.findRowPrivilegeByRoleIdAPriType(uumRole.getRoleId());
		return this.SUCCESS;
	}
	/**
	 * 功能说明：保存为角色分配的模块操作权限
	 * @author jacobliang
	 * @return
	 * @time Nov 2, 2010 2:22:23 PM
	 */
	public String saveRoleOptPrivilege(){
		try {
			privilegeService.setHttpServletRequest(this.getHttpServletRequest());
			privilegeService.savePrivilege(modOptIdList, uumRole.getRoleId(), 
					PRIVILEGE_TYPE_OPT,OWNER_TYPE_ROL,privilegeScope);
		} catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	/**
	 * 功能说明：保存为角色分配的行权限
	 * @author jacobliang
	 * @return
	 * @time Nov 2, 2010 2:22:23 PM
	 */
	public String saveRoleRowPrivilege(){
		try {
			privilegeService.setHttpServletRequest(this.getHttpServletRequest());
			modOptIdList = new ArrayList<String>(){{add(privilegeScope);}}; 
			privilegeService.savePrivilege(modOptIdList, uumRole.getRoleId(), 
					PRIVILEGE_TYPE_ROW,OWNER_TYPE_ROL,privilegeScope);
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
	 * 功能说明：角色停用时检查其下是否有用户
	 * @author jacobliang
	 * @return
	 * @time Nov 10, 2010 2:39:23 PM
	 */
	public String roleDisableCheckUser(){
		/**
		 * 1 角色下有相应的用户,不能被停用
		 */
		int userTotal = roleUserService.findRoleUserRefTotalByRoleId(uumRole.getRoleId());//是否已经分配用户
		if(userTotal > 0){
			disEnableResultFlag = 1;
		}
		return this.SUCCESS;
	}
	/**
	 * 功能说明：
	 * @author jacobliang
	 * @return
	 * @time Jan 13, 2012 6:56:58 PM
	 */
	public String obtainRoleListByUserId(){
		long userId = (Long)getHttpServletRequest().getSession().getAttribute(USER_ID);
		uumRoleUserRefList = this.roleUserService.findRoleUserListByUserId(userId);
		return this.SUCCESS;
	}
	public UumRole getModel() {
		return uumRole;
	}

	public List<UumRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<UumRole> roleList) {
		this.roleList = roleList;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean getExistRole() {
		return existRole;
	}

	public void setExistRole(boolean existRole) {
		this.existRole = existRole;
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

	public String getDisEnableFlag() {
		return disEnableFlag;
	}

	public void setDisEnableFlag(String disEnableFlag) {
		this.disEnableFlag = disEnableFlag;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

	public List<SysplModuleMemu> getAssignedModMenuList() {
		return assignedModMenuList;
	}

	public void setAssignedModMenuList(List<SysplModuleMemu> assignedModMenuList) {
		this.assignedModMenuList = assignedModMenuList;
	}

	public List<SysplModuleMemu> getUnassignedModMenuList() {
		return unassignedModMenuList;
	}

	public void setUnassignedModMenuList(List<SysplModuleMemu> unassignedModMenuList) {
		this.unassignedModMenuList = unassignedModMenuList;
	}

	public List<String> getModOptIdList() {
		return modOptIdList;
	}

	public void setModOptIdList(List<String> modOptIdList) {
		this.modOptIdList = modOptIdList;
	}

	public int getDisEnableResultFlag() {
		return disEnableResultFlag;
	}

	public void setDisEnableResultFlag(int disEnableResultFlag) {
		this.disEnableResultFlag = disEnableResultFlag;
	}

	public void setRoleUserService(RoleUserService roleUserService) {
		this.roleUserService = roleUserService;
	}

	public List<UumRoleUserRef> getUumRoleUserRefList() {
		return uumRoleUserRefList;
	}

	public void setUumRoleUserRefList(List<UumRoleUserRef> uumRoleUserRefList) {
		this.uumRoleUserRefList = uumRoleUserRefList;
	}

	public String getPrivilegeScope() {
		return privilegeScope;
	}

	public void setPrivilegeScope(String privilegeScope) {
		this.privilegeScope = privilegeScope;
	}

	public String getRoleRowPrivilege() {
		return roleRowPrivilege;
	}

	public void setRoleRowPrivilege(String roleRowPrivilege) {
		this.roleRowPrivilege = roleRowPrivilege;
	}
	
}
