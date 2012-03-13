/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:00:17 PM
 */
package com.bhtec.dao.iface.uum.group.rolegroup;

import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.BaseDao;
import com.bhtec.domain.pojo.uum.UumGroupMember;

public interface RoleGroupDao extends BaseDao {
	/**
	 * 功能说明：根据组名称查询组信息
	 * @author jacobliang
	 * @param start
	 * @param limit
	 * @param groupName	组名称
	 * @return
	 * @time Dec 21, 2010 8:37:29 PM
	 */
	public Map findRoleGroupByCon(int start,int limit,String groupName);
	/**
	 * 功能说明：根据组ID查询所有组成员
	 * @author jacobliang
	 * @param 	groupId		组成ID
	 * @return
	 * @time Dec 21, 2010 8:39:15 PM
	 */
	public List<UumGroupMember> findGroupByGroupId(long groupId);
	/**
	 * 功能说明：根据组名称查询组数量
	 * @author jacobliang
	 * @param groupName	组名称
	 * @return
	 * @time Dec 22, 2010 9:42:55 AM
	 */
	public int findGroupByGroupName(String groupName);
	/**
	 * 功能说明：根据组ID删除组成员
	 * @author jacobliang
	 * @param groupId
	 * @time Jan 27, 2011 2:04:47 PM
	 */
	public void deleteGroupMemberByGroupId(long groupId);
	/**
	 * 功能说明：根据组ID修改组状态
	 * @author jacobliang
	 * @param groupId
	 * @param status
	 * @time Jan 27, 2011 3:10:53 PM
	 */
	public void modifyGroupStatus(long groupId,String status);
}
