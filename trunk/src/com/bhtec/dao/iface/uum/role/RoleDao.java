/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 27, 2010 @2:56:23 PM
 */
package com.bhtec.dao.iface.uum.role;

import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.BaseDao;
import com.bhtec.domain.pojo.uum.UumRole;

public interface RoleDao extends BaseDao {
	/**
	 * 功能说明：根据角色名称查询角色
	 * @author jacobliang
	 * @param
	 * @param
	 * @param roleName 角色名称
	 * @return
	 * @throws 
	 * @time Sep 27, 2010 8:39:36 PM
	 */
	public int findRoleCountByRoleName(String roleName);
	/**
	 * 功能说明：根据条件查询角色
	 * @author jacobliang
	 * @param start	开始页
	 * @param limit	每页记录数
	 * @param roleName角色名称
	 * @return
	 * @throws 
	 * @time Sep 27, 2010 3:02:42 PM
	 */
	public Map findRoleByCon(int start,int limit,String roleName);
	/**
	 * 功能说明：查询所有角色信息
	 * @author jacobliang
	 * @throws 
	 * @time Sep 27, 2010 3:02:42 PM
	 */
	public List<UumRole> findAllRole();
}
