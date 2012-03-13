/**
 *功能说明：
 * @author jacobliang
 * @time @Nov 1, 2010 @11:43:13 AM
 */
package com.bhtec.dao.impl.uum.privilege;

import static com.bhtec.common.constant.Common.*;
import static com.bhtec.common.constant.Common.PRIVILEGE_TYPE_OPT;

import java.util.List;

import com.bhtec.dao.iface.uum.privilege.PrivilegeDao;
import com.bhtec.dao.impl.BaseDaoHibImpl;
import com.bhtec.domain.pojo.uum.UumPrivilege;
import com.bhtec.exception.ApplicationException;

public class PrivilegeDaoHibImpl extends BaseDaoHibImpl implements PrivilegeDao {

	public void deletePrivilege(long ownerId, String privilegeType,String ownerType)
			throws ApplicationException {
		String hqlString = "delete from UumPrivilege pri " +
						   " where pri.ownerId =  " +ownerId+
						   " and pri.privilegeType =  '"+privilegeType+"' " +
						   " and pri.ownerType = '"+ownerType+"'";
		this.excuteHql(hqlString);
	}
	
	public void deletePrivilegeByOwnerIdType(long ownerId,String ownerType)
			throws ApplicationException {
		String hqlString = "delete from UumPrivilege pri " +
						   " where pri.ownerId =  " +ownerId+
						   " and pri.ownerType = '"+ownerType+"'";
		this.excuteHql(hqlString);
	}
	
	public void deleteRowPriByResource(String resourceId)
			throws ApplicationException {
		String hqlString = "delete from UumPrivilege pri " +
						   " where pri.resourceId =  '" +resourceId+"'"+
						   " and pri.privilegeScope = '"+PRIVILEGE_SCOPE_ORG+"' " +
						   " and pri.privilegeType = '"+PRIVILEGE_TYPE_ROW+"'";
		this.excuteHql(hqlString);
	}

	public List<UumPrivilege> findPrivilegeByOwnIdAPriType(String ownerId,
			String privilegeType,String ownerType) {
		String queryString = "from UumPrivilege pri where pri.ownerId in ("+ownerId+") " +
							 "and pri.privilegeType in ("+privilegeType+") " +
							 "and pri.ownerType in ("+ownerType+") " +
							 "order by pri.privilegeId";
		List<UumPrivilege> uumPrivilegeList = this.findByProperty(queryString);
		return uumPrivilegeList;
	}

	public int findUserSpecialPrivilegeByUserId(long userId) {
		String queryString = "select count(*) from UumPrivilege pri " +
							 "where pri.ownerId = "+userId +
							 " and pri.ownerType = '"+OWNER_TYPE_USR+"'";
		return this.getRowCount(queryString);
	}
	
	public void deletePrivilegeByModOptId(String modOptRefId){
		String hqlString = "delete from UumPrivilege pri " +
						   " where pri.resourceId in (" +modOptRefId+")"+
						   " and pri.privilegeType =  '"+PRIVILEGE_TYPE_OPT+"' " ;
		this.excuteHql(hqlString);
	}
}
