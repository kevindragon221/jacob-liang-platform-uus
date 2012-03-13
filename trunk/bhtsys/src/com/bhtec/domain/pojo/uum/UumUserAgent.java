package com.bhtec.domain.pojo.uum;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * UumUserAgent entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UumUserAgent implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1000000L;
	private Long agentId;
	private Long userId;
	private Long agentUserId;
	private Long orgRoleId;
	private Date beginDate;
	private Date endDate;
	private String reason;
	private Set uumUserAgentPrivileges = new HashSet(0);
	
	private String userName;
	private String userCode;
	private String organName;
	private String roleName;
	private Long organId;
	private Long roleId;
	// Constructors

	/** default constructor */
	public UumUserAgent() {
	}

	/** minimal constructor */
	public UumUserAgent(Long agentId) {
		this.agentId = agentId;
	}

	/** full constructor */
	public UumUserAgent(Long agentId, Long userId, Long agentUserId,
			String askDirection, Date beginDate, Date endDate, String reason,
			String askResult) {
		this.agentId = agentId;
		this.userId = userId;
		this.agentUserId = agentUserId;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.reason = reason;
	}

	// Property accessors

	public Long getAgentId() {
		return this.agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getAgentUserId() {
		return this.agentUserId;
	}

	public void setAgentUserId(Long agentUserId) {
		this.agentUserId = agentUserId;
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

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Long getOrgRoleId() {
		return orgRoleId;
	}

	public void setOrgRoleId(Long orgRoleId) {
		this.orgRoleId = orgRoleId;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getOrganId() {
		return organId;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	@JSON(serialize=false)
	public Set getUumUserAgentPrivileges() {
		return uumUserAgentPrivileges;
	}

	public void setUumUserAgentPrivileges(Set uumUserAgentPrivileges) {
		this.uumUserAgentPrivileges = uumUserAgentPrivileges;
	}
}