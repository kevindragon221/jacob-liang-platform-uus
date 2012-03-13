package com.bhtec.domain.pojo.platform;

/**
 * SysplAccessory entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysplAccessory implements java.io.Serializable {

	// Fields

	private Long accessoryId;
	private Long infoId;
	private String accessoryName;

	// Constructors

	/** default constructor */
	public SysplAccessory() {
	}

	/** minimal constructor */
	public SysplAccessory(Long accessoryId) {
		this.accessoryId = accessoryId;
	}

	/** full constructor */
	public SysplAccessory(Long accessoryId, Long infoId, String accessoryName) {
		this.accessoryId = accessoryId;
		this.infoId = infoId;
		this.accessoryName = accessoryName;
	}

	// Property accessors

	public Long getAccessoryId() {
		return this.accessoryId;
	}

	public void setAccessoryId(Long accessoryId) {
		this.accessoryId = accessoryId;
	}

	public Long getInfoId() {
		return this.infoId;
	}

	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}

	public String getAccessoryName() {
		return this.accessoryName;
	}

	public void setAccessoryName(String accessoryName) {
		this.accessoryName = accessoryName;
	}

}