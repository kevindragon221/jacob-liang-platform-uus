/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 27, 2010 @2:56:23 PM
 */
package com.bhtec.dao.iface.uum.role;

import java.util.List;

import com.bhtec.dao.iface.BaseDao;
import com.bhtec.domain.pojo.uum.UumRoleUserRef;
import com.bhtec.exception.ApplicationException;

public interface RoleUserDao extends BaseDao {
	/**
	 * 功能说明：根据userId查询用户所拥有的角色
	 * @author jacobliang
	 * @param userId
	 * @return
	 * @time Oct 24, 2010 5:07:17 PM
	 */
	public List<UumRoleUserRef> findRoleUserListByUserId(long userId);
	/**
	 * 功能说明：根据userId删除用户所有的角色
	 * @author jacobliang
	 * @param userId
	 * @throws ApplicationException
	 * @time Oct 24, 2010 5:13:26 PM
	 */
	public void deleteRoleUserRef(long userId)throws ApplicationException;
	/**
	 * 功能说明：根据机构角色ID查询角色用户关系
	 * @author jacobliang
	 * @param orgRoleId
	 * @return
	 * @time Nov 5, 2010 8:30:57 PM
	 */
	public int findRoleUserRefById(long orgRoleId);
	/**
	 * 功能说明：根据角色ID查询其下分配的用户数量
	 * @author jacobliang
	 * @param roleId
	 * @return
	 * @time Nov 10, 2010 2:02:48 PM
	 */
	public int findRoleUserRefTotalByRoleId(long roleId);
	/**
	 * 功能说明：根据用户ID和机构角色ID查找用户的角色
	 * @author jacobliang
	 * @param userId
	 * @param orgRoleId
	 * @return
	 * @time Feb 28, 2012 10:09:49 AM
	 */
	public UumRoleUserRef findRoleUserByUserIdAOrgRoleId(long userId,long orgRoleId);
}
