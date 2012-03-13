/**
 * 功能说明：任务管理
 * @author jacobliang
 */
package com.bhtec.service.iface.platform.scheduler;

import java.util.Map;

import com.bhtec.domain.pojo.platform.SysplSchedulerJob;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.BaseService;

public interface SchedulerService extends BaseService {
	/**
	 * 功能说明：保存任务
	 * @author jacobliang
	 * @param sysplSchedulerJob		任务对象
	 * @throws ApplicationException
	 * @time Jan 18, 2011 3:56:27 PM
	 */
	public void saveSysplSchedulerJob(SysplSchedulerJob sysplSchedulerJob) throws ApplicationException;
	/**
	 * 功能说明：根据任务ID查询某个任务
	 * @author jacobliang
	 * @param  jobId				任务ID
	 * @return sysplSchedulerJob	任务对象
	 * @throws 
	 * @time Jan 18, 2011 3:56:27 PM
	 */
	public SysplSchedulerJob findSysplSchedulerJobByJobId(Long jobId);
	/**
	 * 功能说明：修改任务
	 * @author jacobliang
	 * @param  sysplSchedulerJob	任务对象
	 * @return
	 * @throws 
	 * @time Jan 18, 2011 3:56:27 PM
	 */
	public void modifySysplSchedulerJob(SysplSchedulerJob sysplSchedulerJob)throws ApplicationException;
	/**
	 * 功能说明：删除任务
	 * @author jacobliang
	 * @param jobId					任务ID
	 * @time Jan 18, 2011 3:59:44 PM
	 */
	public void deleteSysplSchedulerJobByJobId(Long jobId)throws ApplicationException;
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
	public boolean findSysplSchedulerJobByJobName(String jobName);
	/**
	 * 功能说明：启动调度器
	 * @author jacobliang
	 * @time Jan 18, 2011 4:14:37 PM
	 */
	public void startScheduler()throws ApplicationException;
	/**
	 * 功能说明：停止调度器
	 * @author jacobliang
	 * @time Jan 18, 2011 4:15:32 PM
	 */
	public void stopScheduler();
	/**
	 * 功能说明：启动job
	 * @author jacobliang
	 * @param	jobId				任务ID
	 * @time Jan 18, 2011 4:20:22 PM
	 */
	public void startJob(long jobId)throws ApplicationException;
	/**
	 * 功能说明：停止job
	 * @author jacobliang
	 * @param	jobId				任务ID
	 * @param 	jobName				任务名称
	 * @return
	 * @time Jan 18, 2011 4:29:32 PM
	 */
	public void stopJob(long jobId,String jobName)throws ApplicationException;
}
