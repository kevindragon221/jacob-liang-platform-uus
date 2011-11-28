/**
 *功能说明：
 * @author jacobliang
 * @time @Nov 1, 2010 @11:43:13 AM
 */
package com.bhtec.dao.impl.uum.privilege;

import java.util.ArrayList;
import java.util.List;

import com.bhtec.dao.iface.uum.privilege.PrivilegeDao;
import com.bhtec.dao.impl.BaseDaoHibImpl;
import com.bhtec.domain.pojo.platform.SysplModOptRef;
import com.bhtec.domain.pojo.uum.UumPrivilege;
import com.bhtec.exception.ApplicationException;
import static com.bhtec.common.constant.ActionVariable.*;

public class PrivilegeDaoHibImpl extends BaseDaoHibImpl implements PrivilegeDao {

	public void deletePrivilege(long ownerId, String privilegeType)
			throws ApplicationException {
		String hqlString = "delete from UumPrivilege pri " +
						   " where pri.ownerId =  " +ownerId+
						   " and pri.privilegeType =  '"+privilegeType+"'";
		this.excuteHql(hqlString);
	}

	public List<SysplModOptRef> findPrivilegeByOwnIdAPriType(long ownerId,
			String privilegeType) {
		String queryString = "from UumPrivilege pri where pri.ownerId = ? and pri.privilegeType = ? " +
							 "order by pri.privilegeId";
		Object[] paras = {ownerId,privilegeType};
		List<UumPrivilege> uumPrivilegeList = this.findByPropertyWithParas(queryString, paras);
		List<SysplModOptRef> sysplModOptRefList = new ArrayList<SysplModOptRef>();
		for(UumPrivilege uumPrivilege:uumPrivilegeList){
			sysplModOptRefList.add(uumPrivilege.getSysplModOptRef());
		}
		return sysplModOptRefList;
	}

	public int findUserSpecialPrivilegeByUserId(long userId) {
		String queryString = "select count(*) from UumPrivilege pri " +
							 "where pri.ownerId = "+userId +
							 " and pri.privilegeType = '"+USER_PRIVILEGE+"'";
		return this.getRowCount(queryString);
	}

}
