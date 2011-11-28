package com.bhtec.domain.pojo.uum;

import com.bhtec.domain.pojo.platform.SysplModOptRef;

/**
 * UumPrivilege entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UumPrivilege implements java.io.Serializable {

	// Fields

	private Long privilegeId;
	private SysplModOptRef sysplModOptRef;
	private Long ownerId;
	private String privilegeType;

	// Constructors

	/** default constructor */
	public UumPrivilege() {
	}

	/** minimal constructor */
	public UumPrivilege(Long privilegeId) {
		this.privilegeId = privilegeId;
	}

	/** full constructor */
	public UumPrivilege(Long privilegeId, SysplModOptRef sysplModOptRef,
			Long ownerId, String privilegeType) {
		this.privilegeId = privilegeId;
		this.sysplModOptRef = sysplModOptRef;
		this.ownerId = ownerId;
		this.privilegeType = privilegeType;
	}

	// Property accessors

	public Long getPrivilegeId() {
		return this.privilegeId;
	}

	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}

	public SysplModOptRef getSysplModOptRef() {
		return this.sysplModOptRef;
	}

	public void setSysplModOptRef(SysplModOptRef sysplModOptRef) {
		this.sysplModOptRef = sysplModOptRef;
	}

	public Long getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getPrivilegeType() {
		return this.privilegeType;
	}

	public void setPrivilegeType(String privilegeType) {
		this.privilegeType = privilegeType;
	}

}