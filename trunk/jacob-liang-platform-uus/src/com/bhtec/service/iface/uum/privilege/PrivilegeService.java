/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 27, 2010 @3:10:17 PM
 */
package com.bhtec.service.iface.uum.privilege;

import java.util.List;
import java.util.Map;

import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.domain.pojo.uum.UumOrgRoleRef;
import com.bhtec.domain.pojo.uum.UumRole;
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
	public void savePrivilege(List<Long> modOptIds,long ownerId,String privilegeType)throws ApplicationException;
	/**
	 * 功能说明：根据登录的用户或角色ID 和 标识查询所有模块操作
	 * @author jacobliang
	 * @param ownerId			用户或角色ID
	 * @param privilegeType		为用户或角色标识
	 * @return modMenuMap:1-list;2-map;3-map;4-map
	 * @time Nov 1, 2010 11:29:03 AM
	 */
	public Map findUserModOptPriForLogin(long ownerId,String privilegeType);
	/**
	 * 功能说明：根据用户或角色ID 和 标识查询所有已分配和未分配模块操作权限
	 * @author jacobliang
	 * @param ownerId			用户或角色ID
	 * @param privilegeType		为用户或角色标识
	 * @return	Map<String,List<SysplModuleMemu>> 1.list 已分配	2.list	未分配
	 * @time Nov 1, 2010 11:36:34 AM
	 */
	public Map<String,List<SysplModuleMemu>> findSeledAUnseledModOptByOwnIdAPriType(long ownerId,String privilegeType);
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
	public void deletePrivilege(long ownerId,String privilegeType)throws ApplicationException;
}
