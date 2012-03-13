package com.bhtec.domain.pojo.platform;

import java.util.Date;

/**
 * SysplSysOptLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysplSysOptLog implements java.io.Serializable {

	// Fields

	private Long optId;
	private String optModName;
	private String optName;
	private String optContent;
	private String optBusinessId;
	private Date optTime;
	private String optPcName;
	private String optPcIp;
	private String optUserName;
	private String optUserRole;
	private String optUserOgan;

	private Date startTime;
	private Date endTime;
	// Constructors

	/** default constructor */
	public SysplSysOptLog() {
	}

	/** minimal constructor */
	public SysplSysOptLog(Long optId) {
		this.optId = optId;
	}

	/** full constructor */
	public SysplSysOptLog(Long optId, String optModName, String optName,
			String optContent, String optBusinessId, Date optTime,
			String optPcName, String optPcIp, String optUserName,
			String optUserRole, String optUserOgan) {
		this.optId = optId;
		this.optModName = optModName;
		this.optName = optName;
		this.optContent = optContent;
		this.optBusinessId = optBusinessId;
		this.optTime = optTime;
		this.optPcName = optPcName;
		this.optPcIp = optPcIp;
		this.optUserName = optUserName;
		this.optUserRole = optUserRole;
		this.optUserOgan = optUserOgan;
	}

	// Property accessors

	public Long getOptId() {
		return this.optId;
	}

	public void setOptId(Long optId) {
		this.optId = optId;
	}

	public String getOptModName() {
		return this.optModName;
	}

	public void setOptModName(String optModName) {
		this.optModName = optModName;
	}

	public String getOptName() {
		return this.optName;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

	public String getOptContent() {
		return this.optContent;
	}

	public void setOptContent(String optContent) {
		this.optContent = optContent;
	}

	public String getOptBusinessId() {
		return this.optBusinessId;
	}

	public void setOptBusinessId(String optBusinessId) {
		this.optBusinessId = optBusinessId;
	}

	public Date getOptTime() {
		return this.optTime;
	}

	public void setOptTime(Date optTime) {
		this.optTime = optTime;
	}

	public String getOptPcName() {
		return this.optPcName;
	}

	public void setOptPcName(String optPcName) {
		this.optPcName = optPcName;
	}

	public String getOptPcIp() {
		return this.optPcIp;
	}

	public void setOptPcIp(String optPcIp) {
		this.optPcIp = optPcIp;
	}

	public String getOptUserName() {
		return this.optUserName;
	}

	public void setOptUserName(String optUserName) {
		this.optUserName = optUserName;
	}

	public String getOptUserRole() {
		return this.optUserRole;
	}

	public void setOptUserRole(String optUserRole) {
		this.optUserRole = optUserRole;
	}

	public String getOptUserOgan() {
		return this.optUserOgan;
	}

	public void setOptUserOgan(String optUserOgan) {
		this.optUserOgan = optUserOgan;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}