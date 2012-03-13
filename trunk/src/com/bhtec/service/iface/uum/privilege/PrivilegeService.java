/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 27, 2010 @3:10:17 PM
 */
package com.bhtec.service.iface.uum.privilege;

import java.util.List;
import java.util.Map;

import com.bhtec.domain.pojo.platform.SysplModOptRef;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.domain.pojo.uum.UumOrgRoleRef;
import com.bhtec.domain.pojo.uum.UumRole;
import com.bhtec.domain.pojo.uum.UumUser;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.BaseService;

public interface PrivilegeService extends BaseService {
	/**
	 * 功能说明：为角色或用户保存分配权限
	 * @author jacobliang
	 * @param modOptIds			模块操作关系IDS
	 * @param ownerId			用户或角色ID
	 * @param privilegeType		为用户或角色标识
	 * @throws ApplicationException
	 * @time Nov 1, 2010 10:26:30 AM
	 */
	public void savePrivilege(List<String> modOptIds,long ownerId,String privilegeType,
								String ownerType,String privilegeScope)throws ApplicationException;
	/**
	 * 功能说明：根据登录的用户或角色ID 和 标识查询所有模块操作
	 * @author jacobliang
	 * @param ownerType		    为用户或角色标识
	 * @return modMenuMap:1-list;2-map;3-map;4-map
	 * @time Nov 1, 2010 11:29:03 AM
	 */
	public Map findUserPrivilegeForLogin(long userId,long roleId,long organId,long organRoleId);
	/**
	 * 功能说明：根据用户或角色ID 和 标识查询所有已分配和未分配模块操作权限
	 * @author jacobliang
	 * @param ownerId			用户或角色ID
	 * @param ownerType		为用户或角色标识
	 * @return	Map<String,List<SysplModuleMemu>> 1.list 已分配	2.list	未分配
	 * @time Nov 1, 2010 11:36:34 AM
	 */
	public Map findSeledAUnseledModOptByOwnIdAPriType(long ownerId,String ownerType);
	/**
	 * 功能说明：查询判断用户是否有特殊操作权限
	 * @author jacobliang
	 * @param userId		用户或角色ID
	 * @return true 有 false 无
	 * @time Nov 3, 2010 8:16:42 PM
	 */
	public boolean findUserHasSpecialPrivilege(long userId);
	/**
	 * 功能说明：根据用户或角色ID 和 标识删除已经分配的模块操作权限
	 * @author jacobliang
	 * @param ownerId			用户或角色ID
	 * @param privilegeType		为用户或角色标识
	 * @throws ApplicationException
	 * @time Nov 1, 2010 10:31:20 AM
	 */
	public void deletePrivilege(long ownerId,String privilegeType,String ownerType)throws ApplicationException;
	/**
	 * 功能说明：删除模块操作关系时同时也删除模块操作权限
	 * @author jacobliang
	 * @param modOptRefId
	 * @time Feb 8, 2012 1:09:22 PM
	 */
	public void deletePrivilegeByModOptId(String modOptRefId);
	/**
	 * 功能说明：查询角色的行权限
	 * @author jacobliang
	 * @param roleId			角色ID
	 * @return	String
	 * @time Nov 1, 2010 11:36:34 AM
	 */
	public String findRowPrivilegeByRoleIdAPriType(long roleId);
	/**
	 * 功能说明：根据用户名模糊查询用户信息
	 * @author jacobliang
	 * @param userName
	 * @return
	 * @time Feb 14, 2012 4:38:16 PM
	 */
	public List<UumUser> findAllUumUserByUserName(String userName);
	/**
	 * 功能说明：根据用户或角色ID 和 标识查询所有已分配和未分配模块操作权限
	 * @author jacobliang
	 * @param userId			用户ID
	 * @return	1.list 已分配 	2.list	权限范围
	 * @time Nov 1, 2010 11:36:34 AM
	 */
	public Map findSeledRowPrivilegeByUserId(long userId);
	/**
	 * 功能说明：过滤所有的模块操作，构成模块树，并获得相应模块下的操作
	 * @author jacobliang
	 * @param sysplModOptRefList	系统模块操作关系列表
	 * @param isTreeFlag			是否为树标志
	 * @return
	 * @time Nov 1, 2010 1:44:44 PM
	 */
	public Map findFilterModOpt(List<SysplModOptRef> sysplModOptRefList,boolean isTreeFlag);
	/**
	 * 功能说明：查询代理用户的权限
	 * @author jacobliang
	 * @param userId
	 * @param roleId
	 * @param organId
	 * @param organRoleId
	 * @param privilegeIdList
	 * @return
	 * @time Feb 24, 2012 11:39:43 AM
	 */
	public Map findUserPrivilegeForAgentLogin(long userId,long roleId,long organId,
											  long organRoleId,List privilegeIdList);
	/**
	 * 功能说明：根据拥有者ID和拥有者类型删除用户或角色的所有权限
	 * @author jacobliang
	 * @param ownerId	拥有者ID
	 * @param ownerType 拥有者类型
	 * @throws ApplicationException
	 * @time Feb 29, 2012 2:21:45 PM
	 */
	public void deletePrivilegeByOwnerIdType(long ownerId,String ownerType)throws ApplicationException;
	/**
	 * 功能说明：停用机构时根据机构ID删除行权限
	 * @author jacobliang
	 * @param resourceId
	 * @throws ApplicationException
	 * @time Mar 2, 2012 4:13:20 PM
	 */
	public void deleteRowPriByResource(String resourceId)throws ApplicationException;
}
