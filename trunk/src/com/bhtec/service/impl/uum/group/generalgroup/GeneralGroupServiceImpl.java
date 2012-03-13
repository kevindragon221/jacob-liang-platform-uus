/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:30:57 PM
 */
package com.bhtec.service.impl.uum.group.generalgroup;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.GENERAL_GROUP;
import static com.bhtec.common.constant.Common.USER;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.bhtec.dao.iface.uum.group.usergroup.UserGroupDao;
import com.bhtec.domain.pojo.uum.UumGroup;
import com.bhtec.domain.pojo.uum.UumGroupMember;
import com.bhtec.domain.pojo.uum.UumRole;
import com.bhtec.domain.pojo.uum.UumUser;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.uum.group.generalgroup.GeneralGroupService;
import com.bhtec.service.iface.uum.group.rolegroup.RoleGroupService;
import com.bhtec.service.iface.uum.group.usergroup.UserGroupService;
import com.bhtec.service.impl.BaseServiceImpl;

public class GeneralGroupServiceImpl extends BaseServiceImpl implements GeneralGroupService {
	Logger log = Logger.getLogger(this.getClass());
	private UserGroupService userGroupService;//应用用户组的service
	private RoleGroupService roleGroupService;
	private UserGroupDao userGroupDao;
	
	/**
	 * 功能说明：保存用户组
	 * @author jacobliang
	 * @param uumGroup		用户组对象
	 * @throws ApplicationException
	 * @time Jan 24, 2011 4:10:06 PM
	 */
	public void saveUumGroup(UumGroup uumGroup) throws ApplicationException{
		userGroupService.setHttpServletRequest(this.getRequest());
		this.userGroupService.saveUumGroup(uumGroup);
	}
	/**
	 * 功能说明：修改用户组
	 * @author jacobliang
	 * @param uumGroup 	用户组对象
	 * @throws ApplicationException
	 *@time Jan 24, 2011 4:10:06 PM
	 */
	public void modifyUumGroup(UumGroup uumGroup) throws ApplicationException{
		userGroupService.setHttpServletRequest(this.getRequest());
		this.userGroupService.modifyUumGroup(uumGroup);
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
		userGroupService.setHttpServletRequest(this.getRequest());
		return userGroupService.disEnableGroup(groupId, disEnableFlag);
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
	public Map findGeneralGroupByCon(int start,int limit,String groupName,
								  List<UumUser> uumUserList,List<UumRole> uumRoleList){
		Map map = this.userGroupDao.findUserGroupByCon(start, limit, groupName,GENERAL_GROUP);
		List<UumGroup> limitModuleList =  (List<UumGroup>)map.get(BUSI_LIST);
		for(UumGroup uumGroup:limitModuleList){
			Set set = uumGroup.getUumGroupMembers();
			Iterator iterator = set.iterator();
			while(iterator.hasNext()){
				UumGroupMember uumGroupMember = (UumGroupMember)iterator.next();
				long memberResourceId = uumGroupMember.getMemberResourceId();
				if(USER.equals(uumGroupMember.getGroupMemberType())){//如果是用户
					UumUser newUumUser = null; 
					for(UumUser uumUser:uumUserList){
						if(uumUser.getUserId().longValue() == memberResourceId){
							newUumUser = uumUser;
							break;
						}
					}
					if(newUumUser == null)newUumUser = new UumUser();
					uumGroupMember.setMemberResourceName(newUumUser.getUserName());
				}else{//角色
					UumRole newUumRole = null; 
					for(UumRole uumRole:uumRoleList){
						if(uumRole.getRoleId().longValue() == memberResourceId){
							newUumRole = uumRole;
							break;
						}
					}
					if(newUumRole == null)newUumRole = new UumRole();
					uumGroupMember.setMemberResourceName(newUumRole.getRoleName());
				}
			}
		}
		return map;
	}
	
	
	/**
	 * 功能说明：查询指定的大类代码是否存在
	 * @author jacobliang
	 * @param 	groupName	组名称
	 * @param 	groupType	组类型 同用户组共用方法
	 * @return
	 * @time Jan 24, 2011 4:10:06 PM
	 */
	public boolean findGroupNameIsExist(String groupName){
		return this.userGroupService.findGroupNameIsExist(groupName,GENERAL_GROUP);
	}
	
	/**
	 * 功能说明：根据机构ID角色ID和用户ID查询用户信息
	 * @author jacobliang
	 * @param orgId		机构ID
	 * @param roleId	角色ID
	 * @return
	 * @time Jan 26, 2011 9:15:05 AM
	 */
	public List<UumUser> findGeneralByOrgRolId(long organId,long roleId,String memberType)throws ApplicationException{
		List<UumUser>  list = new ArrayList<UumUser>();
		if(USER.equals(memberType)){
			list = this.userGroupService.findUserByOrgRolId(organId, roleId);
		}else{
			List<UumRole> roleList = this.roleGroupService.findRoleByOrgId(organId);
			//将roleId转换成userId
			if(roleList != null){
				for(UumRole uumRole : roleList){
					UumUser uumUser = new UumUser();
					uumUser.setUserId(uumRole.getRoleId());
					uumUser.setUserName(uumRole.getRoleName());
					list.add(uumUser);
				}
			}
		}
		return list;
	}
	
	public void setUserGroupService(UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}
	
	public void setRoleGroupService(RoleGroupService roleGroupService) {
		this.roleGroupService = roleGroupService;
	}
	public UserGroupDao getUserGroupDao() {
		return userGroupDao;
	}
	public void setUserGroupDao(UserGroupDao userGroupDao) {
		this.userGroupDao = userGroupDao;
	}
}
