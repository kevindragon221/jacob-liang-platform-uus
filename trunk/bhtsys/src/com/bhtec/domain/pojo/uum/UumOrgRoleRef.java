package com.bhtec.domain.pojo.uum;

import java.util.HashSet;
import java.util.Set;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * UumOrgRoleRef entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UumOrgRoleRef implements java.io.Serializable {

	// Fields

	private Long orgRoleId;
	private UumRole uumRole;
	private UumOrgan uumOrgan;
	private Set uumRoleUserRefs = new HashSet(0);

	// Constructors

	/** default constructor */
	public UumOrgRoleRef() {
	}

	/** minimal constructor */
	public UumOrgRoleRef(Long orgRoleId) {
		this.orgRoleId = orgRoleId;
	}

	/** full constructor */
	public UumOrgRoleRef(Long orgRoleId, UumRole uumRole, UumOrgan uumOrgan,
			Set uumRoleUserRefs) {
		this.orgRoleId = orgRoleId;
		this.uumRole = uumRole;
		this.uumOrgan = uumOrgan;
		this.uumRoleUserRefs = uumRoleUserRefs;
	}

	// Property accessors

	public Long getOrgRoleId() {
		return this.orgRoleId;
	}

	public void setOrgRoleId(Long orgRoleId) {
		this.orgRoleId = orgRoleId;
	}

	public UumRole getUumRole() {
		return this.uumRole;
	}

	public void setUumRole(UumRole uumRole) {
		this.uumRole = uumRole;
	}

	public UumOrgan getUumOrgan() {
		return this.uumOrgan;
	}

	public void setUumOrgan(UumOrgan uumOrgan) {
		this.uumOrgan = uumOrgan;
	}
	@JSON(serialize= false)
	public Set getUumRoleUserRefs() {
		return this.uumRoleUserRefs;
	}

	public void setUumRoleUserRefs(Set uumRoleUserRefs) {
		this.uumRoleUserRefs = uumRoleUserRefs;
	}

}