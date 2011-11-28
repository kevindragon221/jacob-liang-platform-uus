package com.bhtec.domain.pojo.platform;

/**
 * SysplPrimarykeySeq entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysplPrimarykeySeq implements java.io.Serializable {

	// Fields

	private String pojoName;
	private String primarykeyName;

	// Constructors

	/** default constructor */
	public SysplPrimarykeySeq() {
	}

	/** full constructor */
	public SysplPrimarykeySeq(String pojoName, String primarykeyName) {
		this.pojoName = pojoName;
		this.primarykeyName = primarykeyName;
	}

	// Property accessors

	public String getPojoName() {
		return this.pojoName;
	}

	public void setPojoName(String pojoName) {
		this.pojoName = pojoName;
	}

	public String getPrimarykeyName() {
		return this.primarykeyName;
	}

	public void setPrimarykeyName(String primarykeyName) {
		this.primarykeyName = primarykeyName;
	}

}