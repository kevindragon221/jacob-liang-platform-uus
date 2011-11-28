/**
 *功能说明：用户组
 * @author jacobliang
 * @time Dec 22, 2010 @5:09:33 PM
 */
package com.bhtec.service.iface.uum.group.usergroup;

import java.util.List;
import java.util.Map;

import com.bhtec.domain.pojo.uum.UumGroup;
import com.bhtec.domain.pojo.uum.UumGroupMember;
import com.bhtec.domain.pojo.uum.UumUser;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.BaseService;

public interface UserGroupService extends BaseService {
	/**
	 * 功能说明：保存用户组
	 * @author jacobliang
	 * @param uumGroup		用户组对象
	 * @throws ApplicationException
	 * @time Jan 24, 2011 4:10:06 PM
	 */
	public void saveUumGroup(UumGroup uumGroup) throws ApplicationException;
	/**
	 * 功能说明：修改用户组
	 * @author jacobliang
	 * @param uumGroup 	用户组对象
	 * @throws ApplicationException
	 *@time Jan 24, 2011 4:10:06 PM
	 */
	public void modifyUumGroup(UumGroup uumGroup) throws ApplicationException;
	/**
	 * 功能说明：根据用户组ID查询用户组
	 * @author jacobliang
	 * @param  groupId	用户组ID
	 * @return
	 * @time Jan 24, 2011 4:10:06 PM
	 */
	public UumGroup findUumGroupById(long groupId);
	/**
	 * 功能说明：根据组ID查询所有组成员
	 * @author jacobliang
	 * @param 	groupId		组成ID
	 * @return
	 * @time Jan 24, 2011 4:10:06 PM
	 */
	public List<UumGroupMember> findGroupByGroupId(long groupId);
	/**
	 * 功能说明：停用启用用户组
	 * @author jacobliang
	 * @param  disEnableFlag		停用启用标志
	 * @param  groupId			用户组ID
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
	 * @param uumUserList	用户列表
	 * @return
	 * @time Jan 24, 2011 4:10:06 PM
	 */
	public Map findUserGroupByCon(int start,int limit,String groupName,List<UumUser> uumUserList);
	/**
	 * 功能说明：查询指定的大类代码是否存在
	 * @author jacobliang
	 * @param 	groupName	组名称
	 * @param 	groupType	组类型 同普通组共用方法
	 * @return
	 * @time Jan 24, 2011 4:10:06 PM
	 */
	public boolean findGroupNameIsExist(String groupName,String groupType);
	/**
	 * 功能说明：根据机构ID角色ID和用户ID查询用户信息
	 * @author jacobliang
	 * @param orgId		机构ID
	 * @param roleId	角色ID
	 * @return
	 * @time Jan 26, 2011 9:15:05 AM
	 */
	public List<UumUser> findUserByOrgRolId(long organId,long roleId)throws ApplicationException;
	/**
	 * 功能说明：根据组ID修改组状态
	 * @author jacobliang
	 * @param groupId
	 * @param status
	 * @time Jan 27, 2011 3:10:53 PM
	 */
	public void modifyGroupStatus(long groupId,String status);
}
