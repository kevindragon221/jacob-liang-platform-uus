/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 11, 2010 @10:48:52 AM
 */
package com.bhtec.dao.iface.platform.logger;

import java.util.Date;
import java.util.Map;

import com.bhtec.dao.iface.BaseDao;
import com.bhtec.domain.pojo.platform.SysplSysOptLog;

public interface SysPlLoggerDao  extends BaseDao{
	/**
	 * 功能说明：保存日志
	 * @author jacobliang
	 * @param
	 * @param
	 * @param SysplSysOptLog	日志对象
	 * @throws 
	 * @time Sep 11, 2010 10:52:16 AM
	 */
	public void saveLog(SysplSysOptLog SysplSysOptLog);
	/**
	 * 功能说明：删除日志
	 * @author jacobliang
	 * @param keepdays	保留天数
	 * @throws 
	 * @time Sep 11, 2010 10:52:34 AM
	 */
	public void deleteLog(int keepdays);
	/**
	 * 功能说明：查找日志
	 * @author jacobliang
	 * @param start		开始记录
	 * @param limit		结束记录
	 * @param sysplSysOptLog	查询对象
	 * @return
	 * @time Dec 29, 2010 9:50:14 PM
	 */
	public Map findLoggerByCon(int start,int limit,SysplSysOptLog sysplSysOptLog);
}
