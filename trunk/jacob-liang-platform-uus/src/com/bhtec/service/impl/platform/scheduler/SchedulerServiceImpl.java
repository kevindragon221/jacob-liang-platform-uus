/**
 *功能说明：系统编码管理
 * @author jacobliang
 * @time @Jul 26, 2010 @5:30:57 PM
 */
package com.bhtec.service.impl.platform.scheduler;

import static com.bhtec.common.constant.Common.DELETE_OPT;
import static com.bhtec.common.constant.Common.DISABLE;
import static com.bhtec.common.constant.Common.ENABLE;
import static com.bhtec.common.constant.Common.LOG_LEVEL_FIRST;
import static com.bhtec.common.constant.Common.LOG_LEVEL_SECOND;
import static com.bhtec.common.constant.Common.LOG_LEVEL_THIRD;
import static com.bhtec.common.constant.Common.MODIFY_OPT;
import static com.bhtec.common.constant.Common.SAVE_OPT;
import static com.bhtec.common.constant.Common.SPLIT;
import static com.bhtec.common.constant.ServiceVariable.SYS_SCHEDULER;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.bhtec.dao.iface.platform.scheduler.SchedulerDao;
import com.bhtec.domain.pojo.platform.SysplSchedulerJob;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.platform.scheduler.SchedulerService;
import com.bhtec.service.impl.BaseServiceImpl;

public class SchedulerServiceImpl extends BaseServiceImpl implements SchedulerService {
	Logger log = Logger.getLogger(this.getClass());
	private SchedulerDao schedulerDao;
	
	/**
	 * 功能说明：保存任务
	 * @author jacobliang
	 * @param sysplSchedulerJob		任务对象
	 * @throws ApplicationException
	 * @time Jan 18, 2011 3:56:27 PM
	 */
	public void saveSysplSchedulerJob(SysplSchedulerJob sysplSchedulerJob) throws ApplicationException{
		schedulerDao.save(sysplSchedulerJob);
		if(ENABLE.equals(sysplSchedulerJob.getStatus())){//增加任务且状态为启用将任务加入调度
			SchedulerHandle.getInstance().startJob(sysplSchedulerJob);
		}
		StringBuffer optContent = new StringBuffer();
		optContent.append("任务名称："+sysplSchedulerJob.getJobName()+SPLIT)
					.append("任务类描述:"+sysplSchedulerJob.getJobClassDescript()+SPLIT)
					.append("触发器类型:"+sysplSchedulerJob.getTriggerType()+SPLIT)
					.append("时间表达式:"+sysplSchedulerJob.getTimeExpress()+SPLIT);
		this.saveLog(LOG_LEVEL_FIRST, SYS_SCHEDULER, SAVE_OPT, optContent.toString(),
				sysplSchedulerJob.getJobId().toString());
	}
	
	/**
	 * 功能说明：根据任务ID查询某个任务
	 * @author jacobliang
	 * @param  jobId				任务ID
	 * @return sysplSchedulerJob	任务对象
	 * @throws 
	 * @time Jan 18, 2011 3:56:27 PM
	 */
	public SysplSchedulerJob findSysplSchedulerJobByJobId(Long jobId){
		SysplSchedulerJob sysplSchedulerJob = (SysplSchedulerJob)schedulerDao
					.getPojoById("com.bhtec.domain.pojo.platform.SysplSchedulerJob", jobId);
		return sysplSchedulerJob;
	}
	/**
	 * 功能说明：修改编码
	 * @author jacobliang
	 * @param  sysplSchedulerJob	编码对象
	 * @return
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public void modifySysplSchedulerJob(SysplSchedulerJob sysplSchedulerJob)throws ApplicationException{
		this.schedulerDao.update(sysplSchedulerJob);
		StringBuffer optContent = new StringBuffer();
		optContent.append("任务名称："+sysplSchedulerJob.getJobName()+SPLIT)
		.append("任务类描述:"+sysplSchedulerJob.getJobClassDescript()+SPLIT)
		.append("触发器类型:"+sysplSchedulerJob.getTriggerType()+SPLIT)
		.append("时间表达式:"+sysplSchedulerJob.getTimeExpress()+SPLIT);
		this.saveLog(LOG_LEVEL_SECOND, SYS_SCHEDULER, MODIFY_OPT, optContent.toString(),
				sysplSchedulerJob.getJobId().toString());
	}
	/**
	 * 功能说明：删除任务
	 * @author jacobliang
	 * @param jobId					任务ID
	 * @time Jan 18, 2011 3:59:44 PM
	 */
	public void deleteSysplSchedulerJobByJobId(Long jobId)throws ApplicationException{
		SysplSchedulerJob sysplSchedulerJob = findSysplSchedulerJobByJobId(jobId);
		this.schedulerDao.delete(sysplSchedulerJob);
		SchedulerHandle.getInstance().stopJob(sysplSchedulerJob.getJobName());
		StringBuffer optContent = new StringBuffer();
		optContent.append("任务名称："+sysplSchedulerJob.getJobName()+SPLIT)
		.append("任务类描述:"+sysplSchedulerJob.getJobClassDescript()+SPLIT)
		.append("触发器类型:"+sysplSchedulerJob.getTriggerType()+SPLIT)
		.append("时间表达式:"+sysplSchedulerJob.getTimeExpress()+SPLIT);
		this.saveLog(LOG_LEVEL_THIRD, SYS_SCHEDULER, DELETE_OPT, optContent.toString(),
				sysplSchedulerJob.getJobId().toString());
	}
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
	public Map findSchedulerByCon(int start,int limit,String jobName,String triggerType){
		return this.schedulerDao.findSchedulerByCon(start, limit, jobName, triggerType);
	}
	
	/**
	 * 功能说明：根据任务名称查询任务是否存在
	 * @author jacobliang
	 * @param jobName		任务名称
	 * @param boolean		true存在	false不存在
	 * @return
	 * @time Jan 12, 2011 10:56:51 AM
	 */
	public boolean findSysplSchedulerJobByJobName(String jobName){
		int count = schedulerDao.findSysplSchedulerJobByJobName(jobName);
		if(count == 0){
			return false;
		}
		return true;
	}
	/**
	 * 功能说明：查询所有任务调度
	 * @author jacobliang
	 * @return
	 * @time Jan 19, 2011 2:34:25 PM
	 */
	private List<SysplSchedulerJob> findAllSysplSchedulerJob(){
		return this.schedulerDao.getAll("SysplSchedulerJob", "");
	}
	/**
	 * 功能说明：启动调度器
	 * @author jacobliang
	 * @time Jan 18, 2011 4:14:37 PM
	 */
	public void startScheduler()throws ApplicationException{
		SchedulerHandle schedulerHandle = SchedulerHandle.getInstance();
		List<SysplSchedulerJob> jobList = findAllSysplSchedulerJob();
		schedulerHandle.setJobList(jobList);
		String jobIds = "";
		for(SysplSchedulerJob sysplSchedulerJob:jobList){
			String jobId = sysplSchedulerJob.getJobId()+"";
			jobIds = "".equals(jobIds)?jobId:jobIds+","+jobId;
		}
		schedulerDao.modifyAllJobStatus(jobIds, ENABLE);
		schedulerHandle.startScheduler(true);
		this.saveLog(LOG_LEVEL_THIRD, SYS_SCHEDULER, "启动调度器", "启动调度器并初始化任务","");
	}
	/**
	 * 功能说明：停止调度器
	 * @author jacobliang
	 * @time Jan 18, 2011 4:15:32 PM
	 */
	public void stopScheduler(){
		SchedulerHandle.getInstance().stopScheduler();
		List<SysplSchedulerJob> jobList = findAllSysplSchedulerJob();
		String jobIds = "";
		for(SysplSchedulerJob sysplSchedulerJob:jobList){
			String jobId = sysplSchedulerJob.getJobId()+"";
			jobIds = "".equals(jobIds)?jobId:jobIds+","+jobId;
		}
		schedulerDao.modifyAllJobStatus(jobIds, DISABLE);
		try {
			this.saveLog(LOG_LEVEL_THIRD, SYS_SCHEDULER, "停止调度器", "停止调度器","");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 功能说明：启动job
	 * @author jacobliang
	 * @param	jobId				任务ID
	 * @time Jan 18, 2011 4:20:22 PM
	 */
	public void startJob(long jobId)throws ApplicationException{
		SysplSchedulerJob sysplSchedulerJob = this.findSysplSchedulerJobByJobId(jobId);
		SchedulerHandle.getInstance().startJob(sysplSchedulerJob);
		StringBuffer sb = new StringBuffer();
		sb.append("启动job:"+SPLIT).append("jobName:"+sysplSchedulerJob.getJobName()+SPLIT)
		.append("job类描述:"+sysplSchedulerJob.getJobClassDescript()+SPLIT)
		.append("触发器类型:"+sysplSchedulerJob.getTriggerType()+SPLIT);
		this.schedulerDao.modifyJobStatus(jobId, ENABLE);//更改状态
		this.saveLog(LOG_LEVEL_THIRD, SYS_SCHEDULER, "启动job", sb.toString(),""+jobId);
	}
	/**
	 * 功能说明：停止job
	 * @author jacobliang
	 * @param 	jobId				任务ID
	 * @param	jobName				任务name
	 * @return
	 * @time Jan 18, 2011 4:29:32 PM
	 */
	public void stopJob(long jobId,String jobName)throws ApplicationException{
		SchedulerHandle.getInstance().stopJob(jobName);
		this.schedulerDao.modifyJobStatus(jobId, DISABLE);//更改状态
		this.saveLog(LOG_LEVEL_THIRD, SYS_SCHEDULER, "停止job", "停止job:"+jobName,"");
	}
	
	public void setSchedulerDao(SchedulerDao schedulerDao) {
		this.schedulerDao = schedulerDao;
	}
}
