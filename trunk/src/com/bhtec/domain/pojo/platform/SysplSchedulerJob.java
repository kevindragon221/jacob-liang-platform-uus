package com.bhtec.domain.pojo.platform;

import java.util.Date;

/**
 * SysplSchedulerJob entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysplSchedulerJob implements java.io.Serializable {

	// Fields

	private Long jobId;
	private String jobName;
	private String jobClassDescript;
	private String triggerType;
	private String timeExpress;
	private Date startTime;
	private Date endTime;
	private Integer repeatTime;
	private Long splitTime;
	private String status;
	private String memo;
	private String creator;
	private Date createDate;

	// Constructors

	/** default constructor */
	public SysplSchedulerJob() {
	}

	/** minimal constructor */
	public SysplSchedulerJob(Long jobId, String status) {
		this.jobId = jobId;
		this.status = status;
	}

	/** full constructor */
	public SysplSchedulerJob(Long jobId, String jobName,
			String jobClassDescript, String triggerType, String timeExpress,
			Date startTime, Date endTime, Integer repeatTime, Long splitTime,
			String status, String memo, String creator, Date createDate) {
		this.jobId = jobId;
		this.jobName = jobName;
		this.jobClassDescript = jobClassDescript;
		this.triggerType = triggerType;
		this.timeExpress = timeExpress;
		this.startTime = startTime;
		this.endTime = endTime;
		this.repeatTime = repeatTime;
		this.splitTime = splitTime;
		this.status = status;
		this.memo = memo;
		this.creator = creator;
		this.createDate = createDate;
	}

	// Property accessors

	public Long getJobId() {
		return this.jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobClassDescript() {
		return this.jobClassDescript;
	}

	public void setJobClassDescript(String jobClassDescript) {
		this.jobClassDescript = jobClassDescript;
	}

	public String getTriggerType() {
		return this.triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public String getTimeExpress() {
		return this.timeExpress;
	}

	public void setTimeExpress(String timeExpress) {
		this.timeExpress = timeExpress;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getRepeatTime() {
		return this.repeatTime;
	}

	public void setRepeatTime(Integer repeatTime) {
		this.repeatTime = repeatTime;
	}

	public Long getSplitTime() {
		return this.splitTime;
	}

	public void setSplitTime(Long splitTime) {
		this.splitTime = splitTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}