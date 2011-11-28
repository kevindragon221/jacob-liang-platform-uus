package com.bhtec.domain.pojo.uum;

/**
 * UumUserCommfun entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UumUserCommfun implements java.io.Serializable {

	// Fields

	private UumUserCommfunId id;

	// Constructors

	/** default constructor */
	public UumUserCommfun() {
	}

	/** full constructor */
	public UumUserCommfun(UumUserCommfunId id, Long roleId) {
		this.id = id;
	}

	// Property accessors

	public UumUserCommfunId getId() {
		return this.id;
	}

	public void setId(UumUserCommfunId id) {
		this.id = id;
	}

}