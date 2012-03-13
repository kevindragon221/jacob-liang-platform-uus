/**
 *功能说明：调度器操作
 * @author jacobliang
 * @time @Jan 18, 2011 @4:47:21 PM
 */
package com.bhtec.service.impl.platform.scheduler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.bhtec.common.tools.UtilTools;
import com.bhtec.domain.pojo.platform.SysplSchedulerJob;
import com.bhtec.exception.ApplicationException;

public class SchedulerHandle {
	private Logger log = Logger.getLogger(this.getClass());
	SchedulerFactory sf = new StdSchedulerFactory();
    private Scheduler sched = null;
	private static SchedulerHandle schedulerHandle = new SchedulerHandle();
	private final String SIMPLE = "simple";
	private final String CRON = "cron";
	private final String TRIGGER_NAME = "_trigger";
	private List<SysplSchedulerJob> jobList;
	
	private SchedulerHandle(){
		
	}
	
	public static SchedulerHandle getInstance(){
		return schedulerHandle;
	}
	
	/**
	 * 功能说明：启动调度器
	 * @author jacobliang
	 * @param	isInit	是否需要初始化 true 需要 false 不需要
	 * @time Jan 18, 2011 4:14:37 PM
	 */
	public void startScheduler(boolean isInit)throws ApplicationException{
		try {
		    if(sched == null){
				sched = sf.getScheduler();
				sched.start();
				log.info("scheduler has already started.");
				if(isInit)
					initAllJobs(jobList);//初始化所有任务
		    }
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 功能说明：停止调度器
	 * @author jacobliang
	 * @time Jan 18, 2011 4:15:32 PM
	 */
	public void stopScheduler(){
		try {
			if(sched != null){
				sched.shutdown();
				sched = null;
				log.info("scheduler has already stopped.");
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 功能说明：启动job
	 * @author jacobliang
	 * @param	jobId				任务ID
	 * @time Jan 18, 2011 4:20:22 PM
	 */
	public void startJob(SysplSchedulerJob sysplSchedulerJob)throws ApplicationException{
		if(UtilTools.isNull(sysplSchedulerJob))
			throw new ApplicationException("sysplSchedulerJob is null.");
		startScheduler(false);
		addJob(sysplSchedulerJob);
		log.info("job has already added.");
	}
	/**
	 * 功能说明：停止job
	 * @author jacobliang
	 * @param  jobName			任务名称
	 * @return
	 * @time Jan 18, 2011 4:29:32 PM
	 */
	public void stopJob(String jobName)throws ApplicationException{
		try {
			if(sched == null)return;
			boolean triggerBol = sched.unscheduleJob(jobName+TRIGGER_NAME, sched.DEFAULT_GROUP);
			boolean jobBol = sched.deleteJob(jobName, sched.DEFAULT_GROUP);
			log.info(jobName+TRIGGER_NAME +" stop result " + triggerBol);
			log.info(jobName+" job stop result " + jobBol);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 功能说明：初始化所有Jobs
	 * @author jacobliang
	 * @param jobList
	 * @time Jan 18, 2011 5:07:20 PM
	 */
	private void initAllJobs(List<SysplSchedulerJob> jobList)throws ApplicationException{
		if(UtilTools.isNull(jobList))
			throw new ApplicationException("jobList is null.");
		for(SysplSchedulerJob sysplSchedulerJob:jobList){
			addJob(sysplSchedulerJob);
		}
		log.info("scheduler has already inited.");
	}
	/**
	 * 功能说明：调度中增加任务
	 * @author jacobliang
	 * @param sysplSchedulerJob
	 * @time Jan 18, 2011 5:13:36 PM
	 */
	private void addJob(SysplSchedulerJob sysplSchedulerJob)throws ApplicationException{
		String jobName = sysplSchedulerJob.getJobName();
		boolean bol = this.jobIsExist(jobName);
		if(bol){
			log.info("job already exist");
			return ;//job already exist
		}
		String triggerType = sysplSchedulerJob.getTriggerType();
		String jobClassDescript = sysplSchedulerJob.getJobClassDescript();
		String timeExpress = sysplSchedulerJob.getTimeExpress();
		Date startTime = sysplSchedulerJob.getStartTime()==null?new Date():sysplSchedulerJob.getStartTime();
		Date endTime = sysplSchedulerJob.getEndTime();
		int repeatCount = sysplSchedulerJob.getRepeatTime()==null?0:sysplSchedulerJob.getRepeatTime();
		long repeatInterval = sysplSchedulerJob.getSplitTime()==null?0:sysplSchedulerJob.getSplitTime();
		
		Class clzz = null;
		try {
			clzz = Class.forName(jobClassDescript);
		} catch (ClassNotFoundException e) {
			throw new ApplicationException(e);
		}
		JobDetail job = null;
		Trigger trigger = null;
		try {
			if(SIMPLE.equalsIgnoreCase(triggerType)){
				 job = new JobDetail(jobName, sched.DEFAULT_GROUP, clzz);
			     trigger = new SimpleTrigger(jobName+TRIGGER_NAME, sched.DEFAULT_GROUP,
			        		startTime, endTime, repeatCount, repeatInterval);
			}else if(CRON.equalsIgnoreCase(triggerType)){
				job = new JobDetail(jobName, sched.DEFAULT_GROUP, clzz);
				trigger = new CronTrigger(jobName+TRIGGER_NAME, sched.DEFAULT_GROUP, timeExpress); 
			}
			sched.scheduleJob(job, trigger);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 功能说明：查询任务是否存在
	 * @author jacobliang
	 * @param jobName
	 * @return
	 * @time Jan 18, 2011 5:27:30 PM
	 */
	public boolean jobIsExist(String jobName){
		boolean bol = false;
		if(sched == null)return bol;
		try {
			String[] jobNames = sched.getJobNames(sched.DEFAULT_GROUP);
			for(String jobNamee:jobNames){
				if(jobNamee.equals(jobName)){
					bol = true;
					break;
				}
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return bol;
	}
	
	/**功能说明：
	 * @author jacobliang
	 * @param args
	 * @time Jan 18, 2011 4:47:21 PM
	 */
	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		SchedulerHandle handle = SchedulerHandle.getInstance(); 
		List<SysplSchedulerJob> jobList = new ArrayList<SysplSchedulerJob>();
		SysplSchedulerJob job = new SysplSchedulerJob();
		job.setJobName("jobtest");
		job.setTriggerType(handle.SIMPLE);
		job.setJobClassDescript("com.bhtec.service.impl.platform.scheduler.JobTest");
		job.setRepeatTime(10);
		job.setSplitTime(3000L);
		job.setStartTime(new Date());
		jobList.add(job);
		handle.setJobList(jobList);
		try {
			while(true){
				String command = reader.readLine();
				if("exit".equals(command)){
					System.exit(0);
				}else if("startScheduler".equals(command)){
					handle.startScheduler(true);
				}else if("stopScheduler".equals(command)){
					handle.stopScheduler();
				}else if("jobtest".equals(command)){
					boolean bol = handle.jobIsExist("jobtest");
					System.out.println("jobtest wether exist " +bol);
				}else if("startJob".equals(command)){
					SysplSchedulerJob job2 = new SysplSchedulerJob();
					job2.setJobName("jobtest2");
					job2.setTriggerType(handle.CRON);
					job2.setJobClassDescript("com.bhtec.service.impl.platform.scheduler.JobTest");
					job2.setRepeatTime(10);
					job2.setSplitTime(3000L);
					job2.setStartTime(new Date());
					job2.setTimeExpress("0/3 * * * * ?");
					handle.startJob(job2);
				}else if("stopJob".equals(command)){
					handle.stopJob("jobtest2");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<SysplSchedulerJob> getJobList() {
		return jobList;
	}

	public void setJobList(List<SysplSchedulerJob> jobList) {
		this.jobList = jobList;
	}

	public Scheduler getSched() {
		return sched;
	}

	public void setSched(Scheduler sched) {
		this.sched = sched;
	}

}
