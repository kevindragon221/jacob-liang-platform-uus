package com.bhtec.domain.pojo.uum;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * UumRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UumRole implements java.io.Serializable {

	// Fields

	private Long roleId;
	private String roleName;
	private String status;
	private String memo;
	private String creator;
	private Date createDate;
	private Short roleLevel;
	private Set uumOrgRoleRefs = new HashSet(0);

	// Constructors

	/** default constructor */
	public UumRole() {
	}


	// Property accessors

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public Short getRoleLevel() {
		return this.roleLevel;
	}

	public void setRoleLevel(Short roleLevel) {
		this.roleLevel = roleLevel;
	}
	@JSON(serialize= false)
	public Set getUumOrgRoleRefs() {
		return this.uumOrgRoleRefs;
	}

	public void setUumOrgRoleRefs(Set uumOrgRoleRefs) {
		this.uumOrgRoleRefs = uumOrgRoleRefs;
	}

}