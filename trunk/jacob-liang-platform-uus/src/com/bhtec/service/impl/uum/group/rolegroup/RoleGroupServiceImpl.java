/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:30:57 PM
 */
package com.bhtec.service.impl.uum.group.rolegroup;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.DISABLE;
import static com.bhtec.common.constant.Common.ENABLE;
import static com.bhtec.common.constant.Common.LOG_LEVEL_FIRST;
import static com.bhtec.common.constant.Common.LOG_LEVEL_SECOND;
import static com.bhtec.common.constant.Common.MODIFY_OPT;
import static com.bhtec.common.constant.Common.SAVE_OPT;
import static com.bhtec.common.constant.Common.SPLIT;
import static com.bhtec.common.constant.ServiceVariable.DISABLE_DIS;
import static com.bhtec.common.constant.ServiceVariable.ENABLE_DIS;
import static com.bhtec.common.constant.ServiceVariable.ROLE_GROUP_MGR;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.bhtec.dao.iface.uum.group.rolegroup.RoleGroupDao;
import com.bhtec.domain.pojo.uum.UumGroup;
import com.bhtec.domain.pojo.uum.UumGroupMember;
import com.bhtec.domain.pojo.uum.UumOrgRoleRef;
import com.bhtec.domain.pojo.uum.UumRole;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.uum.group.rolegroup.RoleGroupService;
import com.bhtec.service.iface.uum.role.RoleOrganService;
import com.bhtec.service.impl.BaseServiceImpl;

public class RoleGroupServiceImpl extends BaseServiceImpl implements RoleGroupService {
	Logger log = Logger.getLogger(this.getClass());
	private RoleGroupDao roleGroupDao;
	private RoleOrganService roleOrganService;
	
	/**
	 * 功能说明：保存用户组
	 * @author jacobliang
	 * @param uumGroup		用户组对象
	 * @throws ApplicationException
	 * @time Jan 24, 2011 4:10:06 PM
	 */
	public void saveUumGroup(UumGroup uumGroup) throws ApplicationException{
		this.roleGroupDao.save(uumGroup);
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
		this.roleGroupDao.deleteGroupMemberByGroupId(uumGroup.getGroupId());
		this.roleGroupDao.update(uumGroup);
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
		return (UumGroup)this.roleGroupDao.getPojoById("com.bhtec.domain.pojo.uum.UumGroup", groupId);
	}
	/**
	 * 功能说明：根据组ID查询所有组成员
	 * @author jacobliang
	 * @param 	groupId		组成ID
	 * @return
	 * @time Jan 24, 2011 4:10:06 PM
	 */
	public List<UumGroupMember> findGroupByGroupId(long groupId){
		return this.roleGroupDao.findGroupByGroupId(groupId);
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
			this.roleGroupDao.modifyGroupStatus(groupId, DISABLE);
			this.writeLog(uumGroup, LOG_LEVEL_SECOND, DISABLE_DIS);
			return true;
		}else{
			UumGroup uumGroup = this.findUumGroupById(groupId);
			this.roleGroupDao.modifyGroupStatus(groupId, ENABLE);
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
	public Map findRoleGroupByCon(int start,int limit,String groupName,List<UumRole> uumRoleList){
		Map map = this.roleGroupDao.findRoleGroupByCon(start, limit, groupName);
		List<UumGroup> limitModuleList =  (List<UumGroup>)map.get(BUSI_LIST);
		
		for(UumGroup uumGroup:limitModuleList){
			Set set = uumGroup.getUumGroupMembers();
			Iterator iterator = set.iterator();
			while(iterator.hasNext()){
				UumGroupMember uumGroupMember = (UumGroupMember)iterator.next();
				long memberResourceId = uumGroupMember.getMemberResourceId();
				UumRole newUumRole = null; 
				for(UumRole uumRole:uumRoleList){
					if(uumRole.getRoleId().longValue() == memberResourceId){
						newUumRole = uumRole;
						break;
					}
				}
				if(newUumRole == null)newUumRole = new UumRole();
				uumGroupMember.setMemberResourceName(newUumRole.getRoleName());
				uumGroupMember.setMemberResourceCode(newUumRole.getMemo());
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
		saveLog(logLevel, ROLE_GROUP_MGR,opt,logContent.toString(), uumGroup.getGroupId()+"");
	}
	
	/**
	 * 功能说明：查询指定的大类代码是否存在
	 * @author jacobliang
	 * @param 	groupName	组名称
	 * @return
	 * @time Jan 24, 2011 4:10:06 PM
	 */
	public boolean findGroupNameIsExist(String groupName){
		int count = this.roleGroupDao.findGroupByGroupName(groupName);
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
	public List<UumRole> findRoleByOrgId(long organId)throws ApplicationException{
		List<UumRole> roleList = new ArrayList<UumRole>();
		List<UumOrgRoleRef> uumOrgRoleRefList = roleOrganService.findRoleOrganListByOrgId(organId);
		if(uumOrgRoleRefList != null && uumOrgRoleRefList.size() > 0){
			for(UumOrgRoleRef uumOrgRoleRef:uumOrgRoleRefList){
				UumRole uumRole = uumOrgRoleRef.getUumRole();
				roleList.add(uumRole);
			}
		}
		
		return roleList;
	}
	
	public void setRoleGroupDao(RoleGroupDao roleGroupDao) {
		this.roleGroupDao = roleGroupDao;
	}
	public void setRoleOrganService(RoleOrganService roleOrganService) {
		this.roleOrganService = roleOrganService;
	}
	
}
