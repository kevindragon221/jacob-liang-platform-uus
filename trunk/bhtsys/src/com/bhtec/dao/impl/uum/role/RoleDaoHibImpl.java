/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 23, 2010 @3:08:56 PM
 */
package com.bhtec.dao.impl.uum.role;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.constant.PojoVariable.UUM_ROLE;
import static com.bhtec.common.tools.UtilTools.isEmpty;
import static com.bhtec.common.tools.UtilTools.isNullOrEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.uum.role.RoleDao;
import com.bhtec.dao.impl.BaseDaoHibImpl;
import com.bhtec.domain.pojo.uum.UumOrgan;
import com.bhtec.domain.pojo.uum.UumRole;
public class RoleDaoHibImpl extends BaseDaoHibImpl implements RoleDao {

	public List<UumRole> findAllRole() {
		String queryString = "from UumRole uumRole where uumRole.roleId != 0 ";
		List<UumRole> roleList = this.findByProperty(queryString);
		return roleList;
	}

	public Map findRoleByCon(int start, int limit, String roleName) {
		String queryString = "from UumRole uumRole where uumRole.roleId != 0 ";
		List params = new ArrayList();
		if(!isNullOrEmpty(roleName)){
			queryString += " and uumRole.roleName like ?";
			params.add("%"+roleName+"%");
		}
		
		String countSql = "select count(*) " + queryString;
		queryString += " order by uumRole.roleId desc"; 
		List<UumOrgan> limitList =  this.findByHqlWithPagination(start, limit, queryString,params);//分页
		int totalProperty = this.getRowCount(countSql,params);//总记录数
		Map map = new HashMap();
		map.put(TOTAL_PROPERTY, totalProperty);
		map.put(BUSI_LIST, limitList);
		return map;
	}

	public int findRoleCountByRoleName(String roleName) {
		if(isNullOrEmpty(roleName))return 0;
		String queryString = "select count(*) from UumRole uumRole " +
				 			 "where uumRole.roleName = '"+roleName+"'";
		return this.getRowCount(queryString);
	}

	

}
