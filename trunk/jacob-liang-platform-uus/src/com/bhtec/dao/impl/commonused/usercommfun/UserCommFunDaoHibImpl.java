/**
 *功能说明：
 * @author jacobliang
 * @time @Nov 26, 2010 @11:06:32 AM
 */
package com.bhtec.dao.impl.commonused.usercommfun;

import java.util.List;

import com.bhtec.dao.iface.commonused.usercommfun.UserCommFunDao;
import com.bhtec.dao.impl.BaseDaoHibImpl;
import com.bhtec.domain.pojo.uum.UumUserCommfun;

public class UserCommFunDaoHibImpl extends BaseDaoHibImpl implements
		UserCommFunDao {

	public void deleteUserCommFun(long userId) {
		String hql = "delete from UumUserCommfun commfun where " +
				  	 "commfun.id.userId = "+userId;
		this.excuteHql(hql);
	}
	
	

	public List<UumUserCommfun> findUserCommFunByUserId(long userId) {
		String queryString = "from UumUserCommfun commfun where commfun.id.userId = "+userId;
		return this.findByProperty(queryString);
	}

}
