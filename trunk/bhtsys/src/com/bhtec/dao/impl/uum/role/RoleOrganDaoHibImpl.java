/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 23, 2010 @3:08:56 PM
 */
package com.bhtec.dao.impl.uum.role;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.tools.UtilTools.isEmpty;
import static com.bhtec.common.tools.UtilTools.isNullOrEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bhtec.dao.iface.uum.role.RoleOrganDao;
import com.bhtec.dao.impl.BaseDaoHibImpl;
import com.bhtec.domain.pojo.uum.UumOrgRoleRef;
import com.bhtec.domain.pojo.uum.UumOrgan;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;

public class RoleOrganDaoHibImpl extends BaseDaoHibImpl implements RoleOrganDao {
	Logger log = Logger.getLogger(this.getClass());
	
	public UumOrgRoleRef findOrganRoleRefByOrgRolId(long orgId, long roleId)
			throws ApplicationException {
		String hqlString = "from UumOrgRoleRef uumOrgRoleRef " +
						   "where uumOrgRoleRef.uumOrgan.orgId = " + orgId+
						   "and uumOrgRoleRef.uumRole.roleId = " + roleId;
		List<UumOrgRoleRef> list = (List<UumOrgRoleRef>)this.findByProperty(hqlString);
		if(list != null){
			if(list.size()>1){
				throw new ApplicationException("同一机构下拥有相同的角色");
			}else{
				return list.get(0);
			}
		}
		return new UumOrgRoleRef();
	}

	public List<UumOrgRoleRef> findSelectedRoleByOrgId(long orgId) {
		String queryString = "from UumOrgRoleRef uumOrgRoleRef " +
							 "where uumOrgRoleRef.uumOrgan.orgId = ?  " +
							 "order by uumOrgRoleRef.orgRoleId desc";
		return this.findByPropertyWithParas(queryString, new Long[]{orgId});
	}
	
	public Map findSelectedRoleByOrgId(int start,int limit,long orgId,String orgName) {
		String queryString = "from UumOrgRoleRef uumOrgRoleRef " +
							 "where uumOrgRoleRef.uumOrgan.orgId = " + orgId +
							 " and uumOrgRoleRef.orgRoleId != 0";
		List params = new ArrayList();
		if(!isNullOrEmpty(orgName)){
			queryString += " and uumOrgRoleRef.uumRole.roleName like ?";
			params.add("%"+orgName+"%");
		}
		
		String countSql = "select count(*) " + queryString;
		queryString += " order by uumOrgRoleRef.orgRoleId desc"; 
		List<UumOrgRoleRef> limitList =  this.findByHqlWithPagination(start, limit, queryString,params);//分页
		
		int totalProperty = this.getRowCount(countSql,params);//总记录数
		Map map = new HashMap();
		map.put(TOTAL_PROPERTY, totalProperty);
		map.put(BUSI_LIST, limitList);
		return map;
	}
	
	public List<UumOrgRoleRef> findRoleOrganListByOrgId(long orgId){
		String roleQueryString = "from UumOrgRoleRef uumOrgRoleRef " +
							 	 "where uumOrgRoleRef.uumOrgan.orgId = "+orgId;
		List<UumOrgRoleRef> orgRolelist = this.findByProperty(roleQueryString);
		return orgRolelist;
	}
	/**
	 * 功能说明：根据机构ID查询其下的角色数量
	 * @author jacobliang
	 * @param organId
	 * @return
	 * @time Nov 10, 2010 10:32:41 AM
	 */
	public int findUumOrgRoleRefTotalByOrgId(long organId){
		String queryString = "select count(*) from UumOrgRoleRef uumOrgRoleRef " +
	 	 					 "where uumOrgRoleRef.uumOrgan.orgId = "+organId;
		return this.getRowCount(queryString);
	}
	/**
	 * 功能说明：根据角色ID删除机构角色关系记录，为停用角色所用
	 * @author jacobliang
	 * @param roleId
	 * @time Nov 10, 2010 2:27:47 PM
	 */
	public void deleteOrgRoleRefByRoleId(long roleId){
		String hqlString = "delete from UumOrgRoleRef uumOrgRoleRef " +
						   "where uumOrgRoleRef.uumRole.roleId = " + roleId;
		this.excuteHql(hqlString);
	}
	/**
	 * 功能说明：根据多个机构角色ID查询机构角色信息
	 * @author jacobliang
	 * @param orgRoleIds
	 * @return
	 * @time Feb 14, 2012 5:19:59 PM
	 */
	public List<UumOrgRoleRef> findUumOrgRoleRefByOrgRoleIds(String orgRoleIds){
		String roleQueryString = "from UumOrgRoleRef uumOrgRoleRef " +
			 	 				 "where uumOrgRoleRef.orgRoleId in("+orgRoleIds+")";
		List<UumOrgRoleRef> orgRolelist = this.findByProperty(roleQueryString);
		return orgRolelist;
	}
	
	public List findUumOrgRoleRefByRoleId(String roleId){
		String roleQueryString = "select orgRoleId from UumOrgRoleRef uumOrgRoleRef " +
			 	 				 "where uumOrgRoleRef.uumRole.roleId = "+roleId;
		List orgRolelist = this.findByProperty(roleQueryString);
		return orgRolelist;
	}
}
