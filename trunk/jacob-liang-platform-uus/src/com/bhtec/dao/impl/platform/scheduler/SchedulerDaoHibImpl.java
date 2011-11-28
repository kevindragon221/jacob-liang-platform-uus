/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:03:17 PM
 */
package com.bhtec.dao.impl.platform.scheduler;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.tools.UtilTools.isNullOrEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.platform.scheduler.SchedulerDao;
import com.bhtec.dao.impl.BaseDaoHibImpl;
import com.bhtec.domain.pojo.platform.SysplSchedulerJob;

public class SchedulerDaoHibImpl extends BaseDaoHibImpl implements SchedulerDao {
	
	public Map findSchedulerByCon(int start,int limit,String jobName,String triggerType){
		String queryString = "from SysplSchedulerJob sjob where 0 = 0 ";
		List params = new ArrayList();
		if(!isNullOrEmpty(jobName)){
			queryString += " and sjob.jobName like ?";
			params.add("%"+jobName+"%");
		}
		if(!isNullOrEmpty(triggerType)){
			queryString += " and sjob.triggerType like ?";
			params.add("%"+triggerType+"%");
		}
		
		String countSql = "select count(*) " + queryString;
		queryString += " order by sjob.jobId desc"; 
		List<SysplSchedulerJob> limitModuleList =  this.findByHqlWithPagination(start, limit, queryString, params);//分页
		int totalProperty = this.getRowCount(countSql,params);//总记录数
		Map map = new HashMap();
		map.put(TOTAL_PROPERTY, totalProperty);
		map.put(BUSI_LIST, limitModuleList);
		return map;
	}
	
	/**
	 * 功能说明：根据任务名称查询任务是否存在
	 * @author jacobliang
	 * @param jobName		任务名称
	 * @return
	 * @time Jan 12, 2011 10:56:51 AM
	 */
	public int findSysplSchedulerJobByJobName(String jobName){
		String queryString = "select count(*) from SysplSchedulerJob sjob " +
							 "where sjob.jobName = ?";
		List params = new ArrayList();
		params.add(jobName);
		int totalProperty = this.getRowCount(queryString,params);//总记录数
		return totalProperty;
	}
	/**
	 * 功能说明：根据任务名称修改任务状态
	 * @author jacobliang
	 * @param 	jobId				任务ID
	 * @param	status				任务status
	 * @time Jan 19, 2011 4:56:05 PM
	 */
	public void modifyJobStatus(long jobId,String status){
		String hql = "update SysplSchedulerJob sjob set sjob.status = '"+status+"' "
					+"where sjob.jobId = "+jobId;
		this.excuteHql(hql);
	}
	
	/**
	 * 功能说明：批量修改任务状态
	 * @author jacobliang
	 * @param 	jobId				任务ID
	 * @param	status				任务status
	 * @time Jan 19, 2011 4:56:05 PM
	 */
	public void modifyAllJobStatus(String jobIds,String status){
		String hql = "update SysplSchedulerJob sjob set sjob.status = '"+status+"' "
					+"where sjob.jobId in ("+jobIds+")";
		this.excuteHql(hql);
	}
}
