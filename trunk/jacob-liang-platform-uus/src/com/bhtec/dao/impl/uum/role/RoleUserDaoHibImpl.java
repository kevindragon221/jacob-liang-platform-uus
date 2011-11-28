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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bhtec.dao.iface.uum.role.RoleOrganDao;
import com.bhtec.dao.iface.uum.role.RoleUserDao;
import com.bhtec.dao.impl.BaseDaoHibImpl;
import com.bhtec.domain.pojo.uum.UumOrgRoleRef;
import com.bhtec.domain.pojo.uum.UumOrgan;
import com.bhtec.domain.pojo.uum.UumRoleUserRef;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;

public class RoleUserDaoHibImpl extends BaseDaoHibImpl implements RoleUserDao {
	Logger log = Logger.getLogger(this.getClass());

	public void deleteRoleUserRef(long userId) throws ApplicationException {
		String hqlString = "delete from UumRoleUserRef rur where rur.uumUser.userId = "+userId;
		this.excuteHql(hqlString);
	}

	public List<UumRoleUserRef> findRoleUserListByUserId(long userId) {
		String queryString = "from UumRoleUserRef rur where rur.uumUser.userId = "+userId;
		List<UumRoleUserRef> roleUserRefList = this.findByProperty(queryString);
		return roleUserRefList;
	}
	
	public int findRoleUserRefById(long orgRoleId){
		String queryString = "select count(*) from UumRoleUserRef rur where rur.uumOrgRoleRef.orgRoleId = "+orgRoleId;
		return this.getRowCount(queryString);
	}
	
	public int findRoleUserRefTotalByRoleId(long roleId){
		String queryString = "select count(*) from UumRoleUserRef rur where rur.uumOrgRoleRef.orgRoleId in ("+
							 "select orr.orgRoleId from UumOrgRoleRef orr where orr.uumRole.roleId = " +roleId+")";
		return this.getRowCount(queryString);
	}
	
}
