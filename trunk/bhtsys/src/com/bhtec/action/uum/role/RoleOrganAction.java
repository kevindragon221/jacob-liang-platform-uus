/**
 *功能说明：分配角色管理
 * @author jacobliang
 * @time @Sep 28, 2010 @3:33:09 PM
 */
package com.bhtec.action.uum.role;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bhtec.action.uum.UumBaseAction;
import com.bhtec.domain.pojo.uum.UumRole;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;
import com.bhtec.service.iface.uum.role.RoleOrganService;

public class RoleOrganAction extends UumBaseAction{
	private static final long serialVersionUID = 1000000L;
	private Logger log = Logger.getLogger(this.getClass());
	private List<UumRole> roleList;
	private String roleName;
	private int count;
	private String failMesg;
	private RoleOrganService roleOrganService;
	private long orgId;
	private long roleId;
	private List<Long> roleIds = new ArrayList<Long>();
	private boolean roleHasUser;
	/**
	 * 功能说明：保存机构角色关系
	 * @author jacobliang
	 * @time @Sep 28, 2010 6:03:41 PM
	 */
	public void saveOrganRoleRef(){
		try {
			roleOrganService.setHttpServletRequest(this.getHttpServletRequest());
			roleOrganService.saveOrganRoleRef(orgId, roleIds);
			this.returnSuccess();
		} catch (ApplicationException e) {
			e.printStackTrace();
			this.returnFailur(e.toString());
		}catch (SystemException e) {
			e.printStackTrace();
			this.returnFailur(this.ERROR);
		}
	}
	
	/**
	 * 功能说明：查询指定机构下的角色
	 * @author jacobliang
	 * @throws 
	 * @time @Sep 28, 2010 6:47:32 PM
	 */
	public String findSelectedRoleByOrgId(){
		try {
			Map map = this.roleOrganService.findSelectedRoleByOrgId(getStart(),getLimit(),getTreeId(),roleName);
			roleList = (List<UumRole>)map.get(BUSI_LIST);
			count = (Integer)map.get(TOTAL_PROPERTY);
		}catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("findSelectedRoleByOrgId() occur error. ", e);
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：查询指定机构下的未分配角色
	 * @author jacobliang
	 * @return
	 * @time @Sep 28, 2010 6:47:32 PM
	 */
	public String findUnselectedRoleByOrgId(){
		try {
			Map map = this.roleOrganService.findUnselectedRoleByOrgId(getStart(),getLimit(),orgId);
			roleList = (List<UumRole>)map.get(BUSI_LIST);
			count = (Integer)map.get(TOTAL_PROPERTY);
		}catch (SystemException e) {
			e.printStackTrace();
			log.error("findUnselectedRoleByOrgId() occur error. ", e);
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：删除为机构分配的角色
	 * @author jacobliang
	 * @throws 
	 * @time Sep 22, 2010 1:29:34 PM
	 */
	public String deleteOrganRoleRef(){
		try {
			roleOrganService.setHttpServletRequest(this.getHttpServletRequest());
			roleHasUser = roleOrganService.deleteOrganRoleRef(orgId, roleIds);
		} catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	public List<UumRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<UumRole> roleList) {
		this.roleList = roleList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getFailMesg() {
		return failMesg;
	}

	public void setFailMesg(String failMesg) {
		this.failMesg = failMesg;
	}

	public void setRoleOrganService(RoleOrganService roleOrganService) {
		this.roleOrganService = roleOrganService;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public boolean isRoleHasUser() {
		return roleHasUser;
	}

	public void setRoleHasUser(boolean roleHasUser) {
		this.roleHasUser = roleHasUser;
	}

}
