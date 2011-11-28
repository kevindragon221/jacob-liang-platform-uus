package com.bhtec.domain.pojo.platform;

import java.util.Date;

/**
 * SysplSysParameter entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysplSysParameter implements java.io.Serializable {

	// Fields

	private Long paraId;
	private String paraName;
	private String paraKeyName;
	private String paraValue;
	private String paraType;
	private String memo;
	private String status;
	private String creator;
	private Date createDate;

	// Constructors

	/** default constructor */
	public SysplSysParameter() {
	}

	/** minimal constructor */
	public SysplSysParameter(Long paraId, String status) {
		this.paraId = paraId;
		this.status = status;
	}

	/** full constructor */
	public SysplSysParameter(Long paraId, String paraName, String paraKeyName,
			String paraValue, String paraType, String memo, String status,
			String creator, Date createDate) {
		this.paraId = paraId;
		this.paraName = paraName;
		this.paraKeyName = paraKeyName;
		this.paraValue = paraValue;
		this.paraType = paraType;
		this.memo = memo;
		this.status = status;
		this.creator = creator;
		this.createDate = createDate;
	}

	// Property accessors

	public Long getParaId() {
		return this.paraId;
	}

	public void setParaId(Long paraId) {
		this.paraId = paraId;
	}

	public String getParaName() {
		return this.paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}

	public String getParaKeyName() {
		return this.paraKeyName;
	}

	public void setParaKeyName(String paraKeyName) {
		this.paraKeyName = paraKeyName;
	}

	public String getParaValue() {
		return this.paraValue;
	}

	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}

	public String getParaType() {
		return this.paraType;
	}

	public void setParaType(String paraType) {
		this.paraType = paraType;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

}