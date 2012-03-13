/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 11, 2010 @10:56:23 AM
 */
package com.bhtec.dao.impl.platform.logger;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.tools.UtilTools.isNull;
import static com.bhtec.common.tools.UtilTools.isNullOrEmpty;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.bhtec.dao.iface.platform.logger.SysPlLoggerDao;
import com.bhtec.dao.impl.BaseDaoHibImpl;
import com.bhtec.domain.pojo.platform.SysplSysOptLog;

public class SysPlLoggerDaoHibImpl  extends BaseDaoHibImpl implements SysPlLoggerDao {
	private Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 功能说明：删除日志
	 * @author jacobliang
	 * @param keepdays	保留天数
	 * @throws 
	 * @time Sep 11, 2010 10:52:34 AM
	 */
	public void deleteLog(int keepdays) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(SysplSysOptLog.class);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -keepdays);
		criteria.add(Expression.le("optTime", cal.getTime()));
		List list = criteria.list();
		this.deleteAll(list);
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
	public Map findLoggerByCon(int start,int limit,SysplSysOptLog sysplSysOptLog){
		String optModName = sysplSysOptLog.getOptModName();
		String optName = sysplSysOptLog.getOptName();
		String optUserName = sysplSysOptLog.getOptUserName();
		String optUserRole = sysplSysOptLog.getOptUserRole();
		String optPcIp = sysplSysOptLog.getOptPcIp();
		String optBusinessId = sysplSysOptLog.getOptBusinessId();
		Date startTime = sysplSysOptLog.getStartTime();
		Date endTime = sysplSysOptLog.getEndTime();
		
		List params = new ArrayList();
		String queryString = "from SysplSysOptLog log where 0 = 0 ";
		if(!isNullOrEmpty(optModName)){
			queryString += " and log.optModName like ?";
			params.add("%"+optModName+"%");
		}
		if(!isNullOrEmpty(optName)){
			queryString += " and log.optName like ?";
			params.add("%"+optName+"%");
		}
		if(!isNullOrEmpty(optUserName)){
			queryString += " and log.optUserName like ?";
			params.add("%"+optUserName+"%");
		}
		if(!isNullOrEmpty(optUserRole)){
			queryString += " and log.optUserRole like ?";
			params.add("%"+optUserRole+"%");
		}
		if(!isNullOrEmpty(optPcIp)){
			queryString += " and log.optPcIp like ?";
			params.add("%"+optPcIp+"%");
		}
		if(!isNullOrEmpty(optBusinessId)){
			queryString += " and log.optBusinessId = ?";
			params.add(optBusinessId);
		}
		if(!isNull(startTime) && !isNull(endTime)){
			queryString += " and log.optTime between ? and ?";
			params.add(startTime);
			params.add(endTime);
		}
		
		String countSql = "select count(*) " + queryString;
		queryString += " order by log.optId desc"; 
		log.info(queryString);
		List<SysplSysOptLog> limitModuleList =  //query.list();
			this.findByHqlWithPagination(start, limit, queryString,params);//分页
		int totalProperty = this.getRowCount(countSql,params);//总记录数
		Map map = new HashMap();
		map.put(TOTAL_PROPERTY, totalProperty);
		map.put(BUSI_LIST, limitModuleList);
		return map;
	}

	public void saveLog(SysplSysOptLog SysplSysOptLog) {
		
	}

}
