/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 23, 2010 @3:22:30 PM
 */
package com.bhtec.service.impl.uum.role;

import static com.bhtec.common.constant.Common.*;
import static com.bhtec.common.constant.Common.ENABLE;
import static com.bhtec.common.constant.Common.LOG_LEVEL_FIRST;
import static com.bhtec.common.constant.Common.LOG_LEVEL_SECOND;
import static com.bhtec.common.constant.Common.MODIFY_OPT;
import static com.bhtec.common.constant.Common.OWNER_TYPE_USR;
import static com.bhtec.common.constant.Common.SAVE_OPT;
import static com.bhtec.common.constant.Common.SPLIT;
import static com.bhtec.common.constant.ServiceVariable.DISABLE_DIS;
import static com.bhtec.common.constant.ServiceVariable.ENABLE_DIS;
import static com.bhtec.common.constant.ServiceVariable.ROLE_MGR;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bhtec.dao.iface.uum.role.RoleDao;
import com.bhtec.domain.pojo.uum.UumRole;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.uum.privilege.PrivilegeService;
import com.bhtec.service.iface.uum.role.RoleOrganService;
import com.bhtec.service.iface.uum.role.RoleService;
import com.bhtec.service.iface.uum.role.RoleUserService;
import com.bhtec.service.impl.BaseServiceImpl;

public class RoleServiceImpl extends BaseServiceImpl implements RoleService {
	Logger log = Logger.getLogger(this.getClass());
	private RoleDao roleDao;
	private RoleOrganService roleOrganService;
	private PrivilegeService privilegeService;
	/**
	 * 功能说明：停用启用角色
	 * @author jacobliang
	 * @param  disEnableFlag	停用启用标志
	 * @param  roleId			角色ID
	 * @throws ApplicationException
	 * @time Sep 27, 2010 3:11:23 PM
	 */
	public void disEnableRole(long roleId, String disEnableFlag)
			throws ApplicationException {
		if(DISABLE.equals(disEnableFlag)){
			roleOrganService.deleteOrgRoleRefByRoleId(roleId);//根据角色ID删除机构角色关系记录，为停用角色所用
			privilegeService.deletePrivilegeByOwnerIdType(roleId, OWNER_TYPE_ROL);//删除用户所拥有操作、行权限
			UumRole uumRole = this.findRoleByRoleId(roleId);
			uumRole.setStatus(DISABLE);
			this.writeLog(uumRole, LOG_LEVEL_SECOND, DISABLE_DIS);
		}else{
			UumRole uumRole = this.findRoleByRoleId(roleId);
			uumRole.setStatus(ENABLE);
			this.writeLog(uumRole, LOG_LEVEL_SECOND, ENABLE_DIS);
		}
	}
	/**
	 * 功能说明：查询所有角色信息
	 * @author jacobliang
	 * @return	List<UumRole>	所有角色列表
	 * @throws 
	 * @time Sep 27, 2010 3:11:23 PM
	 */
	public List<UumRole> findAllRole() {
		return roleDao.findAllRole();
	}

	/**
	 * 功能说明：根据条件分页查询角色
	 * @author jacobliang
	 * @param	start	开始记录数
	 * @param	limit	每页显示条数
	 * @param	roleName 角色名称
	 * @return	Map 1.list 2.count
	 * @throws 
	 * @time Sep 27, 2010 3:11:23 PM
	 */
	public Map findRoleByCon(int start, int limit,String orgName) {
		log.debug("start="+ start+" limit="+ limit+" orgName"+ orgName);
		return roleDao.findRoleByCon(start, limit,  orgName);
	}

	/**
	 * 功能说明：根据角色ID查询某个角色
	 * @author jacobliang
	 * @param 	RoleId	角色ID
	 * @return	UumRole 角色对象
	 * @throws 
	 * @time Sep 27, 2010 3:11:23 PM
	 */
	public UumRole findRoleByRoleId(long roleId) {
		log.debug("will find Role id is "+roleId);
		UumRole uumRole = (UumRole)roleDao
						.getPojoById("com.bhtec.domain.pojo.uum.UumRole", roleId);
		return uumRole;
	}

	/**
	 * 功能说明：查询角色名称是否重复
	 * @author jacobliang
	 * @param	roleName	角色名称
	 * @return  boolean  	true 存在 false不存在
	 * @throws 
	 * @time Sep 27, 2010 3:11:23 PM
	 */
	public boolean findRoleByRoleName(String roleName) {
		int totalProperty = roleDao.findRoleCountByRoleName(roleName);
		log.debug("find Role total is "+totalProperty);
		if(totalProperty > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 功能说明：修改角色
	 * @author jacobliang
	 * @param UumRole	角色对象
	 * @return
	 * @throws 
	 * @time Sep 27, 2010 3:11:23 PM
	 */
	public void modifyRole(UumRole uumRole) throws ApplicationException {
		roleDao.update(uumRole);
		this.writeLog(uumRole, LOG_LEVEL_SECOND, MODIFY_OPT);
	}
	/**
	 * 功能说明：角色保存
	 * @author jacobliang
	 * @param uumRole	角色对象
	 * @throws ApplicationException
	 * @throws 
	 * @time Sep 27, 2010 3:11:23 PM
	 */
	public void saveRole(UumRole uumRole) throws ApplicationException {
		this.roleDao.save(uumRole);
		this.writeLog(uumRole, LOG_LEVEL_FIRST, SAVE_OPT);
	}

	/**
	 * 功能说明：公用模块写日志
	 * @author jacobliang
	 * @param UumRole	模块对象
	 * @throws ApplicationException
	 * @throws 
	 * @time Sep 16, 2010 8:55:43 PM
	 */
	private void writeLog(UumRole uumRole,String logLevel,String opt)throws ApplicationException{
		StringBuffer logContent = new StringBuffer();
		logContent.append(uumRole.getRoleName()+SPLIT);
		saveLog(logLevel, ROLE_MGR,opt,logContent.toString(), uumRole.getRoleId()+"");
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setRoleOrganService(RoleOrganService roleOrganService) {
		this.roleOrganService = roleOrganService;
	}
	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

}
