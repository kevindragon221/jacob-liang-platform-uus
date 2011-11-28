package com.bhtec.service.iface;

import javax.servlet.http.HttpServletRequest;

import com.bhtec.dao.iface.BaseDao;
import com.bhtec.exception.ApplicationException;

/**
 *功能描述：
 *创建时间：Feb 24, 2010 4:28:33 PM 
 *@author lianglp
 *@version 1.0
 */

public interface BaseService {
	/**
	 * 功能说明：baseDao
	 * @author jacobliang
	 * @param
	 * @param
	 * @param BaseDao
	 * @throws 
	 * @time Sep 12, 2010 9:31:31 AM
	 */
	public void setBaseDao(BaseDao BaseDao);
	/**
	 * 功能说明：设置上下文
	 * @author jacobliang
	 * @param
	 * @param
	 * @param HttpServletRequest
	 * @throws 
	 * @time Sep 12, 2010 9:31:31 AM
	 */
	public void setHttpServletRequest(HttpServletRequest request);
	
	/**
	 * 功能说明：公用记录系统操作日志
	 * @author jacobliang
	 * @param
	 * @param
	 * @param optModName
	 * @param optName
	 * @param optContent
	 * @param optBusinessId
	 * @throws 
	 * @time Sep 11, 2010 11:21:08 AM
	 */
	public void saveLog(String level,String optModName,String optName,
						String optContent,String optBusinessId)throws ApplicationException;
}
