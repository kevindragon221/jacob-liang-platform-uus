/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 23, 2010 @3:22:30 PM
 */
package com.bhtec.service.impl.uum.user;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.LOG_LEVEL_FIRST;
import static com.bhtec.common.constant.Common.LOG_LEVEL_SECOND;
import static com.bhtec.common.constant.Common.MODIFY_OPT;
import static com.bhtec.common.constant.Common.OWNER_TYPE_ROL;
import static com.bhtec.common.constant.Common.OWNER_TYPE_USR;
import static com.bhtec.common.constant.Common.PRIVILEGE_SCOPE_ALL;
import static com.bhtec.common.constant.Common.PRIVILEGE_TYPE_OPT;
import static com.bhtec.common.constant.Common.SAVE_OPT;
import static com.bhtec.common.constant.Common.SPLIT;
import static com.bhtec.common.constant.ServiceVariable.MODULE_MENU;
import static com.bhtec.common.constant.ServiceVariable.USER_AGENT;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.bhtec.common.tools.UtilTools;
import com.bhtec.dao.iface.uum.user.UserAgentDao;
import com.bhtec.domain.pojo.platform.SysplModOptRef;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.domain.pojo.uum.UumOrgRoleRef;
import com.bhtec.domain.pojo.uum.UumOrgan;
import com.bhtec.domain.pojo.uum.UumRole;
import com.bhtec.domain.pojo.uum.UumRoleUserRef;
import com.bhtec.domain.pojo.uum.UumUser;
import com.bhtec.domain.pojo.uum.UumUserAgent;
import com.bhtec.domain.pojo.uum.UumUserAgentPrivilege;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.platform.PlatformCommonService;
import com.bhtec.service.iface.uum.privilege.PrivilegeService;
import com.bhtec.service.iface.uum.role.RoleOrganService;
import com.bhtec.service.iface.uum.role.RoleUserService;
import com.bhtec.service.iface.uum.user.UserAgentService;
import com.bhtec.service.iface.uum.user.UserService;
import com.bhtec.service.impl.BaseServiceImpl;

public class UserAgentServiceImpl extends BaseServiceImpl implements UserAgentService {
	Logger log = Logger.getLogger(this.getClass());
	private UserAgentDao userAgentDao;
	private UserService userService;
	private PlatformCommonService platformCommonService;
	private PrivilegeService privilegeService;
	private RoleUserService roleUserService;
	private RoleOrganService roleOrganService;
	public void setUserAgentDao(UserAgentDao userAgentDao) {
		this.userAgentDao = userAgentDao;
	}

	/**
	 * 功能说明：根据用户ID分页查询用户有关的所有代理
	 * @author jacobliang
	 * @param start
	 * @param limit
	 * @param userId
	 * @return
	 * @time Feb 17, 2012 4:41:25 PM
	 */
	public Map findUserAgentByCon(int start, int limit, long userId) {
		Map map = userAgentDao.findUserAgentByCon(start, limit, userId);
		List<UumUserAgent> userAgentList = (List<UumUserAgent>)map.get(BUSI_LIST);
		if(userAgentList != null){
			for(UumUserAgent uumUserAgent:userAgentList){
				long agentUserId = uumUserAgent.getAgentUserId();
				UumUser uumUser = userService.findUserByUserId(agentUserId);
				uumUserAgent.setUserName(uumUser.getUserName());
			}
		}
		return map;
	}
	

	@SuppressWarnings("unused")
	private UumUserAgent findUumUserAgentByAgentId(long agentId){
		return (UumUserAgent)this.userAgentDao.getPojoById("com.bhtec.domain.pojo.uum.UumUserAgent", agentId);
	}
	
	public void modifyUserAgent(UumUserAgent uumUserAgent) throws ApplicationException {
		this.userAgentDao.update(uumUserAgent);//保存用户
		writeLog(uumUserAgent, LOG_LEVEL_SECOND, MODIFY_OPT);
	}
	
	public void saveUserAgent(UumUserAgent uumUserAgent,List<String> modOptIdList) throws ApplicationException {
		if(modOptIdList != null){
			Set<UumUserAgentPrivilege> uumUserAgentPrivileges = new HashSet<UumUserAgentPrivilege>();
			for(String privilegeId:modOptIdList){
				UumUserAgentPrivilege uumUserAgentPrivilege = new UumUserAgentPrivilege();
				uumUserAgentPrivilege.setUumUserAgent(uumUserAgent);
				uumUserAgentPrivilege.setPrivilegeId(privilegeId);
				uumUserAgentPrivilege.setPrivilegeType(PRIVILEGE_TYPE_OPT);
				uumUserAgentPrivileges.add(uumUserAgentPrivilege);
			}
			uumUserAgent.setUumUserAgentPrivileges(uumUserAgentPrivileges);
		}
		this.userAgentDao.save(uumUserAgent);
		this.writeLog(uumUserAgent, LOG_LEVEL_FIRST, SAVE_OPT);
	}
	
	/**
	 * 功能说明：删除用户代理
	 * @author jacobliang
	 * @param uumUserAgent
	 * @throws ApplicationException
	 * @time Feb 17, 2012 4:57:59 PM
	 */
	public void deleteUserAgent(List<Long> agentId)throws ApplicationException{
		List<UumUserAgent> uumUserAgentList = new ArrayList<UumUserAgent>();
		for(long agentIdl:agentId){
			UumUserAgent uumUserAgent = findUumUserAgentByAgentId(agentIdl);
			uumUserAgentList.add(uumUserAgent);
		}
		userAgentDao.deleteAll(uumUserAgentList);
	}
	/**
	 * 功能说明：当用户停用时删除其所有代理
	 * @author jacobliang
	 * @param userId
	 * @time Mar 2, 2012 10:38:19 AM
	 */
	public void deleteAllUserAgent(long userId)throws ApplicationException{
		List<UumUserAgent> userAgentList = this.userAgentDao.findAllUserAgentByUserId(userId);
		userAgentDao.deleteAll(userAgentList); 
	}
	/**
	 * 功能说明：公用模块写日志
	 * @author jacobliang
	 * @param UumOrgan	模块对象
	 * @throws ApplicationException
	 * @throws 
	 * @time Sep 16, 2010 8:55:43 PM
	 */
	private void writeLog(UumUserAgent uumUserAgent,String logLevel,String opt)throws ApplicationException{
		StringBuffer logContent = new StringBuffer();
		logContent.append(uumUserAgent.getAgentId()+SPLIT).append(uumUserAgent.getUserId()+SPLIT)
		.append(uumUserAgent.getAgentUserId()+SPLIT);
		saveLog(logLevel, USER_AGENT,opt,logContent.toString(), uumUserAgent.getAgentId()+"");
	}
	/**
	 * 功能说明：判断是否已经为此用户授过权
	 * @author jacobliang
	 * @return true 存在 false 不存在
	 * @time Feb 21, 2012 4:13:57 PM
	 */
	public boolean hasAgentUser(long userId,long userAgentId){
		int count = this.userAgentDao.findByUserIdAAgentUserId(userId, userAgentId);
		if(count > 0)return true;
		return false;
	}
	/**
	 * 功能说明：查找用户分配及未分配的模块操作代理权限
	 * @author jacobliang
	 * @return
	 * @time Feb 22, 2012 9:53:18 AM
	 */
	public List<SysplModuleMemu> userAssignAgentOptPrivilege(String agentId){
		List list = this.userAgentDao.findAgentPrivilegeByAgentId(agentId);
		if(list.size() == 1){
			String privilegeId = (String)list.get(0);
			if(PRIVILEGE_SCOPE_ALL.equals(privilegeId))return null;
		}
		//查找所有操作
		List<SysplModOptRef> allModOpt = platformCommonService.findAllSysplModOptRef();
		List<SysplModOptRef> priList = new ArrayList<SysplModOptRef>();
		for(int i=0,l=list.size();i<l;i++){
			String privilegeId = (String)list.get(i);
			for(SysplModOptRef sysplModOptRef:allModOpt){
				long modOptId = sysplModOptRef.getModOptId();
				if(modOptId == Long.parseLong(privilegeId)){
					priList.add(sysplModOptRef);
					break;
				}
			}
		}
		List<SysplModuleMemu>  assignedModMenuList = 
			(List<SysplModuleMemu>)privilegeService.findFilterModOpt(priList,true).get(MODULE_MENU);
		return assignedModMenuList;
	}
	
	/**
	 * 功能说明：根据用户ID查询代理记录
	 * @author jacobliang
	 * @param userId
	 * @return
	 * @time Feb 23, 2012 9:39:56 AM
	 */
	public List<UumUserAgent> findUumUserAgentByAgentUserId(long userId){
		List<UumUserAgent> userAgentList = this.userAgentDao.findUumUserAgentByAgentUserId(userId);
		if(userAgentList != null){
			for(UumUserAgent uumUserAgent:userAgentList){
				long userId2 = uumUserAgent.getUserId();
				long orgRoleId = uumUserAgent.getOrgRoleId();
				
				UumRoleUserRef uumRoleUserRef = roleUserService.findRoleUserByUserIdAOrgRoleId(userId2, orgRoleId);
				UumUser uumUser = uumRoleUserRef.getUumUser();
				uumUserAgent.setUserName(uumUser.getUserName());
				uumUserAgent.setUserCode(uumUser.getUserCode());
				uumUserAgent.setUserId(uumUser.getUserId());
				
				UumOrgRoleRef uumOrgRoleRef = uumRoleUserRef.getUumOrgRoleRef();
				UumRole uumRole = uumOrgRoleRef.getUumRole();
				uumUserAgent.setRoleId(uumRole.getRoleId());
				uumUserAgent.setRoleName(uumRole.getRoleName());
				
				UumOrgan uumOrgan = uumOrgRoleRef.getUumOrgan();
				uumUserAgent.setOrganId(uumOrgan.getOrgId());
				uumUserAgent.setOrganName(uumOrgan.getOrgSimpleName());
			}
		}
		return userAgentList;
	}
	
	/**
	 * 功能说明：根据代理ID查询代理的权限
	 * @author jacobliang
	 * @param agentId
	 * @return
	 * @time Feb 24, 2012 10:14:46 AM
	 */
	public List findAgentPrivilegeByAgentId(String agentId){
		return this.userAgentDao.findAgentPrivilegeByAgentId(agentId);
	}
	
	/**
	 * 功能说明：当用户重新分配权限时，删除代理
	 * @author jacobliang
	 * @time Mar 2, 2012 5:48:08 PM
	 */
	public void deleteAgentByOwnerId(Long ownerId,String ownerType){
		List<UumUserAgent> agentList = null;
		if(OWNER_TYPE_ROL.equals(ownerType)){
			StringBuffer ownerIdStr = new StringBuffer();
			List orgRolIdList = roleOrganService.findUumOrgRoleRefByRoleId(ownerId.toString());
			if(orgRolIdList == null)return;
			for(int i=0,l=orgRolIdList.size();i<l;i++){
				ownerIdStr = ownerIdStr.length()==0
							 ?ownerIdStr.append(orgRolIdList.get(i))
						     :ownerIdStr.append(","+orgRolIdList.get(i));
			}
			if(!UtilTools.isNullOrEmpty(ownerIdStr.toString()))
				agentList = this.userAgentDao.findUserAgentByOwnerId(ownerIdStr.toString());
			
		}else if(OWNER_TYPE_USR.equals(ownerType)){
			agentList = this.userAgentDao.findAllUserAgentByUserId(ownerId);
		}
		if(!UtilTools.isNull(agentList))
			this.userAgentDao.deleteAll(agentList);
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setPlatformCommonService(PlatformCommonService platformCommonService) {
		this.platformCommonService = platformCommonService;
	}

	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

	public void setRoleUserService(RoleUserService roleUserService) {
		this.roleUserService = roleUserService;
	}

	public void setRoleOrganService(RoleOrganService roleOrganService) {
		this.roleOrganService = roleOrganService;
	}
}
