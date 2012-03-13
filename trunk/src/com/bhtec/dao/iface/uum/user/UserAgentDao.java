/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 23, 2010 @2:56:23 PM
 */
package com.bhtec.dao.iface.uum.user;

import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.BaseDao;
import com.bhtec.domain.pojo.uum.UumUserAgent;

public interface UserAgentDao extends BaseDao {
	
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
	 * 功能说明：根据用户ID和代理ID查询代理信息
	 * @author jacobliang
	 * @param userId
	 * @param userAgentId
	 * @return
	 * @time Feb 21, 2012 4:35:53 PM
	 */
	public int findByUserIdAAgentUserId(long userId,long userAgentId);
	/**
	 * 功能说明：根据代理ID查询所有权限
	 * @author jacobliang
	 * @param agentId
	 * @return
	 * @time Feb 22, 2012 10:13:57 AM
	 */
	public List findAgentPrivilegeByAgentId(String agentId);
	/**
	 * 功能说明：根据用户ID查询代理记录
	 * @author jacobliang
	 * @param userId
	 * @return
	 * @time Feb 23, 2012 9:39:56 AM
	 */
	public List<UumUserAgent> findUumUserAgentByAgentUserId(long userId);
	/**
	 * 功能说明：根据用户ID查询代理记录
	 * @author jacobliang
	 * @param userId
	 * @return
	 * @time Feb 23, 2012 9:39:56 AM
	 */
	public List<UumUserAgent> findAllUserAgentByUserId(long userId);
	/**
	 * 功能说明：当用户重新分配权限时，删除代理
	 * @author jacobliang
	 * @time Mar 2, 2012 5:48:08 PM
	 */
	public List<UumUserAgent> findUserAgentByOwnerId(String ownerId);
}
