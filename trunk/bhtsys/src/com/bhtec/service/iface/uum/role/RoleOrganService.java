/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 27, 2010 @3:10:17 PM
 */
package com.bhtec.service.iface.uum.role;

import java.util.List;
import java.util.Map;

import com.bhtec.domain.pojo.uum.UumOrgRoleRef;
import com.bhtec.domain.pojo.uum.UumRole;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.BaseService;

public interface RoleOrganService extends BaseService {
	/**
	 * 功能说明：根据机构ID和机构名称查询已分配的角色
	 * @author jacobliang
	 * @param start		翻页开始ID
	 * @param limit		每页记录数
	 * @param orgId		机构ID
	 * @param orgName	查询机构名称
	 * @return
	 * @time Sep 28, 2010 10:22:02 AM
	 */
	public Map findSelectedRoleByOrgId(int start,int limit,long orgId,String orgName);
	/**
	 * 功能说明：根据机构ID查询未分配的角色
	 * @author jacobliang
	 * @param start		翻页开始ID
	 * @param limit		每页记录数
	 * @param orgId		机构ID
	 * @return
	 * @time Sep 28, 2010 10:27:13 AM
	 */
	public Map findUnselectedRoleByOrgId(int start,int limit,long orgId);
	/**
	 * 功能说明：保存为机构分配的角色
	 * @author jacobliang
	 * @param orgRoleId	机构角色关系ID
	 * @param orgId		机构ID
	 * @throws ApplicationException
	 * @time Sep 28, 2010 10:30:16 AM
	 */
	public void saveOrganRoleRef(long orgId,List<Long> roleIds)throws ApplicationException;
	/**
	 * 功能说明：根据机构ID和角色ID删除机构下的角色
	 * @author jacobliang
	 * @param orgId		机构ID
	 * @param roleId	角色ID
	 * @throws ApplicationException
	 * @return boolean true 角色下有用户 false 角色下无用户
	 * @time Sep 28, 2010 10:32:08 AM
	 */
	public boolean deleteOrganRoleRef(long orgId,List<Long> roleId)throws ApplicationException;
	/**
	 * 功能说明：根据机构id查询机构角色关系
	 * @author jacobliang
	 * @param orgId	机构ID
	 * @return
	 * @time Sep 30, 2010 6:55:21 PM
	 */
	public List<UumOrgRoleRef> findRoleOrganListByOrgId(long orgId);
	/**
	 * 功能说明：根据机构角色ID查询机构角色
	 * @author jacobliang
	 * @param orgRoleId
	 * @return UumOrgRoleRef 机构角色关系记录
	 * @time Oct 11, 2010 8:47:58 PM
	 */
	public UumOrgRoleRef findUumOrgRoleRefById(long orgRoleId);
	/**
	 * 功能说明：根据机构ID查询其下的角色数量
	 * @author jacobliang
	 * @param organId	机构ID
	 * @return	int 机构下的角色数量
	 * @time Nov 10, 2010 10:32:41 AM
	 */
	public int findUumOrgRoleRefTotalByOrgId(long organId);
	/**
	 * 功能说明：根据角色ID删除机构角色关系记录，为停用角色所用
	 * @author jacobliang
	 * @param roleId	角色ID
	 * @time Nov 10, 2010 2:27:47 PM
	 */
	public void deleteOrgRoleRefByRoleId(long roleId);
	/**
	 * 功能说明：根据机构ID和角色ID查询机构角色
	 * @author jacobliang
	 * @param orgId		机构ID
	 * @param roleId	角色ID
	 * @throws ApplicationException
	 * @time Sep 28, 2010 10:32:08 AM
	 */
	public UumOrgRoleRef findOrganRoleRefByOrgRolId(long organId,long roleId)throws ApplicationException;
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
