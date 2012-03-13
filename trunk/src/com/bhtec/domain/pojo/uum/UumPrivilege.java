package com.bhtec.domain.pojo.uum;

/**
 * UumPrivilege entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UumPrivilege implements java.io.Serializable {

	// Fields

	private Long privilegeId;
	private String resourceId;
	private Long ownerId;
	private String ownerType;
	private String privilegeScope;
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
	public UumPrivilege(Long privilegeId, String resourceId, Long ownerId,
			String ownerType, String privilegeScope, String privilegeType) {
		this.privilegeId = privilegeId;
		this.resourceId = resourceId;
		this.ownerId = ownerId;
		this.ownerType = ownerType;
		this.privilegeScope = privilegeScope;
		this.privilegeType = privilegeType;
	}

	// Property accessors

	public Long getPrivilegeId() {
		return this.privilegeId;
	}

	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public Long getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerType() {
		return this.ownerType;
	}

	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	public String getPrivilegeScope() {
		return this.privilegeScope;
	}

	public void setPrivilegeScope(String privilegeScope) {
		this.privilegeScope = privilegeScope;
	}

	public String getPrivilegeType() {
		return this.privilegeType;
	}

	public void setPrivilegeType(String privilegeType) {
		this.privilegeType = privilegeType;
	}

}