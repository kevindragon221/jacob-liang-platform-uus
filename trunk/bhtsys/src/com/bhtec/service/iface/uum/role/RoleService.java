/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 27, 2010 @3:10:17 PM
 */
package com.bhtec.service.iface.uum.role;

import java.util.List;
import java.util.Map;

import com.bhtec.domain.pojo.uum.UumRole;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.BaseService;

public interface RoleService extends BaseService {
	/**
	 * 功能说明：角色保存
	 * @author jacobliang
	 * @param uumRole	角色对象
	 * @throws ApplicationException
	 * @throws 
	 * @time Sep 27, 2010 3:11:23 PM
	 */
	public void saveRole(UumRole uumRole) throws ApplicationException;
	/**
	 * 功能说明：停用启用角色
	 * @author jacobliang
	 * @param  disEnableFlag	停用启用标志
	 * @param  roleId			角色ID
	 * @throws ApplicationException
	 * @time Sep 27, 2010 3:11:23 PM
	 */
	public void disEnableRole(long roleId,String disEnableFlag)throws ApplicationException;
	
	/**
	 * 功能说明：根据角色ID查询某个角色
	 * @author jacobliang
	 * @param 	RoleId	角色ID
	 * @return	UumRole 角色对象
	 * @throws 
	 * @time Sep 27, 2010 3:11:23 PM
	 */
	public UumRole findRoleByRoleId(long roleId);
	/**
	 * 功能说明：修改角色
	 * @author jacobliang
	 * @param UumRole	角色对象
	 * @return
	 * @throws 
	 * @time Sep 27, 2010 3:11:23 PM
	 */
	public void modifyRole(UumRole uumRole)throws ApplicationException;
	/**
	 * 功能说明：查询角色名称是否重复
	 * @author jacobliang
	 * @param	roleName	角色名称
	 * @return  boolean  	true 存在 false不存在
	 * @throws 
	 * @time Sep 27, 2010 3:11:23 PM
	 */
	public boolean findRoleByRoleName(String roleName);
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
	public Map findRoleByCon(int start,int limit,String roleName);
	/**
	 * 功能说明：查询所有角色信息
	 * @author jacobliang
	 * @return	List<UumRole>	所有角色列表
	 * @throws 
	 * @time Sep 27, 2010 3:11:23 PM
	 */
	public List<UumRole> findAllRole();
}
