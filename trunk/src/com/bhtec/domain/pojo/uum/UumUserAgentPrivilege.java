package com.bhtec.domain.pojo.uum;


/**
 * UumUserAgentPrivilege entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UumUserAgentPrivilege implements java.io.Serializable {

	// Fields

	private Long userAgentPriId;
	private UumUserAgent uumUserAgent;
	private String privilegeId;
	private String privilegeType;

	// Constructors

	/** default constructor */
	public UumUserAgentPrivilege() {
	}

	/** minimal constructor */
	public UumUserAgentPrivilege(Long userAgentPriId) {
		this.userAgentPriId = userAgentPriId;
	}

	/** full constructor */
	public UumUserAgentPrivilege(Long userAgentPriId, UumUserAgent uumUserAgent,
			String privilegeId, String privilegeType) {
		this.userAgentPriId = userAgentPriId;
		this.uumUserAgent = uumUserAgent;
		this.privilegeId = privilegeId;
		this.privilegeType = privilegeType;
	}

	// Property accessors

	public Long getUserAgentPriId() {
		return this.userAgentPriId;
	}

	public void setUserAgentPriId(Long userAgentPriId) {
		this.userAgentPriId = userAgentPriId;
	}

	public String getPrivilegeId() {
		return this.privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getPrivilegeType() {
		return this.privilegeType;
	}

	public void setPrivilegeType(String privilegeType) {
		this.privilegeType = privilegeType;
	}

	public UumUserAgent getUumUserAgent() {
		return uumUserAgent;
	}

	public void setUumUserAgent(UumUserAgent uumUserAgent) {
		this.uumUserAgent = uumUserAgent;
	}

}