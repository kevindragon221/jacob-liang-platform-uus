/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 23, 2010 @3:22:30 PM
 */
package com.bhtec.service.impl.uum.role;

import static com.bhtec.common.constant.Common.LOG_LEVEL_FIRST;
import static com.bhtec.common.constant.Common.SAVE_OPT;
import static com.bhtec.common.constant.ServiceVariable.N;
import static com.bhtec.common.constant.ServiceVariable.USER_ROLE_MGR;
import static com.bhtec.common.constant.ServiceVariable.Y;
import static com.bhtec.common.tools.UtilTools.isNullOrEmpty;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bhtec.dao.iface.uum.role.RoleUserDao;
import com.bhtec.domain.pojo.uum.UumOrgRoleRef;
import com.bhtec.domain.pojo.uum.UumOrgan;
import com.bhtec.domain.pojo.uum.UumRole;
import com.bhtec.domain.pojo.uum.UumRoleUserRef;
import com.bhtec.domain.pojo.uum.UumUser;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.uum.role.RoleOrganService;
import com.bhtec.service.iface.uum.role.RoleUserService;
import com.bhtec.service.iface.uum.user.UserService;
import com.bhtec.service.impl.BaseServiceImpl;

public class RoleUserServiceImpl extends BaseServiceImpl implements RoleUserService {
	Logger log = Logger.getLogger(this.getClass());
	private RoleUserDao roleUserDao;
	private RoleOrganService roleOrganService;
	private UserService userService;
	
	/**
	 * 功能说明：删除角色用户关系记录
	 * @author jacobliang
	 * @param userId
	 * @throws ApplicationException
	 * @time Oct 9, 2010 3:51:28 PM
	 */
	public void deleteRoleUserRef(long userId) throws ApplicationException {
		roleUserDao.deleteRoleUserRef(userId);
	}
	/**
	 * 功能说明：根据用户ID查找用户的所有角色
	 * @author jacobliang
	 * @param 	userId					用户主键ID
	 * @return	List<UumRoleUserRef>	用户角色关系记录
	 * @time Oct 9, 2010 3:52:47 PM
	 */
	public List<UumRoleUserRef> findRoleUserListByUserId(long userId) {
		List<UumRoleUserRef> roleUserRefList = roleUserDao.findRoleUserListByUserId(userId);
		if(roleUserRefList == null || roleUserRefList.size()==0)return null;
		for(UumRoleUserRef uumRoleUserRef:roleUserRefList){
			UumOrgRoleRef uumOrgRoleRef = uumRoleUserRef.getUumOrgRoleRef();//机构角色关系
			UumOrgan uumOrgan = uumOrgRoleRef.getUumOrgan();
			UumRole uumRole = uumOrgRoleRef.getUumRole();
			
			uumRoleUserRef.setOrgRoleId(uumOrgRoleRef.getOrgRoleId());
			uumRoleUserRef.setOrganId(uumOrgan.getOrgId());
			uumRoleUserRef.setOrganName(uumOrgan.getOrgSimpleName());
			uumRoleUserRef.setRoleId(uumRole.getRoleId());
			uumRoleUserRef.setRoleName(uumRole.getRoleName());
		}
		return roleUserRefList;
	}
	/**
	 * 功能说明：保存角色用户关系记录
	 * @author jacobliang
	 * @param userId		用户id
	 * @param orgRoleId		机构角色关系id
	 * @param dfaultOrgRoleId		默认机构角色ID
	 * @throws ApplicationException
	 * @time Oct 9, 2010 3:47:00 PM
	 */
	public void saveRoleUserRef(long userId, List<Long> orgRoleId,long dfaultOrgRoleId) throws ApplicationException {
		String orgRoleIdStr = "";
		List<UumRoleUserRef> listUumRoleUserRef = new ArrayList<UumRoleUserRef>();
		for(Long orgRoleid:orgRoleId){
			UumRoleUserRef uumRoleUserRef = new UumRoleUserRef();
			
			UumUser uumUser = new UumUser();
			uumUser.setUserId(userId);
			uumRoleUserRef.setUumUser(uumUser);
			
			UumOrgRoleRef uumOrgRoleRef = new UumOrgRoleRef();
			uumOrgRoleRef.setOrgRoleId(orgRoleid);
			uumRoleUserRef.setUumOrgRoleRef(uumOrgRoleRef);
			if(dfaultOrgRoleId == orgRoleid){
				uumRoleUserRef.setIsDefault(Y);
				UumOrgRoleRef uumOrgRoleReff = roleOrganService.findUumOrgRoleRefById(orgRoleid);
				//修改用户的默认机构
				userService.modifyUserBelongOrgIdAssignRole(uumOrgRoleReff.getUumOrgan().getOrgId(), userId);
			}else{
				uumRoleUserRef.setIsDefault(N);
			}		
			listUumRoleUserRef.add(uumRoleUserRef);
			orgRoleIdStr = orgRoleIdStr.equals("")?orgRoleid+"":orgRoleIdStr+","+orgRoleid;
		}
		this.roleUserDao.batchSave(listUumRoleUserRef);
		saveLog(LOG_LEVEL_FIRST, USER_ROLE_MGR,SAVE_OPT,orgRoleIdStr, userId+"");
	}
	
	/**
	 * 功能说明：保存为用户分配的角色
	 * @author jacobliang
	 * @param userId			用户ID
	 * @param orgRoleIds		机构角色字符串以逗号分隔
	 * @param defaultOrgRoleId	默认角色
	 * @throws ApplicationException
	 * @time Oct 25, 2010 7:43:24 PM
	 */
	public void saveRoleUser(long userId,String orgRoleIds,long defaultOrgRoleId)throws ApplicationException{
		//选择删除用户所有的角色
		this.deleteRoleUserRef(userId);
		String[] orgRoleIdArr = orgRoleIds.split(",");
		List<Long> orgRoleIdList = new ArrayList<Long>();
		for(String orgRoleId:orgRoleIdArr){
			if(!isNullOrEmpty(orgRoleId)){
				orgRoleIdList.add(Long.parseLong(orgRoleId));
			}
		}
		//为用户添加角色
		this.saveRoleUserRef(userId, orgRoleIdList, defaultOrgRoleId);
	}
	
	/**
	 * 功能说明：根据机构角色ID查询角色用户关系
	 * @author jacobliang
	 * @param orgRoleUserId
	 * @return
	 * @time Nov 5, 2010 8:30:57 PM
	 */
	public int findRoleUserRefById(long orgRoleId){
		return this.roleUserDao.findRoleUserRefById(orgRoleId);
	}
	
	/**
	 * 功能说明：根据角色ID查询其下分配的用户数量
	 * @author jacobliang
	 * @param roleId	角色ID
	 * @return
	 * @time Nov 10, 2010 2:02:48 PM
	 */
	public int findRoleUserRefTotalByRoleId(long roleId){
		return this.roleUserDao.findRoleUserRefTotalByRoleId(roleId);
	}
	/**
	 * 功能说明：根据用户ID和机构角色ID查找用户的角色
	 * @author jacobliang
	 * @param userId
	 * @param orgRoleId
	 * @return
	 * @time Feb 28, 2012 10:09:49 AM
	 */
	public UumRoleUserRef findRoleUserByUserIdAOrgRoleId(long userId,long orgRoleId){
		return this.roleUserDao.findRoleUserByUserIdAOrgRoleId(userId, orgRoleId);
	}

	public void setRoleUserDao(RoleUserDao roleUserDao) {
		this.roleUserDao = roleUserDao;
	}

	public void setRoleOrganService(RoleOrganService roleOrganService) {
		this.roleOrganService = roleOrganService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
