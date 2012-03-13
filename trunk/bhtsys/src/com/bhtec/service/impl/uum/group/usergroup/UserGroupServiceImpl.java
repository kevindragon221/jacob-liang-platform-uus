/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:30:57 PM
 */
package com.bhtec.service.impl.uum.group.usergroup;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.DISABLE;
import static com.bhtec.common.constant.Common.ENABLE;
import static com.bhtec.common.constant.Common.LOG_LEVEL_FIRST;
import static com.bhtec.common.constant.Common.LOG_LEVEL_SECOND;
import static com.bhtec.common.constant.Common.MODIFY_OPT;
import static com.bhtec.common.constant.Common.SAVE_OPT;
import static com.bhtec.common.constant.Common.SPLIT;
import static com.bhtec.common.constant.Common.USER_GROUP;
import static com.bhtec.common.constant.ServiceVariable.DISABLE_DIS;
import static com.bhtec.common.constant.ServiceVariable.ENABLE_DIS;
import static com.bhtec.common.constant.ServiceVariable.USER_GROUP_MGR;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.bhtec.dao.iface.uum.group.usergroup.UserGroupDao;
import com.bhtec.domain.pojo.uum.UumGroup;
import com.bhtec.domain.pojo.uum.UumGroupMember;
import com.bhtec.domain.pojo.uum.UumOrgRoleRef;
import com.bhtec.domain.pojo.uum.UumRoleUserRef;
import com.bhtec.domain.pojo.uum.UumUser;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.uum.group.usergroup.UserGroupService;
import com.bhtec.service.iface.uum.role.RoleOrganService;
import com.bhtec.service.iface.uum.user.UserService;
import com.bhtec.service.impl.BaseServiceImpl;

public class UserGroupServiceImpl extends BaseServiceImpl implements UserGroupService {
	Logger log = Logger.getLogger(this.getClass());
	private UserGroupDao userGroupDao;
	private RoleOrganService roleOrganService;
	private UserService userService;
	
	/**
	 * 功能说明：保存用户组
	 * @author jacobliang
	 * @param uumGroup		用户组对象
	 * @throws ApplicationException
	 * @time Jan 24, 2011 4:10:06 PM
	 */
	public void saveUumGroup(UumGroup uumGroup) throws ApplicationException{
		this.userGroupDao.save(uumGroup);
		this.writeLog(uumGroup, LOG_LEVEL_FIRST, SAVE_OPT);
	}
	/**
	 * 功能说明：修改用户组
	 * @author jacobliang
	 * @param uumGroup 	用户组对象
	 * @throws ApplicationException
	 *@time Jan 24, 2011 4:10:06 PM
	 */
	public void modifyUumGroup(UumGroup uumGroup) throws ApplicationException{
		this.userGroupDao.deleteGroupMemberByGroupId(uumGroup.getGroupId());
		this.userGroupDao.update(uumGroup);
		this.writeLog(uumGroup, LOG_LEVEL_SECOND, MODIFY_OPT);
	}
	
	/**
	 * 功能说明：根据用户组ID查询用户组
	 * @author jacobliang
	 * @param  groupId	用户组ID
	 * @return
	 * @time Jan 24, 2011 4:10:06 PM
	 */
	public UumGroup findUumGroupById(long groupId){
		return (UumGroup)this.userGroupDao.getPojoById("com.bhtec.domain.pojo.uum.UumGroup", groupId);
	}
	/**
	 * 功能说明：根据组ID查询所有组成员
	 * @author jacobliang
	 * @param 	groupId		组成ID
	 * @return
	 * @time Jan 24, 2011 4:10:06 PM
	 */
	public List<UumGroupMember> findGroupByGroupId(long groupId){
		return this.userGroupDao.findGroupByGroupId(groupId);
	}
	
	/**
	 * 功能说明：停用启用用户组
	 * @author jacobliang
	 * @param  disEnableFlag		停用启用标志
	 * @param  groupId			用户组ID
	 * @return boolean	true为停用启用成功，false不能停用
	 * @throws 
	 * @time Jan 24, 2011 4:10:06 PM
	 */
	public boolean disEnableGroup(long groupId,String disEnableFlag)throws ApplicationException{
		if(DISABLE.equals(disEnableFlag)){
			UumGroup uumGroup = this.findUumGroupById(groupId);
			this.userGroupDao.modifyGroupStatus(groupId, DISABLE);
			this.writeLog(uumGroup, LOG_LEVEL_SECOND, DISABLE_DIS);
			return true;
		}else{
			UumGroup uumGroup = this.findUumGroupById(groupId);
			this.userGroupDao.modifyGroupStatus(groupId, ENABLE);
			this.writeLog(uumGroup, LOG_LEVEL_SECOND, ENABLE_DIS);
			return true;
		}
	}
	
	/**
	 * 功能说明：根据组名称查询组信息
	 * @author jacobliang
	 * @param start
	 * @param limit
	 * @param groupName	组名称
	 * @return
	 * @time Jan 24, 2011 4:10:06 PM
	 */
	@SuppressWarnings("unchecked")
	public Map findUserGroupByCon(int start,int limit,String groupName,List<UumUser> uumUserList){
		Map map = this.userGroupDao.findUserGroupByCon(start, limit, groupName,USER_GROUP);
		List<UumGroup> limitModuleList =  (List<UumGroup>)map.get(BUSI_LIST);
		
		for(UumGroup uumGroup:limitModuleList){
			Set set = uumGroup.getUumGroupMembers();
			Iterator iterator = set.iterator();
			while(iterator.hasNext()){
				UumGroupMember uumGroupMember = (UumGroupMember)iterator.next();
				long memberResourceId = uumGroupMember.getMemberResourceId();
				UumUser newUumUser = null; 
				for(UumUser uumUser:uumUserList){
					if(uumUser.getUserId().longValue() == memberResourceId){
						newUumUser = uumUser;
						break;
					}
				}
				if(newUumUser == null)newUumUser = new UumUser();
				uumGroupMember.setMemberResourceName(newUumUser.getUserName());
				uumGroupMember.setMemberResourceCode(newUumUser.getUserCode());
			}
		}
		return map;
	}
	
	
	/**
	 * 功能说明：公用模块写日志
	 * @author jacobliang
	 * @param sysplModuleMemu	模块对象
	 * @throws ApplicationException
	 * @throws 
	 * @time Sep 16, 2010 8:55:43 PM
	 */
	private void writeLog(UumGroup uumGroup,String logLevel,String opt)throws ApplicationException{
		StringBuffer logContent = new StringBuffer();
		logContent.append("组名称："+uumGroup.getGroupName()+SPLIT)
		.append("组类型："+uumGroup.getGroupType()+SPLIT);
		saveLog(logLevel, USER_GROUP_MGR,opt,logContent.toString(), uumGroup.getGroupId()+"");
	}
	
	/**
	 * 功能说明：查询指定的大类代码是否存在
	 * @author jacobliang
	 * @param 	groupName	组名称
	 * @param 	groupType	组类型 同普通组共用方法
	 * @return
	 * @time Jan 24, 2011 4:10:06 PM
	 */
	public boolean findGroupNameIsExist(String groupName,String groupType){
		int count = this.userGroupDao.findGroupByGroupName(groupName,groupType);
		if(count > 0)
			return true;
		return false;
	}
	
	/**
	 * 功能说明：根据机构ID角色ID和用户ID查询用户信息
	 * @author jacobliang
	 * @param orgId		机构ID
	 * @param roleId	角色ID
	 * @return
	 * @time Jan 26, 2011 9:15:05 AM
	 */
	public List<UumUser> findUserByOrgRolId(long organId,long roleId)throws ApplicationException{
		if(organId != -1 && roleId == -1){
			List<UumUser> userList = userService.findUumUserByOrganId(organId);
			return userList;
		}else{
			UumOrgRoleRef uumOrgRoleRef = roleOrganService.findOrganRoleRefByOrgRolId(organId, roleId);
			Iterator iterator = uumOrgRoleRef.getUumRoleUserRefs().iterator();
			List<UumUser> userList = new ArrayList<UumUser>();
			while(iterator.hasNext()){
				UumRoleUserRef uumRoleUserRef = (UumRoleUserRef)iterator.next();
				userList.add(uumRoleUserRef.getUumUser());
			}
			return userList;
		}
	}
	
	/**
	 * 功能说明：根据组ID修改组状态
	 * @author jacobliang
	 * @param groupId
	 * @param status
	 * @time Jan 27, 2011 3:10:53 PM
	 */
	public void modifyGroupStatus(long groupId,String status){
		this.userGroupDao.modifyGroupStatus(groupId, status);
	}
	
	public void setUserGroupDao(UserGroupDao userGroupDao) {
		this.userGroupDao = userGroupDao;
	}
	public void setRoleOrganService(RoleOrganService roleOrganService) {
		this.roleOrganService = roleOrganService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
