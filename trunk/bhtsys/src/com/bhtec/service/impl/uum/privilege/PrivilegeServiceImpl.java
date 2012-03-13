/**
 *功能说明：
 * @author jacobliang
 * @time @Nov 1, 2010 @11:41:41 AM
 */
package com.bhtec.service.impl.uum.privilege;

import static com.bhtec.common.constant.Common.FOURTH;
import static com.bhtec.common.constant.Common.LOG_LEVEL_FIRST;
import static com.bhtec.common.constant.Common.ORGAN_ID;
import static com.bhtec.common.constant.Common.OWNER_TYPE_ARR;
import static com.bhtec.common.constant.Common.OWNER_TYPE_ROL;
import static com.bhtec.common.constant.Common.OWNER_TYPE_USR;
import static com.bhtec.common.constant.Common.PRIVILEGE_SCOPE;
import static com.bhtec.common.constant.Common.PRIVILEGE_SCOPE_ALL;
import static com.bhtec.common.constant.Common.PRIVILEGE_SCOPE_EXC;
import static com.bhtec.common.constant.Common.PRIVILEGE_SCOPE_ORG;
import static com.bhtec.common.constant.Common.PRIVILEGE_SCOPE_ROL;
import static com.bhtec.common.constant.Common.PRIVILEGE_SCOPE_USR;
import static com.bhtec.common.constant.Common.PRIVILEGE_TYPE_ARR;
import static com.bhtec.common.constant.Common.PRIVILEGE_TYPE_OPT;
import static com.bhtec.common.constant.Common.PRIVILEGE_TYPE_ROW;
import static com.bhtec.common.constant.Common.ROLE_ID;
import static com.bhtec.common.constant.Common.SAVE_OPT;
import static com.bhtec.common.constant.Common.SPLIT;
import static com.bhtec.common.constant.Common.USER;
import static com.bhtec.common.constant.Common.USER_ID;
import static com.bhtec.common.constant.ServiceVariable.ASSIGNED_MODOPTS;
import static com.bhtec.common.constant.ServiceVariable.MODULE_MENU;
import static com.bhtec.common.constant.ServiceVariable.MODULE_OPERATE;
import static com.bhtec.common.constant.ServiceVariable.MOD_ASSIGN_OPT;
import static com.bhtec.common.constant.ServiceVariable.UNASSIGNED_MODOPTS;
import static com.bhtec.common.tools.UtilTools.isEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bhtec.common.util.SystemConfigBean;
import com.bhtec.common.util.SystemConifgXmlParse;
import com.bhtec.dao.iface.uum.privilege.PrivilegeDao;
import com.bhtec.domain.pojo.platform.SysplModOptRef;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.domain.pojo.platform.SysplOperate;
import com.bhtec.domain.pojo.uum.UumOrgRoleRef;
import com.bhtec.domain.pojo.uum.UumOrgan;
import com.bhtec.domain.pojo.uum.UumPrivilege;
import com.bhtec.domain.pojo.uum.UumUser;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.platform.PlatformCommonService;
import com.bhtec.service.iface.uum.organ.OrganService;
import com.bhtec.service.iface.uum.privilege.PrivilegeService;
import com.bhtec.service.iface.uum.role.RoleOrganService;
import com.bhtec.service.iface.uum.user.UserAgentService;
import com.bhtec.service.iface.uum.user.UserService;
import com.bhtec.service.impl.BaseServiceImpl;

public class PrivilegeServiceImpl extends BaseServiceImpl implements
		PrivilegeService {
	Logger log = Logger.getLogger(this.getClass());
	private PrivilegeDao privilegeDao;
	private PlatformCommonService platformCommonService;
	private UserService userService;
	private RoleOrganService roleOrganService;
	private OrganService organService;
	private UserAgentService userAgentService;
	/**
	 * 功能说明：根据用户或角色ID 和 标识查询所有已分配和未分配模块操作权限
	 * @author jacobliang
	 * @param ownerId			用户或角色ID
	 * @param ownerType		为用户或角色标识
	 * @return	Map<String,List<SysplModuleMemu>> 1.list 已分配	2.list	未分配
	 * @time Nov 1, 2010 11:36:34 AM
	 */
	public Map findSeledAUnseledModOptByOwnIdAPriType(long ownerId,String ownerType) {
		//查找已经分配的权限
		List<UumPrivilege> uumPrivilegeOptList = 
							this.privilegeDao.findPrivilegeByOwnIdAPriType(ownerId+"", "'"+PRIVILEGE_TYPE_OPT+"'","'"+ownerType+"'");
		//操作
		StringBuffer resourceIdBuf = new StringBuffer();
		String privilegeScope = "";
		for(UumPrivilege uumPrivilege:uumPrivilegeOptList){
			String resourceId = uumPrivilege.getResourceId();
			if(isEmpty(privilegeScope))
				privilegeScope = uumPrivilege.getPrivilegeScope();
			resourceIdBuf = resourceIdBuf.length()==0
							?resourceIdBuf.append(resourceId)
							:resourceIdBuf.append(","+resourceId);
		}
		List<SysplModOptRef> assignedModOpt = platformCommonService.findSysplModOptRefByModOptRefIds(resourceIdBuf.toString());
		//查找所有操作
		List<SysplModOptRef> allModOpt = platformCommonService.findAllSysplModOptRef();
		allModOpt.removeAll(assignedModOpt);
		List<SysplModOptRef> unassignedModOpt = allModOpt;
		//将操作转化成列表数据
		List<SysplModuleMemu>  assignedModMenuList = (List<SysplModuleMemu>)findFilterModOpt(assignedModOpt,true).get(MODULE_MENU);
		List<SysplModuleMemu>  unassignedModMenuList = (List<SysplModuleMemu>)findFilterModOpt(unassignedModOpt,true).get(MODULE_MENU);
		//将已分配和未分配的模块操作放入map
		Map moduleMemuMap = new HashMap();
		moduleMemuMap.put(ASSIGNED_MODOPTS, assignedModMenuList);
		moduleMemuMap.put(UNASSIGNED_MODOPTS, unassignedModMenuList);
		moduleMemuMap.put(PRIVILEGE_SCOPE, privilegeScope);
		return moduleMemuMap;
	}
	/**
	 * 功能说明：根据用户ID 已分配行权限
	 * @author jacobliang
	 * @param userId			用户ID
	 * @return	1.list 已分配 	2.list	权限范围
	 * @time Nov 1, 2010 11:36:34 AM
	 */
	public Map findSeledRowPrivilegeByUserId(long userId) {
		//查找已经分配的权限
		List<UumPrivilege> uumPrivilegeOptList = 
							this.privilegeDao.findPrivilegeByOwnIdAPriType(userId+"", "'"+PRIVILEGE_TYPE_ROW+"'","'"+OWNER_TYPE_USR+"'");
		StringBuffer resourceIdBuf = new StringBuffer();
		String privilegeScope = "";
		for(UumPrivilege uumPrivilege:uumPrivilegeOptList){
			String resourceId = uumPrivilege.getResourceId();
			if(isEmpty(privilegeScope))
				privilegeScope = uumPrivilege.getPrivilegeScope();
			resourceIdBuf = resourceIdBuf.length()==0
							?resourceIdBuf.append(resourceId)
							:resourceIdBuf.append(","+resourceId);
		}
		List userInfo = new ArrayList();
		if(privilegeScope.equals(PRIVILEGE_SCOPE_USR)){
			List userList = userService.findUumUserByUserIds(resourceIdBuf.toString());
			if(userList != null){
				userInfo = userList;
			}
		}else if(privilegeScope.equals(PRIVILEGE_SCOPE_ROL)){
			List<UumOrgRoleRef> uumOrgRoleRefList = roleOrganService.findUumOrgRoleRefByOrgRoleIds(resourceIdBuf.toString());
			if(uumOrgRoleRefList != null){
				for(int i=0,l=uumOrgRoleRefList.size();i<l;i++){
					UumOrgRoleRef uumOrgRoleRef = uumOrgRoleRefList.get(i);
					long orgRoleId = uumOrgRoleRef.getOrgRoleId();
					String orgName = uumOrgRoleRef.getUumOrgan().getOrgSimpleName();
					String roleName = uumOrgRoleRef.getUumRole().getRoleName();
					Object[] orgObj = new Object[2];
					orgObj[0] = orgRoleId;
					orgObj[1] = orgName+"-"+roleName;
					userInfo.add(orgObj);
				}
			}
		}else{
			List uumOrganList = organService.findUumOrgansByOrgIds(resourceIdBuf.toString());
			if(uumOrganList != null){
				userInfo = uumOrganList;
			}
		}
		Map map = new HashMap();
		map.put(USER, userInfo);
		map.put(PRIVILEGE_SCOPE, privilegeScope);
		return map;
	}
	/**
	 * 功能说明：根据用户名模糊查询用户信息
	 * @author jacobliang
	 * @param userName
	 * @return
	 * @time Feb 14, 2012 4:38:16 PM
	 */
	public List<UumUser> findAllUumUserByUserName(String userName){
		return this.userService.findAllUumUserByUserName(userName);
	}
	/**
	 * 功能说明：根据登录的用户或角色ID 和 标识查询所有模块操作
	 * @author jacobliang
	 * @param userId		    为用户ID
	 * @param roleId		    为角色ID
	 * @param organId		    为机构ID
	 * @param organRoleId		    为机构角色ID
	 * @see	  角色-操作以roleId(笼统)过滤权限  角色-行以organRoleId(具体)过滤权限
	 * @return modMenuMap:1-list;2-map;3-map;4-map
	 * @time Nov 1, 2010 11:29:03 AM
	 */
	public Map findUserPrivilegeForLogin(long userId,long roleId,long organId,long organRoleId) {
		StringBuffer privilegeTypes = new StringBuffer();
		for(String privilegeType:PRIVILEGE_TYPE_ARR){//权限类型数组
			privilegeTypes = privilegeTypes.length()==0
							?privilegeTypes.append("'"+privilegeType+"'")
							:privilegeTypes.append(",'"+privilegeType+"'");
		}
		StringBuffer ownerTypes = new StringBuffer();
		for(String ownerType:OWNER_TYPE_ARR){//拥有者类型数组
			ownerTypes = ownerTypes.length()==0
							?ownerTypes.append("'"+ownerType+"'")
							:ownerTypes.append(",'"+ownerType+"'");
		}
		//查找用户相关所有权限
		String ownerId = userId+","+roleId+","+organRoleId;
		List<UumPrivilege> uumPrivilegeList = 
			this.privilegeDao.findPrivilegeByOwnIdAPriType(ownerId, privilegeTypes.toString(), ownerTypes.toString());
		//划分权限
		List<UumPrivilege> uumPrivilegeOptList = new ArrayList<UumPrivilege>();
		List<UumPrivilege> uumPrivilegeRowList = new ArrayList<UumPrivilege>();
		List<UumPrivilege> uumPrivilegeOptSpecialList = new ArrayList<UumPrivilege>();
		List<UumPrivilege> uumPrivilegeRowSpecialList = new ArrayList<UumPrivilege>();
		for(UumPrivilege uumPrivilege:uumPrivilegeList){
			String ownerType = uumPrivilege.getOwnerType();
			String privilegeType = uumPrivilege.getPrivilegeType();
			if(OWNER_TYPE_USR.equals(ownerType)){//用户
				if(PRIVILEGE_TYPE_OPT.equals(privilegeType)){
					uumPrivilegeOptSpecialList.add(uumPrivilege);
				}else if(PRIVILEGE_TYPE_ROW.equals(privilegeType)){
					uumPrivilegeRowSpecialList.add(uumPrivilege);
				}
			}else if(OWNER_TYPE_ROL.equals(ownerType)){//角色
				if(PRIVILEGE_TYPE_OPT.equals(privilegeType)){
					uumPrivilegeOptList.add(uumPrivilege);
				}else if(PRIVILEGE_TYPE_ROW.equals(privilegeType)){
					uumPrivilegeRowList.add(uumPrivilege);
				}
			}
		}
		//权限校验过滤
		if(!uumPrivilegeOptSpecialList.isEmpty()){
			uumPrivilegeOptList = uumPrivilegeOptSpecialList;
		}
		if(!uumPrivilegeRowSpecialList.isEmpty()){
			uumPrivilegeRowList = uumPrivilegeRowSpecialList;
		}
		//操作
		StringBuffer resourceIdBuf = new StringBuffer();
		String privilegeScope = "";
		for(UumPrivilege uumPrivilege:uumPrivilegeOptList){
			String resourceId = uumPrivilege.getResourceId();
			if(privilegeScope.equals(""))
				privilegeScope = uumPrivilege.getPrivilegeScope();
			resourceIdBuf = resourceIdBuf.length()==0
							?resourceIdBuf.append(resourceId)
							:resourceIdBuf.append(","+resourceId);
		}
		List<SysplModOptRef> sysplModOptRefList = platformCommonService.findSysplModOptRefByModOptRefIds(resourceIdBuf.toString());
		if(PRIVILEGE_SCOPE_EXC.equals(privilegeScope)){
			List<SysplModOptRef> sysplModOptRefListAll = platformCommonService.findAllSysplModOptRef();//所有操作按钮
			sysplModOptRefListAll.removeAll(sysplModOptRefList);
			sysplModOptRefList = sysplModOptRefListAll;
		}else if(PRIVILEGE_SCOPE_ALL.equals(privilegeScope)){
			List<SysplModOptRef> sysplModOptRefListAll = platformCommonService.findAllSysplModOptRef();//所有操作按钮
			sysplModOptRefList = sysplModOptRefListAll;
		}
		
		//过滤所有的模块操作
		Map sysplModOptMap = this.findFilterModOpt(sysplModOptRefList,false);
		List<SysplModuleMemu> sysplModuleMemuList = (List<SysplModuleMemu>)sysplModOptMap.get(MODULE_MENU);
		Map<String,List<SysplModuleMemu>> modOptMap = (Map<String,List<SysplModuleMemu>>)sysplModOptMap.get(MODULE_OPERATE);
		//1,2,3级菜单分类
		Map modMenuMap = platformCommonService.findFilterModuleMenu(sysplModuleMemuList);
		//modMenuMap:1-list;2-map;3-map;4-map
		modMenuMap.put(FOURTH, modOptMap);
		
		//行
		StringBuffer ownerTypwUserPriBuf = new StringBuffer();
		String privilegeScopeFlag = "";
		String ownerTypeFlag = "";
		for(UumPrivilege uumPrivilege:uumPrivilegeRowList){//角色只有其一权限
			String ownerType = uumPrivilege.getOwnerType();
			String resourceId = uumPrivilege.getResourceId();
			String privilegeScopeRow = uumPrivilege.getPrivilegeScope();
			if(OWNER_TYPE_ROL.equals(ownerType)){
				privilegeScopeFlag = privilegeScopeRow;
				ownerTypeFlag = OWNER_TYPE_ROL;
				break;
			}else if(OWNER_TYPE_USR.equals(ownerType)){
				if(isEmpty(ownerTypeFlag) || isEmpty(privilegeScopeFlag) 
						|| !OWNER_TYPE_USR.equals(ownerTypeFlag)){//为空不等ownerTypeUser
					privilegeScopeFlag = privilegeScopeRow;
					ownerTypeFlag = OWNER_TYPE_USR;
				}
				ownerTypwUserPriBuf = ownerTypwUserPriBuf.length()==0
									 ?ownerTypwUserPriBuf.append(resourceId)
									 :ownerTypwUserPriBuf.append(","+resourceId);
			}
		}
		//行权限条件sql
		resourceIdBuf = new StringBuffer();
		resourceIdBuf.append(" AND ");
		if(OWNER_TYPE_ROL.equals(ownerTypeFlag)){
			if(PRIVILEGE_SCOPE_ROL.equals(privilegeScopeFlag)){
				resourceIdBuf.append(ROLE_ID + " = " + organRoleId + " ");//自我真实角色
			}else if(PRIVILEGE_SCOPE_USR.equals(privilegeScopeFlag)){
				resourceIdBuf.append(USER_ID + " = " + userId + " ");
			}else{
				String organChildren = getPrivilegeOrganChildren(organId);
				resourceIdBuf.append(ORGAN_ID + " in ( " + organChildren + ") ");
			}
		}else if(OWNER_TYPE_USR.equals(ownerTypeFlag)){
			if(PRIVILEGE_SCOPE_ROL.equals(privilegeScopeFlag)){
				resourceIdBuf.append(ROLE_ID + " in (" + ownerTypwUserPriBuf + ") ");
			}else if(PRIVILEGE_SCOPE_USR.equals(privilegeScopeFlag)){
				resourceIdBuf.append(USER_ID + " in (" + ownerTypwUserPriBuf + ") ");
			}else{
				resourceIdBuf.append(ORGAN_ID + " in (" + ownerTypwUserPriBuf + ") ");
			}
		}else{
			SystemConfigBean systemConfigBean = SystemConifgXmlParse.getSystemConfigBean();//系统配置权限
			String rowPrivilege = systemConfigBean.getRowPrivilegeLevel();
			if(PRIVILEGE_SCOPE_ROL.equals(rowPrivilege)){
				resourceIdBuf.append(ROLE_ID + " = " + organRoleId + " ");//自我真实角色
			}else if(PRIVILEGE_SCOPE_USR.equals(rowPrivilege)){
				resourceIdBuf.append(USER_ID + " = " + userId + " ");
			}else{
				String organChildren = getPrivilegeOrganChildren(organId);
				resourceIdBuf.append(ORGAN_ID + " in (" + organChildren + ") ");
			}
		}
		
		modMenuMap.put(PRIVILEGE_TYPE_ROW, resourceIdBuf.toString());
		return modMenuMap;
	}
	/**
	 * 功能说明：查询用户的机构上级
	 * @author jacobliang
	 * @param organId
	 * @return
	 * @time Feb 28, 2012 3:56:25 PM
	 */
	private String getPrivilegeOrganChildren(long organId){
		List<UumOrgan> organList = organService.findChildNodeList(organId, 0);
		StringBuffer orgSb = new StringBuffer();
		for(UumOrgan uumOrgan:organList){
			if(orgSb.length() > 0){
				orgSb.append(",").append(uumOrgan.getOrgId());
			}else{
				orgSb.append(uumOrgan.getOrgId());
			}
		}
		return orgSb.toString();
	}
	/**
	 * 功能说明：查询代理用户的权限
	 * @author jacobliang
	 * @param userId
	 * @param roleId
	 * @param organId
	 * @param organRoleId
	 * @param privilegeIdList
	 * @return
	 * @time Feb 24, 2012 11:39:43 AM
	 */
	public Map findUserPrivilegeForAgentLogin(long userId,long roleId,long organId,
											  long organRoleId,List privilegeIdList){
		//操作
		StringBuffer resourceIdBuf = new StringBuffer();
		String privilegeScope = "";
		for(int i=0,l=privilegeIdList.size();i<l;i++){
			String resourceId = privilegeIdList.get(i).toString();
			resourceIdBuf = resourceIdBuf.length()==0
							?resourceIdBuf.append(resourceId)
							:resourceIdBuf.append(","+resourceId);
		}
		List<SysplModOptRef> sysplModOptRefList = platformCommonService.findSysplModOptRefByModOptRefIds(resourceIdBuf.toString());
		//过滤所有的模块操作
		Map sysplModOptMap = this.findFilterModOpt(sysplModOptRefList,false);
		List<SysplModuleMemu> sysplModuleMemuList = (List<SysplModuleMemu>)sysplModOptMap.get(MODULE_MENU);
		Map<String,List<SysplModuleMemu>> modOptMap = (Map<String,List<SysplModuleMemu>>)sysplModOptMap.get(MODULE_OPERATE);
		//1,2,3级菜单分类
		Map modMenuMap = platformCommonService.findFilterModuleMenu(sysplModuleMemuList);
		//modMenuMap:1-list;2-map;3-map;4-map
		modMenuMap.put(FOURTH, modOptMap);
		
		//行
		//查找已经分配的权限
		StringBuffer ownerTypes = new StringBuffer();
		for(String ownerType:OWNER_TYPE_ARR){//拥有者类型数组
			ownerTypes = ownerTypes.length()==0
							?ownerTypes.append("'"+ownerType+"'")
							:ownerTypes.append(",'"+ownerType+"'");
		}
		List<UumPrivilege> uumPrivilegeRowList = 
							this.privilegeDao.findPrivilegeByOwnIdAPriType(userId+"", "'"+PRIVILEGE_TYPE_ROW+"'",ownerTypes.toString());
		
		StringBuffer ownerTypwUserPriBuf = new StringBuffer();
		String privilegeScopeFlag = "";
		String ownerTypeFlag = "";
		if(uumPrivilegeRowList != null){
			for(UumPrivilege uumPrivilege:uumPrivilegeRowList){//角色只有其一权限
				String ownerType = uumPrivilege.getOwnerType();
				String resourceId = uumPrivilege.getResourceId();
				String privilegeScopeRow = uumPrivilege.getPrivilegeScope();
				if(OWNER_TYPE_ROL.equals(ownerType)){
					privilegeScopeFlag = privilegeScopeRow;
					ownerTypeFlag = OWNER_TYPE_ROL;
					break;
				}else if(OWNER_TYPE_USR.equals(ownerType)){
					if(isEmpty(ownerTypeFlag) || isEmpty(privilegeScopeFlag) 
							|| !OWNER_TYPE_USR.equals(ownerTypeFlag)){//为空不等ownerTypeUser
						privilegeScopeFlag = privilegeScopeRow;
						ownerTypeFlag = OWNER_TYPE_USR;
					}
					ownerTypwUserPriBuf = ownerTypwUserPriBuf.length()==0
										 ?ownerTypwUserPriBuf.append(resourceId)
										 :ownerTypwUserPriBuf.append(","+resourceId);
				}
			}
		}
		//行权限条件sql
		resourceIdBuf = new StringBuffer();
		resourceIdBuf.append(" AND ");
		if(OWNER_TYPE_ROL.equals(ownerTypeFlag)){
			if(PRIVILEGE_SCOPE_ROL.equals(privilegeScopeFlag)){
				resourceIdBuf.append(ROLE_ID + " = " + organRoleId + " ");//自我真实角色
			}else if(PRIVILEGE_SCOPE_USR.equals(privilegeScopeFlag)){
				resourceIdBuf.append(USER_ID + " = " + userId + " ");
			}else{
				String organChildren = getPrivilegeOrganChildren(organId);
				resourceIdBuf.append(ORGAN_ID + " in (" + organChildren + ") ");
			}
		}else if(OWNER_TYPE_USR.equals(ownerTypeFlag)){
			if(PRIVILEGE_SCOPE_ROL.equals(privilegeScopeFlag)){
				resourceIdBuf.append(ROLE_ID + " in (" + ownerTypwUserPriBuf + ") ");
			}else if(PRIVILEGE_SCOPE_USR.equals(privilegeScopeFlag)){
				resourceIdBuf.append(USER_ID + " in (" + ownerTypwUserPriBuf + ") ");
			}else{
				resourceIdBuf.append(ORGAN_ID + " in (" + ownerTypwUserPriBuf + ") ");
			}
		}else{
			SystemConfigBean systemConfigBean = SystemConifgXmlParse.getSystemConfigBean();//系统配置权限
			String rowPrivilege = systemConfigBean.getRowPrivilegeLevel();
			if(PRIVILEGE_SCOPE_ROL.equals(rowPrivilege)){
				resourceIdBuf.append(ROLE_ID + " = " + organRoleId + " ");//自我真实角色
			}else if(PRIVILEGE_SCOPE_USR.equals(rowPrivilege)){
				resourceIdBuf.append(USER_ID + " = " + userId + " ");
			}else{
				String organChildren = getPrivilegeOrganChildren(organId);
				resourceIdBuf.append(ORGAN_ID + " in (" + organChildren + ") ");
			}
		}
		
		modMenuMap.put(PRIVILEGE_TYPE_ROW, resourceIdBuf.toString());
		return modMenuMap;
	}
	
	/**
	 * 功能说明：为角色或用户保存分配权限
	 * @author jacobliang
	 * @param modOptIds			模块操作关系IDS
	 * @param ownerId			用户或角色ID
	 * @param privilegeType		为用户或角色标识
	 * @throws ApplicationException
	 * @time Nov 1, 2010 10:26:30 AM
	 */
	@SuppressWarnings("unchecked")
	public void savePrivilege(List<String> modOptIds, long ownerId,
			String privilegeType,String ownerType,String privilegeScope) 
			throws ApplicationException {
		privilegeDao.deletePrivilege(ownerId, privilegeType,ownerType);//删除角色或用戶拥有操作权限
		//删除用户或角色代理权限
		userAgentService.deleteAgentByOwnerId(ownerId, ownerType);
		StringBuffer optContent = new StringBuffer();
		optContent.append("模块操作关系ID:");
		if(PRIVILEGE_SCOPE_ALL.equals(privilegeScope)){
			UumPrivilege uumPrivilege = new UumPrivilege();
			uumPrivilege.setResourceId(PRIVILEGE_SCOPE_ALL);
			uumPrivilege.setOwnerId(ownerId);
			uumPrivilege.setOwnerType(ownerType);
			uumPrivilege.setPrivilegeScope(privilegeScope);
			uumPrivilege.setPrivilegeType(privilegeType);
			this.privilegeDao.save(uumPrivilege);
		}else{
			if(modOptIds != null && modOptIds.size()>0){
				List<UumPrivilege> listUumPrivilege = new ArrayList<UumPrivilege>();
				for(int i=0,j=modOptIds.size();i<j;i++){
					String modOptId = modOptIds.get(i);
					if(modOptId == null)continue;
					UumPrivilege uumPrivilege = new UumPrivilege();
					uumPrivilege.setResourceId(modOptId);
					uumPrivilege.setOwnerId(ownerId);
					uumPrivilege.setOwnerType(ownerType);
					uumPrivilege.setPrivilegeScope(privilegeScope);
					uumPrivilege.setPrivilegeType(privilegeType);
					
					listUumPrivilege.add(uumPrivilege);
					optContent.append(modOptId+SPLIT);
				}
				 this.privilegeDao.batchSave(listUumPrivilege);
				this.saveLog(LOG_LEVEL_FIRST, MOD_ASSIGN_OPT, SAVE_OPT, 
						optContent.toString(), ownerId+SPLIT+privilegeType);
			}
		}
	}
	/**
	 * 功能说明：过滤所有的模块操作，构成模块树，并获得相应模块下的操作
	 * @author jacobliang
	 * @param sysplModOptRefList	系统模块操作关系列表
	 * @param isTreeFlag			是否为树标志
	 * @return
	 * @time Nov 1, 2010 1:44:44 PM
	 */
	public Map findFilterModOpt(List<SysplModOptRef> sysplModOptRefList,boolean isTreeFlag){
		Map<String,List<SysplModuleMemu>> sysplModOptMap = new HashMap<String,List<SysplModuleMemu>>();//四级模块操作 
		List<SysplModuleMemu> sysplModuleMemuList = new ArrayList<SysplModuleMemu>();//模块菜单list
		for(SysplModOptRef sysplModOptRef:sysplModOptRefList){
			SysplModuleMemu sysplModuleMemu3 = sysplModOptRef.getSysplModuleMemu();
			SysplOperate sysplOperate = sysplModOptRef.getSysplOperate();
			//操作按钮
			SysplModuleMemu sysplModuleMemuOpt = new SysplModuleMemu();
			sysplModuleMemuOpt.setModuleId(sysplModOptRef.getModOptId()+1000);//将模块操作关系的id加上1000,防id重复
			sysplModuleMemuOpt.setModName(sysplOperate.getOperateName());
			sysplModuleMemuOpt.setModImgCls(sysplOperate.getOptImgLink());
			sysplModuleMemuOpt.setOptFunLink(sysplOperate.getOptFunLink());////为操作准备功能链接
			sysplModuleMemuOpt.setUpModId(sysplModuleMemu3.getModuleId());//将第三级菜单设置为操作上级
			sysplModuleMemuOpt.setModOrder(sysplOperate.getOptOrder());//操作按钮的顺序
			if(isTreeFlag){//如果为树则加入模块list
				sysplModuleMemuList.add(sysplModuleMemuOpt);
			}else{//不为树则维护操作按钮map
				String modEnId = sysplModuleMemu3.getModEnId();//模块英文id,作为操作列表map的key值
				if(sysplModOptMap.containsKey(modEnId)){
					List<SysplModuleMemu> modOptList = sysplModOptMap.get(modEnId);
					//根据操作顺序进行排序
					int listIndex = 0;//元素索引
					boolean orderBol = true;//是否插入指定索引元素
					for(SysplModuleMemu sysplModuleMemuOptt : modOptList){					
						int listModOrder = sysplModuleMemuOptt.getModOrder();//已经有的操作顺序
						int currModOrder = sysplModuleMemuOpt.getModOrder();//当前操作顺序
						if(listModOrder > currModOrder){
							modOptList.add(listIndex,sysplModuleMemuOpt);//顺序小的插在前
							orderBol = false;
							break;
						}
						listIndex++;
					}
					if(orderBol)
						modOptList.add(sysplModuleMemuOpt);//加到操作list中
				}else{
					List<SysplModuleMemu> modOptList = new ArrayList<SysplModuleMemu>();//操作list
					modOptList.add(sysplModuleMemuOpt);//加到操作list中
					sysplModOptMap.put(modEnId, modOptList);
				}
			}
			//如果包含相应菜单项则加入相应模块菜单
			if(!sysplModuleMemuList.contains(sysplModuleMemu3)){
				sysplModuleMemuList.add(sysplModuleMemu3);
				SysplModuleMemu sysplModuleMemu2 =  sysplModuleMemu3.getSysplModuleMemu();
				sysplModuleMemu3.setUpModId(sysplModuleMemu2.getModuleId());
				if(!sysplModuleMemuList.contains(sysplModuleMemu2)){
					sysplModuleMemuList.add(sysplModuleMemu2);
					SysplModuleMemu sysplModuleMemu1 =  sysplModuleMemu2.getSysplModuleMemu();
					sysplModuleMemu2.setUpModId(sysplModuleMemu1.getModuleId());
					if(!sysplModuleMemuList.contains(sysplModuleMemu1)){
						sysplModuleMemu1.setUpModId(sysplModuleMemu1.getSysplModuleMemu().getModuleId());
						sysplModuleMemuList.add(sysplModuleMemu1);
					}
				}
			}
			
		}
		Collections.sort(sysplModuleMemuList,new SysplModuleMemu());
		Map moduleOptMap = new HashMap();
		moduleOptMap.put(MODULE_MENU, sysplModuleMemuList);
		moduleOptMap.put(MODULE_OPERATE, sysplModOptMap);
		return moduleOptMap;
	}
	/**
	 * 功能说明：查询判断用户是否有特殊操作权限
	 * @author jacobliang
	 * @param userId		用户或角色ID
	 * @return true 有 false 无
	 * @time Nov 3, 2010 8:16:42 PM
	 */
	public boolean findUserHasSpecialPrivilege(long userId) {
		int countPri = this.privilegeDao.findUserSpecialPrivilegeByUserId(userId);
		if(countPri > 0)
			return true;
		return false;
	}
	
	/**
	 * 功能说明：根据用户或角色ID 和 标识删除已经分配的模块操作权限
	 * @author jacobliang
	 * @param ownerId			用户或角色ID
	 * @param privilegeType		为用户或角色标识
	 * @throws ApplicationException
	 * @time Nov 1, 2010 10:31:20 AM
	 */
	public void deletePrivilege(long ownerId,String privilegeType,String ownerType)throws ApplicationException{
		this.privilegeDao.deletePrivilege(ownerId, privilegeType,ownerType);
	}
	
	/**
	 * 功能说明：删除模块操作关系时同时也删除模块操作权限
	 * @author jacobliang
	 * @param modOptRefId
	 * @time Feb 8, 2012 1:09:22 PM
	 */
	public void deletePrivilegeByModOptId(String modOptRefId){
		if(isEmpty(modOptRefId))return;
		this.privilegeDao.deletePrivilegeByModOptId(modOptRefId);
	}
	
	/**
	 * 功能说明：查询角色的行权限
	 * @author jacobliang
	 * @param roleId			角色ID
	 * @return	String
	 * @time Nov 1, 2010 11:36:34 AM
	 */
	public String findRowPrivilegeByRoleIdAPriType(long roleId){
		//查找已经分配的权限
		List<UumPrivilege> uumPrivilegeOptList = 
							this.privilegeDao.findPrivilegeByOwnIdAPriType(roleId+"", "'"+PRIVILEGE_TYPE_ROW+"'","'"+OWNER_TYPE_ROL+"'");
		if(uumPrivilegeOptList.isEmpty())return PRIVILEGE_SCOPE_ORG;
		UumPrivilege uumPrivilege = uumPrivilegeOptList.get(0);
		String resourceId = uumPrivilege.getResourceId();
		return resourceId;
	}
	/**
	 * 功能说明：根据拥有者ID和拥有者类型删除用户或角色的所有权限
	 * @author jacobliang
	 * @param ownerId	拥有者ID
	 * @param ownerType 拥有者类型
	 * @throws ApplicationException
	 * @time Feb 29, 2012 2:21:45 PM
	 */
	public void deletePrivilegeByOwnerIdType(long ownerId,String ownerType)throws ApplicationException{
		this.privilegeDao.deletePrivilegeByOwnerIdType(ownerId, ownerType);
	}
	/**
	 * 功能说明：停用机构时根据机构ID删除行权限
	 * @author jacobliang
	 * @param resourceId
	 * @throws ApplicationException
	 * @time Mar 2, 2012 4:13:20 PM
	 */
	public void deleteRowPriByResource(String resourceId)throws ApplicationException{
		this.privilegeDao.deleteRowPriByResource(resourceId);
	}
	public void setPrivilegeDao(PrivilegeDao privilegeDao) {
		this.privilegeDao = privilegeDao;
	}

	public void setPlatformCommonService(PlatformCommonService platformCommonService) {
		this.platformCommonService = platformCommonService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setRoleOrganService(RoleOrganService roleOrganService) {
		this.roleOrganService = roleOrganService;
	}
	public void setOrganService(OrganService organService) {
		this.organService = organService;
	}
	public void setUserAgentService(UserAgentService userAgentService) {
		this.userAgentService = userAgentService;
	}
}
