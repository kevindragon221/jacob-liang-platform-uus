/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 27, 2010 @3:10:17 PM
 */
package com.bhtec.service.iface.uum.role;

import java.util.List;
import java.util.Map;

import com.bhtec.domain.pojo.uum.UumRoleUserRef;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.BaseService;

public interface RoleUserService extends BaseService {
	/**
	 * 功能说明：保存角色用户关系记录
	 * @author jacobliang
	 * @param userId		用户id
	 * @param orgRoleId		机构角色关系id
	 * @param dfaultOrgRoleId		默认机构角色ID
	 * @param orgRoleUserid		机构角色用户ID
	 * @throws ApplicationException
	 * @time Oct 9, 2010 3:47:00 PM
	 */
	public void saveRoleUserRef(long userId,List<Long> orgRoleId,long dfaultOrgRoleId)throws ApplicationException;
	/**
	 * 功能说明：删除角色用户关系记录
	 * @author jacobliang
	 * @param userId
	 * @throws ApplicationException
	 * @time Oct 9, 2010 3:51:28 PM
	 */
	public void deleteRoleUserRef(long userId)throws ApplicationException;
	/**
	 * 功能说明：根据用户ID查找用户的所有角色
	 * @author jacobliang
	 * @param 	userId					用户主键ID
	 * @return	List<UumRoleUserRef>	用户角色关系记录
	 * @time Oct 9, 2010 3:52:47 PM
	 */
	public List<UumRoleUserRef> findRoleUserListByUserId(long userId);
	/**
	 * 功能说明：保存为用户分配的角色
	 * @author jacobliang
	 * @param userId			用户ID
	 * @param orgRoleIds		机构角色字符串以逗号分隔
	 * @param defaultOrgRoleId	默认角色
	 * @throws ApplicationException
	 * @time Oct 25, 2010 7:43:24 PM
	 */
	public void saveRoleUser(long userId,String orgRoleIds,long defaultOrgRoleId)throws ApplicationException;
	/**
	 * 功能说明：根据机构角色ID查询角色用户关系
	 * @author jacobliang
	 * @param orgRoleUserId
	 * @return
	 * @time Nov 5, 2010 8:30:57 PM
	 */
	public int findRoleUserRefById(long orgRoleUserId);
	/**
	 * 功能说明：根据角色ID查询其下分配的用户数量
	 * @author jacobliang
	 * @param roleId	角色ID
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
