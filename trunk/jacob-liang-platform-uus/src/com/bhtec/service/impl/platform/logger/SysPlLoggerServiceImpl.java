/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 11, 2010 @11:00:01 AM
 */
package com.bhtec.service.impl.platform.logger;

import java.util.Calendar;
import java.util.Map;

import com.bhtec.dao.iface.platform.logger.SysPlLoggerDao;
import com.bhtec.domain.pojo.platform.SysplSysOptLog;
import com.bhtec.service.iface.platform.logger.SysPlLoggerService;
import com.bhtec.service.impl.BaseServiceImpl;

public class SysPlLoggerServiceImpl extends BaseServiceImpl implements SysPlLoggerService {
	private SysPlLoggerDao sysPlLoggerDao;
	
	/**
	 * 功能说明：删除日志
	 * @author jacobliang
	 * @param keepdays	保留天数
	 * @throws 
	 * @time Sep 11, 2010 10:52:34 AM
	 */
	public void deleteLog(int keepdays){
		sysPlLoggerDao.deleteLog(keepdays);
	}
	/**
	 * 功能说明：查找日志
	 * @author jacobliang
	 * @param start		开始记录
	 * @param limit		结束记录
	 * @param sysplSysOptLog	查询对象
	 * @return
	 * @time Dec 29, 2010 9:50:14 PM
	 */
	public Map findLoggerByCon(int start,int limit,SysplSysOptLog sysplSysOptLog) {
		return sysPlLoggerDao.findLoggerByCon(start, limit, sysplSysOptLog);
	}
	
	/**
	 * 功能说明：查询系统操作日志内容根据操作ID
	 * @author jacobliang
	 * @param optId			操作ID
	 * @return
	 * @time Dec 30, 2010 4:56:24 PM
	 */
	public String findSysplSysOptLogContentById(long optId){
		String queryString = "select log.optContent from SysplSysOptLog log " +
							 "where log.optId = " + optId;
		Object obj = this.sysPlLoggerDao.getSingleRowRecord(queryString);
		return obj==null?"":obj.toString();
	}

	public void saveLog(SysplSysOptLog sysplSysOptLog) {
		sysPlLoggerDao.save(sysplSysOptLog);
	}

	public void setSysPlLoggerDao(SysPlLoggerDao sysPlLoggerDao) {
		this.sysPlLoggerDao = sysPlLoggerDao;
	}

}
