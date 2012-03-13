/**
 *功能说明：用户常用功能
 * @author jacobliang
 * @time @Nov 26, 2010 @10:56:12 AM
 */
package com.bhtec.dao.iface.commonused.usercommfun;

import java.util.List;

import com.bhtec.dao.iface.BaseDao;
import com.bhtec.domain.pojo.uum.UumUserCommfun;
import com.bhtec.exception.ApplicationException;

public interface UserCommFunDao  extends BaseDao{
	/**
	 * 功能说明：根据ID删除用户所有常用功能
	 * @author jacobliang
	 * @param id		userId or moduleId	用户ID或模块ID
	 * @throws ApplicationException
	 * @time Nov 26, 2010 10:57:57 AM
	 */
	public void deleteUserCommFun(long userId);
	/**
	 * 功能说明：根据用户ID查询用户常用功能
	 * @author jacobliang
	 * @param userId
	 * @return
	 * @time Nov 26, 2010 11:00:32 AM
	 */
	public List<UumUserCommfun> findUserCommFunByUserId(long userId);
}
