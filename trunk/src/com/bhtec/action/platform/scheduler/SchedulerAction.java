/**
 *功能说明：模块管理
 * @author jacobliang
 * @time @Jul 26, 2010 @4:54:52 PM
 */
package com.bhtec.action.platform.scheduler;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.constant.Common.USER_CODE;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bhtec.action.platform.PlatformBaseAction;
import com.bhtec.domain.pojo.platform.SysplSchedulerJob;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;
import com.bhtec.service.iface.platform.scheduler.SchedulerService;
import com.bhtec.service.impl.platform.scheduler.SchedulerHandle;
import com.opensymphony.xwork2.ModelDriven;

public class SchedulerAction extends PlatformBaseAction implements ModelDriven<SysplSchedulerJob> {
	private Logger log = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 10000L;
	private SysplSchedulerJob sysplSchedulerJob = new SysplSchedulerJob();
	private SchedulerService schedulerService;
	private String failMesg;
	private List<SysplSchedulerJob> jobList;
	private int count;
	private boolean existJobName;
	private boolean schedulerIsStart;
	/**
	 * 功能说明：保存任务
	 * @author jacobliang
	 * @param sysplSchedulerJob		任务对象
	 * @throws ApplicationException
	 * @time Jan 18, 2011 3:56:27 PM
	 */
	public void saveSysplSchedulerJob() {
		try {
			schedulerService.setHttpServletRequest(this.getHttpServletRequest());
			sysplSchedulerJob.setCreateDate(new Date());
			sysplSchedulerJob.setCreator((String)getHttpServletRequest().getSession().getAttribute(USER_CODE));
			schedulerService.saveSysplSchedulerJob(sysplSchedulerJob);
			this.returnSuccess();
		}catch (ApplicationException e) {
			e.printStackTrace();
			this.returnFailur(e.toString());
		}catch (SystemException e) {
			e.printStackTrace();
			this.returnFailur(this.ERROR);
		}
	}
	
	/**
	 * 功能说明：根据任务ID查询某个任务
	 * @author jacobliang
	 * @param  jobId				任务ID
	 * @return sysplSchedulerJob	任务对象
	 * @throws 
	 * @time Jan 18, 2011 3:56:27 PM
	 */
	public String findSysplSchedulerJobByJobId(){
		sysplSchedulerJob = schedulerService.findSysplSchedulerJobByJobId(new Long(getModViewRecId()));
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：根据任务名称查询任务是否存在
	 * @author jacobliang
	 * @param jobName		任务名称
	 * @return
	 * @time Jan 12, 2011 10:56:51 AM
	 */
	public String findSysplSchedulerJobByJobName(){
		existJobName = schedulerService.findSysplSchedulerJobByJobName(getHttpServletRequest().getParameter("jobName"));
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：修改任务
	 * @author jacobliang
	 * @param  sysplSchedulerJob	任务对象
	 * @return
	 * @throws 
	 * @time Jan 18, 2011 3:56:27 PM
	 */
	public void modifySysplSchedulerJob()throws ApplicationException{
		try {
			schedulerService.setHttpServletRequest(this.getHttpServletRequest());
			schedulerService.modifySysplSchedulerJob(sysplSchedulerJob);
		this.returnSuccess();
		} catch (ApplicationException e) {
			e.printStackTrace();
			this.returnFailur(e.toString());
		}catch (SystemException e) {
			e.printStackTrace();
			this.returnFailur(this.ERROR);
		}
	}
	/**
	 * 功能说明：查询调度任务
	 * @author jacobliang
	 * @return Map 1 list 2 总数
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public String findSchedulerByCon(){
		Map map = schedulerService.findSchedulerByCon(getStart(),getLimit(),
				sysplSchedulerJob.getJobName(), sysplSchedulerJob.getTriggerType());
		jobList = (List<SysplSchedulerJob>)map.get(BUSI_LIST);
		count = (Integer)map.get(TOTAL_PROPERTY);
		return this.SUCCESS;
	}
	/**
	 * 功能说明：删除任务
	 * @author jacobliang
	 * @param   disEnableFlag	停用启用标志
	 * @param 	codeId		    编码ID
	 * @return  boolean			true为停用启用成功，false不能停用
	 * @throws 
	 * @time Sep 22, 2010 1:29:34 PM
	 */
	public String deleteSysplSchedulerJobByJobId(){
		try {
			schedulerService.setHttpServletRequest(this.getHttpServletRequest());
			schedulerService.deleteSysplSchedulerJobByJobId(new Long(getModViewRecId()));
		} catch (NumberFormatException e) {
			e.printStackTrace();		
			log.error("deleteSysplSchedulerJobByJobId() is not number. ", e);
			failMesg = this.ERROR;
		} catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：启动调度器
	 * @author jacobliang
	 * @time Jan 18, 2011 4:14:37 PM
	 */
	public String startScheduler(){
		try{
			schedulerService.setHttpServletRequest(this.getHttpServletRequest());
			schedulerService.startScheduler();
		}catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	/**
	 * 功能说明：停止调度器
	 * @author jacobliang
	 * @time Jan 18, 2011 4:15:32 PM
	 */
	public String stopScheduler(){
		try{
			schedulerService.setHttpServletRequest(this.getHttpServletRequest());
			schedulerService.stopScheduler();
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	/**
	 * 功能说明：启动job
	 * @author jacobliang
	 * @param	jobId				任务ID
	 * @time Jan 18, 2011 4:20:22 PM
	 */
	public String startJob(){
		try{
			schedulerService.setHttpServletRequest(this.getHttpServletRequest());
			schedulerService.startJob(new Long(getModViewRecId()));
		}catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	/**
	 * 功能说明：停止job
	 * @author jacobliang
	 * @param 	jobName				任务名称
	 * @return
	 * @time Jan 18, 2011 4:29:32 PM
	 */
	public String stopJob(){
		try{
			schedulerService.setHttpServletRequest(this.getHttpServletRequest());
			schedulerService.stopJob(new Long(getModViewRecId()),sysplSchedulerJob.getJobName());
		}catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：调度器状态
	 * @author jacobliang
	 * @return
	 * @time Jan 20, 2011 10:49:55 AM
	 */
	public String schedulerStatus(){
		if(SchedulerHandle.getInstance().getSched() == null){
			schedulerIsStart = false;
		}else{
			schedulerIsStart = true;
		}
		return this.SUCCESS;
	}
	
	public SysplSchedulerJob getModel() {
		return sysplSchedulerJob;
	}

	public void setSchedulerService(SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}
	public String getFailMesg() {
		return failMesg;
	}
	public void setFailMesg(String failMesg) {
		this.failMesg = failMesg;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<SysplSchedulerJob> getJobList() {
		return jobList;
	}
	public void setJobList(List<SysplSchedulerJob> jobList) {
		this.jobList = jobList;
	}
	public boolean getExistJobName(){
		return this.existJobName;
	}
	public void setExistJobName(boolean existJobName){
		this.existJobName = existJobName;
	}

	public boolean getSchedulerIsStart() {
		return schedulerIsStart;
	}

	public void setSchedulerIsStart(boolean schedulerIsStart) {
		this.schedulerIsStart = schedulerIsStart;
	}
}
