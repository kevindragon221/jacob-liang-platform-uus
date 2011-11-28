package com.bhtec.dao.iface.uum.privilege;

import java.util.List;

import com.bhtec.dao.iface.BaseDao;
import com.bhtec.domain.pojo.platform.SysplModOptRef;
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
	public void deletePrivilege(long ownerId,String privilegeType)throws ApplicationException;
	/**
	 * 功能说明：根据用户或角色ID 和 标识查询所有模块操作
	 * @author jacobliang
	 * @param ownerId			用户或角色ID
	 * @param privilegeType		为用户或角色标识
	 * @return	List<SysplModOptRef>	模块列表	
	 * @throws ApplicationException
	 * @time Nov 1, 2010 10:46:53 AM
	 */
	public List<SysplModOptRef> findPrivilegeByOwnIdAPriType(long ownerId,String privilegeType);
	/**
	 * 功能说明：根据用户ID查询用户特殊操作权限
	 * @author jacobliang
	 * @param userId
	 * @return
	 * @time Nov 3, 2010 8:18:22 PM
	 */
	public int findUserSpecialPrivilegeByUserId(long userId);
}
