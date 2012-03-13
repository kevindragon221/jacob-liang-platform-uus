/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 11, 2010 @10:58:11 AM
 */
package com.bhtec.service.iface.platform.logger;

import java.util.Date;
import java.util.Map;

import com.bhtec.domain.pojo.platform.SysplSysOptLog;

public interface SysPlLoggerService {
	/**
	 * 功能说明：保存日志
	 * @author jacobliang
	 * @param
	 * @param
	 * @param SysplSysOptLog	日志对象
	 * @throws 
	 * @time Sep 11, 2010 10:52:16 AM
	 */
	public void saveLog(SysplSysOptLog sysplSysOptLog);
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
	/**
	 * 功能说明：查询系统操作日志内容根据操作ID
	 * @author jacobliang
	 * @param optId			操作ID
	 * @return
	 * @time Dec 30, 2010 4:56:24 PM
	 */
	public String findSysplSysOptLogContentById(long optId);
}
