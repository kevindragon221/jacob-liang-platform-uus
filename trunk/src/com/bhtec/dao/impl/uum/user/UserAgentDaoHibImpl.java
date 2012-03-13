/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 23, 2010 @3:08:56 PM
 */
package com.bhtec.dao.impl.uum.user;

import static com.bhtec.common.constant.Common.*;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.uum.user.UserAgentDao;
import com.bhtec.dao.impl.BaseDaoHibImpl;
import com.bhtec.domain.pojo.uum.UumUserAgent;

@SuppressWarnings("unchecked")
public class UserAgentDaoHibImpl extends BaseDaoHibImpl implements UserAgentDao {

	/**
	 * 功能说明：根据用户ID分页查询用户有关的所有代理
	 * @author jacobliang
	 * @param start
	 * @param limit
	 * @param userId
	 * @return
	 * @time Feb 17, 2012 4:41:25 PM
	 */
	public Map findUserAgentByCon(int start,int limit,long userId){
		String queryString = "from UumUserAgent uumUserAgent " +
							 "where uumUserAgent.userId = ? " ;
		List params = new ArrayList();
		params.add(userId);
		
		String countSql = "select count(*) " + queryString;
		queryString = queryString + "order by uumUserAgent.agentId desc";
		List<UumUserAgent> limitList =  this.findByHqlWithPagination(start, limit, queryString, params);//分页
		
		int totalProperty = this.getRowCount(countSql,params);//总记录数
		Map map = new HashMap();
		map.put(TOTAL_PROPERTY, totalProperty);
		map.put(BUSI_LIST, limitList);
		return map;
	}
	/**
	 * 功能说明：根据用户ID和代理ID查询代理信息
	 * @author jacobliang
	 * @param userId
	 * @param userAgentId
	 * @return
	 * @time Feb 21, 2012 4:35:53 PM
	 */
	public int findByUserIdAAgentUserId(long userId,long userAgentId){
		String queryString = "select count(*) from UumUserAgent uumUserAgent " +
		 					 "where uumUserAgent.userId = ? " +
		 					 "and agentUserId = ?" ;
		List params = new ArrayList();
		params.add(userId);
		params.add(userAgentId);
		return this.getRowCount(queryString, params);
	}
	
	/**
	 * 功能说明：根据代理ID查询所有权限
	 * @author jacobliang
	 * @param agentId
	 * @return
	 * @time Feb 22, 2012 10:13:57 AM
	 */
	public List findAgentPrivilegeByAgentId(String agentId){
		String queryString = "select privilegeId from UumUserAgentPrivilege apri " +
							 "where apri.uumUserAgent.agentId = " + agentId;
		List params = new ArrayList();
		params.add(agentId);
		List list = this.findByProperty(queryString);
		return list;
	}
	/**
	 * 功能说明：根据用户ID查询有效期内代理记录
	 * @author jacobliang
	 * @param userId
	 * @return
	 * @time Feb 23, 2012 9:39:56 AM
	 */
	public List<UumUserAgent> findUumUserAgentByAgentUserId(long userId){
		String queryString = "from UumUserAgent uumUserAgent " +
				 			 "where uumUserAgent.agentUserId = ? " +
				 			 "and beginDate < ? and endDate > ?" ;
		Date date = new Date();
		Object[] obj = {userId,date,date};
		return this.findByPropertyWithParas(queryString, obj);
	}
	
	/**
	 * 功能说明：根据用户ID查询代理记录
	 * @author jacobliang
	 * @param userId
	 * @return
	 * @time Feb 23, 2012 9:39:56 AM
	 */
	public List<UumUserAgent> findAllUserAgentByUserId(long userId){
		String queryString = "from UumUserAgent uumUserAgent " +
				 			 "where uumUserAgent.userId = ? ";
		Date date = new Date();
		Object[] obj = {userId};
		return this.findByPropertyWithParas(queryString, obj);
	}
	/**
	 * 功能说明：当用户重新分配权限时，删除代理
	 * @author jacobliang
	 * @time Mar 2, 2012 5:48:08 PM
	 */
	public List<UumUserAgent> findUserAgentByOwnerId(String ownerId){
		String queryString = "from UumUserAgent uumUserAgent " +
			  			  	 "where uumUserAgent.orgRoleId in ("+ownerId+")";
		return this.findByProperty(queryString);
	}
}
