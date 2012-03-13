/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 23, 2010 @3:22:30 PM
 */
package com.bhtec.service.impl.uum.role;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.DELETE_OPT;
import static com.bhtec.common.constant.Common.LOG_LEVEL_FIRST;
import static com.bhtec.common.constant.Common.LOG_LEVEL_THIRD;
import static com.bhtec.common.constant.Common.SAVE_OPT;
import static com.bhtec.common.constant.Common.SPLIT;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.constant.PojoVariable.UUM_ORG_ROLE_REF;
import static com.bhtec.common.constant.ServiceVariable.ROLE_ASSIGN;
import static com.bhtec.common.tools.UtilTools.isNullOrEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bhtec.dao.iface.uum.role.RoleDao;
import com.bhtec.dao.iface.uum.role.RoleOrganDao;
import com.bhtec.domain.pojo.uum.UumOrgRoleRef;
import com.bhtec.domain.pojo.uum.UumOrgan;
import com.bhtec.domain.pojo.uum.UumRole;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.uum.privilege.PrivilegeService;
import com.bhtec.service.iface.uum.role.RoleOrganService;
import com.bhtec.service.iface.uum.role.RoleUserService;
import com.bhtec.service.impl.BaseServiceImpl;

public class RoleOrganServiceImpl extends BaseServiceImpl implements RoleOrganService {
	Logger log = Logger.getLogger(this.getClass());
	private RoleOrganDao roleOrganDao;
	private RoleDao roleDao;
	private RoleUserService roleUserService;

	public void setRoleOrganDao(RoleOrganDao roleOrganDao) {
		this.roleOrganDao = roleOrganDao;
	}

	/**
	 * 功能说明：根据机构ID和角色ID删除机构下的角色
	 * @author jacobliang
	 * @param orgId		机构ID
	 * @param roleId	角色ID
	 * @throws ApplicationException
	 * @return boolean true 角色下有用户 false 角色下无用户
	 * @time Sep 28, 2010 10:32:08 AM
	 */
	public boolean deleteOrganRoleRef(long orgId, List<Long> roleIds)
			throws ApplicationException {
		List<UumOrgRoleRef> uumOrgRoleRefList = new ArrayList<UumOrgRoleRef>();
		StringBuffer logContent = new StringBuffer();
		logContent.append("删除机构下的角色ID：");
		for(long roleId:roleIds){
			UumOrgRoleRef uumOrgRoleRef = this.roleOrganDao.findOrganRoleRefByOrgRolId(orgId, roleId);
			int uumRoleUserCount = roleUserService.findRoleUserRefById(uumOrgRoleRef.getOrgRoleId());
			if(uumRoleUserCount == 0){//角色下无用户
				uumOrgRoleRefList.add(uumOrgRoleRef);
			}else{//角色下有用户
				return true;
			}
			logContent.append(roleId+SPLIT);
		}
		this.roleOrganDao.deleteAll(uumOrgRoleRefList);//删除所有机构角色
		saveLog(LOG_LEVEL_THIRD, ROLE_ASSIGN,DELETE_OPT,logContent.toString(), orgId+"");
		return false;
	}

	/**
	 * 功能说明：根据机构ID和机构名称查询已分配的角色
	 * @author jacobliang
	 * @param start		翻页开始ID
	 * @param limit		每页记录数
	 * @param treeId	机构ID
	 * @param orgName	查询机构名称
	 * @return
	 * @time Sep 28, 2010 10:22:02 AM
	 */
	public Map findSelectedRoleByOrgId(int start,int limit,long orgId,String orgName) {
		Map map = this.roleOrganDao.findSelectedRoleByOrgId(start, limit, orgId, orgName);
		List<UumOrgRoleRef> listOrgRoleRef = (List<UumOrgRoleRef>)map.get(BUSI_LIST);;
		List<UumRole> roleList = new ArrayList<UumRole>();
		if(!listOrgRoleRef.isEmpty()){
			for(UumOrgRoleRef uumOrgRoleRef:listOrgRoleRef){
				roleList.add(uumOrgRoleRef.getUumRole());
			}
		}
		map.put(BUSI_LIST, roleList);
		return map;
	}
	
	/**
	 * 功能说明：根据机构ID查询其下已有的角色信息
	 * @author jacobliang
	 * @param orgId
	 * @return
	 * @time Nov 17, 2010 1:53:06 PM
	 */
	private List<UumRole> findSelectedRoleByOrgId(long orgId) {
		List<UumOrgRoleRef> listOrgRoleRef = this.roleOrganDao.findSelectedRoleByOrgId(orgId);
		List<UumRole> roleList = new ArrayList<UumRole>();
		if(!listOrgRoleRef.isEmpty()){
			for(UumOrgRoleRef uumOrgRoleRef:listOrgRoleRef){
				roleList.add(uumOrgRoleRef.getUumRole());
			}
		}
		return roleList;
	}

	/**
	 * 功能说明：根据机构ID查询未分配的角色
	 * @author jacobliang
	 * @param start		翻页开始ID
	 * @param limit		每页记录数
	 * @param orgId		机构ID
	 * @return
	 * @time Sep 28, 2010 10:27:13 AM
	 */
	public Map findUnselectedRoleByOrgId(int start,int limit,long orgId) {
		List<UumRole> allRoleList = roleDao.findAllRole();
		List<UumRole> selectedRoleList = findSelectedRoleByOrgId(orgId);
		allRoleList.removeAll(selectedRoleList);
		List<UumRole> newRoleList = new ArrayList<UumRole>();
		int count = 0;
		for(UumRole uumRole:allRoleList){
			count++;
			if(count >= (start+1) && count <= (limit+start)){
				newRoleList.add(uumRole);
			}
		}
		Map map = new HashMap();
		map.put(BUSI_LIST, newRoleList);
		map.put(TOTAL_PROPERTY, allRoleList.size());
		return map;
	}

	/**
	 * 功能说明：保存为机构分配的角色
	 * @author jacobliang
	 * @param orgRoleId	机构角色关系ID
	 * @param orgId		机构ID
	 * @throws ApplicationException
	 * @time Sep 28, 2010 10:30:16 AM
	 */
	public void saveOrganRoleRef(long orgId,List<Long> roleIds)
					throws ApplicationException {
		if(!roleIds.isEmpty()){
			List<UumOrgRoleRef> uumOrgRoleRefList = new ArrayList<UumOrgRoleRef>();
			StringBuffer logContent = new StringBuffer();
			for(Long roleId:roleIds){
				UumOrgRoleRef uumOrgRoleRef = new UumOrgRoleRef();
				UumRole uumRole = new UumRole();
				uumRole.setRoleId(roleId);
				
				UumOrgan uumOrgan = new UumOrgan();
				uumOrgan.setOrgId(orgId);
				
				uumOrgRoleRef.setUumRole(uumRole);
				uumOrgRoleRef.setUumOrgan(uumOrgan);
				
				uumOrgRoleRefList.add(uumOrgRoleRef);
				
				logContent.append("机构id:"+orgId+SPLIT).append("角色id:"+roleId+SPLIT);
			}
			saveLog(LOG_LEVEL_FIRST, ROLE_ASSIGN,SAVE_OPT,logContent.toString(), "");
			this.roleOrganDao.batchSave(uumOrgRoleRefList);
		}
	}
	
	/**
	 * 功能说明：根据机构id查询机构角色关系
	 * @author jacobliang
	 * @param orgId
	 * @return
	 * @time Sep 30, 2010 6:55:21 PM
	 */
	public List<UumOrgRoleRef> findRoleOrganListByOrgId(long orgId){
		return this.roleOrganDao.findRoleOrganListByOrgId(orgId);
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	/**
	 * 功能说明：根据机构角色ID查询机构角色
	 * @author jacobliang
	 * @param orgRoleId
	 * @return
	 * @time Oct 11, 2010 8:47:58 PM
	 */
	public UumOrgRoleRef findUumOrgRoleRefById(long orgRoleId) {
		return (UumOrgRoleRef)this.roleOrganDao.getPojoById("com.bhtec.domain.pojo.uum.UumOrgRoleRef", orgRoleId);
	}
	
	/**
	 * 功能说明：根据机构ID查询其下的角色数量
	 * @author jacobliang
	 * @param organId
	 * @return
	 * @time Nov 10, 2010 10:32:41 AM
	 */
	public int findUumOrgRoleRefTotalByOrgId(long organId){
		return roleOrganDao.findUumOrgRoleRefTotalByOrgId(organId);
	}
	
	/**
	 * 功能说明：根据角色ID删除机构角色关系记录，为停用角色所用
	 * @author jacobliang
	 * @param roleId
	 * @time Nov 10, 2010 2:27:47 PM
	 */
	public void deleteOrgRoleRefByRoleId(long roleId){
		this.roleOrganDao.deleteOrgRoleRefByRoleId(roleId);
	}
	/**
	 * 功能说明：根据机构ID和角色ID查询机构角色
	 * @author jacobliang
	 * @param orgId		机构ID
	 * @param roleId	角色ID
	 * @throws ApplicationException
	 * @time Sep 28, 2010 10:32:08 AM
	 */
	public UumOrgRoleRef findOrganRoleRefByOrgRolId(long organId,long roleId)throws ApplicationException{
		return this.roleOrganDao.findOrganRoleRefByOrgRolId(organId, roleId);
	}
	
	/**
	 * 功能说明：根据多个机构角色ID查询机构角色信息
	 * @author jacobliang
	 * @param orgRoleIds
	 * @return
	 * @time Feb 14, 2012 5:19:59 PM
	 */
	public List<UumOrgRoleRef> findUumOrgRoleRefByOrgRoleIds(String orgRoleIds){
		if(isNullOrEmpty(orgRoleIds))return null;
		return this.roleOrganDao.findUumOrgRoleRefByOrgRoleIds(orgRoleIds);
	}
	
	/**
	 * 功能说明：根据角色ID查询所有机构角色关系ID
	 * @author jacobliang
	 * @param roleId
	 * @return
	 * @time Mar 2, 2012 6:17:03 PM
	 */
	public List findUumOrgRoleRefByRoleId(String roleId){
		return this.roleOrganDao.findUumOrgRoleRefByRoleId(roleId);
	}

	public void setRoleUserService(RoleUserService roleUserService) {
		this.roleUserService = roleUserService;
	}
}
