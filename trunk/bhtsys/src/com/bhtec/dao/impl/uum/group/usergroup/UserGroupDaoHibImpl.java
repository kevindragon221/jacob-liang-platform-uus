/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:03:17 PM
 */
package com.bhtec.dao.impl.uum.group.usergroup;

import static com.bhtec.common.constant.ActionVariable.USER_GROUP;
import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.tools.UtilTools.isNullOrEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bhtec.dao.iface.uum.group.usergroup.UserGroupDao;
import com.bhtec.dao.impl.BaseDaoHibImpl;
import com.bhtec.domain.pojo.uum.UumGroup;
import com.bhtec.domain.pojo.uum.UumGroupMember;

public class UserGroupDaoHibImpl extends BaseDaoHibImpl implements UserGroupDao {
	
	/**
	 * 功能说明：根据组名称查询组信息
	 * @author jacobliang
	 * @param start
	 * @param limit
	 * @param groupName	组名称
	 * @param groupType	组类型
	 * @return
	 * @time Dec 21, 2010 8:37:29 PM
	 */
	public Map findUserGroupByCon(int start,int limit,String groupName,String groupType){
		String queryString = "from UumGroup ug where ug.groupType = '"+groupType+"'";
		List params = new ArrayList();
		if(!isNullOrEmpty(groupName)){
			queryString += " and ug.groupName like ?";
			params.add("%"+groupName+"%");
		}
		
		String countSql = "select count(*) " + queryString;
		queryString += " order by ug.groupId desc"; 
		List<UumGroup> limitModuleList =  this.findByHqlWithPagination(start, limit, queryString,params);//分页
		int totalProperty = this.getRowCount(countSql,params);//总记录数
		Map map = new HashMap();
		map.put(TOTAL_PROPERTY, totalProperty);
		map.put(BUSI_LIST, limitModuleList);
		return map;
	}
	
	/**
	 * 功能说明：根据组ID查询所有组成员
	 * @author jacobliang
	 * @param 	groupId		组成ID
	 * @return
	 * @time Dec 21, 2010 8:39:15 PM
	 */
	public List<UumGroupMember> findGroupByGroupId(long groupId){
		String queryString = "from UumGroup ug " +
							 "where ug.groupType = 'userGroup' " +
							 "and ug.groupId = " +groupId;
		
		UumGroup uumGroup = (UumGroup)this.findByProperty(queryString).get(0);
		Iterator iterator = uumGroup.getUumGroupMembers().iterator();
		List<UumGroupMember> uumGroupMemberList = new ArrayList<UumGroupMember>();
		while(iterator.hasNext()){
			UumGroupMember uumGroupMember = (UumGroupMember)iterator.next();
			uumGroupMemberList.add(uumGroupMember);
		}
		Collections.sort(uumGroupMemberList, new UumGroupMember().getUumGroupMemberCompInst());
		return uumGroupMemberList;
	}
	
	
	/**
	 * 功能说明：根据组名称查询组数量
	 * @author jacobliang
	 * @param groupName	组名称
	 * @param groupType	组类型
	 * @return
	 * @time Dec 22, 2010 9:42:55 AM
	 */
	public int findGroupByGroupName(String groupName,String groupType){
		String queryString = "select count(*) from UumGroup ug " +
		   					 "where ug.groupName = ? and ug.groupType = ?";
		List params = new ArrayList();
		params.add(groupName);
		params.add(groupType);
		return this.getRowCount(queryString,params);
	}
	
	/**
	 * 功能说明：根据组ID删除组成员
	 * @author jacobliang
	 * @param groupId
	 * @time Jan 27, 2011 2:04:47 PM
	 */
	public void deleteGroupMemberByGroupId(long groupId){
		String hqlString = "delete from UumGroupMember ugm " +
					 "where ugm.uumGroup.groupId = "+groupId;
		this.excuteHql(hqlString);
	}
	/**
	 * 功能说明：根据组ID修改组状态
	 * @author jacobliang
	 * @param groupId
	 * @param status
	 * @time Jan 27, 2011 3:10:53 PM
	 */
	public void modifyGroupStatus(long groupId,String status){
		String hqlString = "update UumGroup ug " +
		 				   "set ug.status = '"+status+"' " +
		 				   "where ug.groupId="+groupId;
		this.excuteHql(hqlString);
	}
}
