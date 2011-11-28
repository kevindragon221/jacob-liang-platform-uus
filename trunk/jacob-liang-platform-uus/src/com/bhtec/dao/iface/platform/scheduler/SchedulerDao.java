/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:00:17 PM
 */
package com.bhtec.dao.iface.platform.scheduler;

import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.BaseDao;
import com.bhtec.domain.pojo.platform.SysplSchedulerJob;

public interface SchedulerDao extends BaseDao {
	/**
	 * 功能说明：查询调度任务
	 * @author jacobliang
	 * @param start			开始记录
	 * @param limit			每页记录数
	 * @param jobName		任务名称
	 * @param triggerType	触发器类型
	 * @return
	 * @time Jan 11, 2011 4:48:48 PM
	 */
	public Map findSchedulerByCon(int start,int limit,String jobName,String triggerType);
	/**
	 * 功能说明：根据任务名称查询任务是否存在
	 * @author jacobliang
	 * @param jobName		任务名称
	 * @return
	 * @time Jan 12, 2011 10:56:51 AM
	 */
	public int findSysplSchedulerJobByJobName(String jobName);
	/**
	 * 功能说明：根据任务名称修改任务状态
	 * @author jacobliang
	 * @param 	jobId				任务ID
	 * @param	status				任务status
	 * @time Jan 19, 2011 4:56:05 PM
	 */
	public void modifyJobStatus(long jobId,String status);
	/**
	 * 功能说明：批量修改任务状态
	 * @author jacobliang
	 * @param 	jobId				任务ID
	 * @param	status				任务status
	 * @time Jan 19, 2011 4:56:05 PM
	 */
	public void modifyAllJobStatus(String jobIds,String status);
}
