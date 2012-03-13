/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 23, 2010 @3:08:56 PM
 */
package com.bhtec.dao.impl.uum.role;

import java.util.List;

import org.apache.log4j.Logger;

import com.bhtec.dao.iface.uum.role.RoleUserDao;
import com.bhtec.dao.impl.BaseDaoHibImpl;
import com.bhtec.domain.pojo.uum.UumRoleUserRef;
import com.bhtec.exception.ApplicationException;

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
	public UumRoleUserRef findRoleUserByUserIdAOrgRoleId(long userId,long orgRoleId){
		String queryString = "from UumRoleUserRef rur " +
							 "where rur.uumUser.userId = "+userId+" "+
							 "and rur.uumOrgRoleRef.orgRoleId = "+orgRoleId;
		return (UumRoleUserRef)this.getSingleRowRecord(queryString);
	}
}
