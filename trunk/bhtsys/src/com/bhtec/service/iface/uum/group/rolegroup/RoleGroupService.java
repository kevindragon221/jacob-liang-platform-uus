/**
 *功能说明：角色组
 * @author jacobliang
 * @time Dec 22, 2010 @5:09:33 PM
 */
package com.bhtec.service.iface.uum.group.rolegroup;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bhtec.domain.pojo.uum.UumGroup;
import com.bhtec.domain.pojo.uum.UumGroupMember;
import com.bhtec.domain.pojo.uum.UumRole;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.BaseService;

public interface RoleGroupService extends BaseService {
	/**
	 * 功能说明：保存角色组
	 * @author jacobliang
	 * @param uumGroup		角色组对象
	 * @throws ApplicationException
	 * @time Jan 24, 2011 4:10:06 PM
	 */
	public void saveUumGroup(UumGroup uumGroup) throws ApplicationException;
	/**
	 * 功能说明：修改角色组
	 * @author jacobliang
	 * @param uumGroup 	角色组对象
	 * @throws ApplicationException
	 *@time Jan 24, 2011 4:10:06 PM
	 */
	public void modifyUumGroup(UumGroup uumGroup) throws ApplicationException;
	/**
	 * 功能说明：停用启用角色组
	 * @author jacobliang
	 * @param  disEnableFlag		停用启用标志
	 * @param  groupId			角色组ID
	 * @return boolean	true为停用启用成功，false不能停用
	 * @throws 
	 * @time Jan 24, 2011 4:10:06 PM
	 */
	public boolean disEnableGroup(long groupId,String disEnableFlag)throws ApplicationException;
	/**
	 * 功能说明：根据组名称查询组信息
	 * @author jacobliang
	 * @param start
	 * @param limit
	 * @param groupName		组名称
	 * @param uumRoleList	角色列表
	 * @return
	 * @time Jan 24, 2011 4:10:06 PM
	 */
	public Map findRoleGroupByCon(int start,int limit,String groupName,List<UumRole> uumRoleList);
	/**
	 * 功能说明：查询指定的大类代码是否存在
	 * @author jacobliang
	 * @param 	groupName	组名称
	 * @return
	 * @time Jan 24, 2011 4:10:06 PM
	 */
	public boolean findGroupNameIsExist(String groupName);
	/**
	 * 功能说明：根据机构ID查询角色信息
	 * @author jacobliang
	 * @param orgId		机构ID
	 * @param roleId	角色ID
	 * @return
	 * @time Jan 26, 2011 9:15:05 AM
	 */
	public List<UumRole> findRoleByOrgId(long organId)throws ApplicationException;
}
