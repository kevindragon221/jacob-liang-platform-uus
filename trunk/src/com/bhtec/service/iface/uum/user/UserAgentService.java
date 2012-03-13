/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 30, 2010 @3:10:17 PM
 */
package com.bhtec.service.iface.uum.user;

import java.util.List;
import java.util.Map;

import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.domain.pojo.uum.UumUserAgent;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.BaseService;

public interface UserAgentService extends BaseService {
	
	/**
	 * 功能说明：保存用户代理
	 * @author jacobliang
	 * @param uumUserAgent
	 * @throws ApplicationException
	 * @time Feb 17, 2012 4:53:33 PM
	 */
	public void saveUserAgent(UumUserAgent uumUserAgent,List<String> modOptIdList) throws ApplicationException;
	/**
	 * 功能说明：修改用户代理
	 * @author jacobliang
	 * @param uumUserAgent
	 * @throws ApplicationException
	 * @time Feb 17, 2012 4:56:24 PM
	 */
	public void modifyUserAgent(UumUserAgent uumUserAgent)throws ApplicationException;
	/**
	 * 功能说明：删除用户代理
	 * @author jacobliang
	 * @param agentId
	 * @throws ApplicationException
	 * @time Feb 17, 2012 4:57:59 PM
	 */
	public void deleteUserAgent(List<Long> agentId)throws ApplicationException;
	/**
	 * 功能说明：根据用户ID分页查询用户有关的所有代理
	 * @author jacobliang
	 * @param start
	 * @param limit
	 * @param userId
	 * @return
	 * @time Feb 17, 2012 4:41:25 PM
	 */
	public Map findUserAgentByCon(int start,int limit,long userId);
	/**
	 * 功能说明：判断是否已经为此用户授过权
	 * @author jacobliang
	 * @return
	 * @time Feb 21, 2012 4:13:57 PM
	 */
	public boolean hasAgentUser(long userId,long userAgentId);
	/**
	 * 功能说明：查找用户分配及未分配的模块操作代理权限
	 * @author jacobliang
	 * @return
	 * @time Feb 22, 2012 9:53:18 AM
	 */
	public List<SysplModuleMemu> userAssignAgentOptPrivilege(String agentId);
	/**
	 * 功能说明：根据用户ID查询代理记录
	 * @author jacobliang
	 * @param userId
	 * @return
	 * @time Feb 23, 2012 9:39:56 AM
	 */
	public List<UumUserAgent> findUumUserAgentByAgentUserId(long userId);
	/**
	 * 功能说明：根据代理ID查询代理的权限
	 * @author jacobliang
	 * @param agentId
	 * @return
	 * @time Feb 24, 2012 10:14:46 AM
	 */
	public List findAgentPrivilegeByAgentId(String agentId);
	/**
	 * 功能说明：当用户停用时删除其所有代理
	 * @author jacobliang
	 * @param userId
	 * @time Mar 2, 2012 10:38:19 AM
	 */
	public void deleteAllUserAgent(long userId)throws ApplicationException;;
	/**
	 * 功能说明：当用户重新分配权限时，删除代理
	 * @author jacobliang
	 * @time Mar 2, 2012 5:48:08 PM
	 */
	public void deleteAgentByOwnerId(Long ownerId,String ownerType);
}
