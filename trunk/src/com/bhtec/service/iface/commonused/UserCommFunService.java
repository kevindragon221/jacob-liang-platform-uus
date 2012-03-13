/**
 *功能说明：用户常用功能
 * @author jacobliang
 * @time @Nov 26, 2010 @11:16:59 AM
 */
package com.bhtec.service.iface.commonused;

import java.util.List;

import com.bhtec.domain.pojo.uum.UumUserCommfun;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.BaseService;

public interface UserCommFunService extends BaseService {
	/**
	 * 功能说明：根据用户ID查询用户常用功能
	 * @author jacobliang
	 * @param userId	用户ID
	 * @return
	 * @time Nov 26, 2010 11:00:32 AM
	 */
	public List<UumUserCommfun> findUserCommFunByUserId(long userId);
	/**
	 * 功能说明：保存用户常用功能
	 * @author jacobliang
	 * @param userId		用户ID
	 * @param moduleId		多模块ID
	 * @throws ApplicationException
	 * @time Nov 26, 2010 11:23:16 AM
	 */
	public void saveUserCommFun(long userId,List<Long> moduleId)throws ApplicationException;
	/**
	 * 功能说明：删除用户无用常用功能
	 * @author jacobliang
	 * @param 	uumUserCommfunList		 用户常用功能列表
	 * @throws ApplicationException
	 * @time Nov 26, 2010 10:57:57 AM
	 */
	public void deleteUserCommFunById(List<UumUserCommfun> uumUserCommfunList)throws ApplicationException;
	/**
	 * 功能说明：用户停用时删除用户常用功能
	 * @author jacobliang
	 * @param userId
	 * @time Mar 2, 2012 10:51:22 AM
	 */
	public void deleteUserCommFunByUserId(long userId)throws ApplicationException;
}
