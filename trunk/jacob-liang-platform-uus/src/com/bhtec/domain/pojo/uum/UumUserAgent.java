package com.bhtec.domain.pojo.uum;

import java.util.Date;

/**
 * UumUserAgent entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UumUserAgent implements java.io.Serializable {

	// Fields

	private Long agentId;
	private UumUser uumUser;
	private Long agentUserId;
	private Long privilegeId;
	private Date beginDate;
	private Date endDate;

	// Constructors

	/** default constructor */
	public UumUserAgent() {
	}

	/** minimal constructor */
	public UumUserAgent(Long agentId) {
		this.agentId = agentId;
	}

	/** full constructor */
	public UumUserAgent(Long agentId, UumUser uumUser, Long agentUserId,
			Long privilegeId, Date beginDate, Date endDate) {
		this.agentId = agentId;
		this.uumUser = uumUser;
		this.agentUserId = agentUserId;
		this.privilegeId = privilegeId;
		this.beginDate = beginDate;
		this.endDate = endDate;
	}

	// Property accessors

	public Long getAgentId() {
		return this.agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public UumUser getUumUser() {
		return this.uumUser;
	}

	public void setUumUser(UumUser uumUser) {
		this.uumUser = uumUser;
	}

	public Long getAgentUserId() {
		return this.agentUserId;
	}

	public void setAgentUserId(Long agentUserId) {
		this.agentUserId = agentUserId;
	}

	public Long getPrivilegeId() {
		return this.privilegeId;
	}

	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}

	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}