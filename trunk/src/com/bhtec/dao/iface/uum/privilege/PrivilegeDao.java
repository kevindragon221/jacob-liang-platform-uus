package com.bhtec.dao.iface.uum.privilege;

import java.util.List;

import com.bhtec.dao.iface.BaseDao;
import com.bhtec.domain.pojo.uum.UumPrivilege;
import com.bhtec.exception.ApplicationException;

public interface PrivilegeDao extends BaseDao {
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
	 * 功能说明：根据用户或角色ID 和 标识查询所有模块操作
	 * @author jacobliang
	 * @param ownerId			用户或角色ID
	 * @param privilegeType		为用户或角色标识
	 * @return	List<SysplModOptRef>	模块列表	
	 * @throws ApplicationException
	 * @time Nov 1, 2010 10:46:53 AM
	 */
	public List<UumPrivilege> findPrivilegeByOwnIdAPriType(String ownerId,String privilegeType,String ownerType);
	/**
	 * 功能说明：根据用户ID查询用户特殊操作权限
	 * @author jacobliang
	 * @param userId
	 * @return
	 * @time Nov 3, 2010 8:18:22 PM
	 */
	public int findUserSpecialPrivilegeByUserId(long userId);
	/**
	 * 功能说明：删除模块操作关系时同时也删除模块操作权限
	 * @author jacobliang
	 * @param modOptRefId
	 * @time Feb 8, 2012 1:09:22 PM
	 */
	public void deletePrivilegeByModOptId(String modOptRefId);
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
	public void deleteRowPriByResource(String resourceId)throws ApplicationException ;
}
