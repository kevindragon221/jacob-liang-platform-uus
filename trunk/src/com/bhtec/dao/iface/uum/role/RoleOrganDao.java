/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 27, 2010 @2:56:23 PM
 */
package com.bhtec.dao.iface.uum.role;

import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.BaseDao;
import com.bhtec.domain.pojo.uum.UumOrgRoleRef;
import com.bhtec.exception.ApplicationException;

public interface RoleOrganDao extends BaseDao {
	/**
	 * 功能说明：根据机构ID查询已分配的角色
	 * @author jacobliang
	 * @param orgId		机构ID
	 * @return
	 * @time Sep 28, 2010 10:22:02 AM
	 */
	public Map findSelectedRoleByOrgId(int start,int limit,long orgId,String orgName);
	/**
	 * 功能说明：根据机构ID查询已分配的角色
	 * @author jacobliang
	 * @param orgId		机构ID
	 * @return
	 * @time Sep 28, 2010 10:22:02 AM
	 */
	public List<UumOrgRoleRef> findSelectedRoleByOrgId(long orgId);
	/**
	 * 功能说明：根据机构ID和角色ID查询机构角色
	 * @author jacobliang
	 * @param orgId		机构ID
	 * @param roleId	角色ID
	 * @throws ApplicationException
	 * @time Sep 28, 2010 10:32:08 AM
	 */
	public UumOrgRoleRef findOrganRoleRefByOrgRolId(long orgId,long roleId)throws ApplicationException;
	/**
	 * 功能说明：根据机构id查询机构角色关系
	 * @author jacobliang
	 * @param orgId
	 * @return
	 * @time Sep 30, 2010 6:55:21 PM
	 */
	public List<UumOrgRoleRef> findRoleOrganListByOrgId(long orgId);
	/**
	 * 功能说明：根据机构ID查询其下的角色数量
	 * @author jacobliang
	 * @param organId
	 * @return
	 * @time Nov 10, 2010 10:32:41 AM
	 */
	public int findUumOrgRoleRefTotalByOrgId(long organId);
	/**
	 * 功能说明：根据角色ID删除机构角色关系记录，为停用角色所用
	 * @author jacobliang
	 * @param roleId
	 * @time Nov 10, 2010 2:27:47 PM
	 */
	public void deleteOrgRoleRefByRoleId(long roleId);
	/**
	 * 功能说明：根据多个机构角色ID查询机构角色信息
	 * @author jacobliang
	 * @param orgRoleIds
	 * @return
	 * @time Feb 14, 2012 5:19:59 PM
	 */
	public List<UumOrgRoleRef> findUumOrgRoleRefByOrgRoleIds(String orgRoleIds);
	/**
	 * 功能说明：根据角色ID查询所有机构角色关系ID
	 * @author jacobliang
	 * @param roleId
	 * @return
	 * @time Mar 2, 2012 6:17:03 PM
	 */
	public List findUumOrgRoleRefByRoleId(String roleId);
}
