package com.bhtec.domain.pojo.platform;

/**
 * SysplPageFuncArea entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysplPageFuncArea implements java.io.Serializable {

	// Fields

	private Long funcAreaId;
	private String funcAreaName;
	private String funcAreaResource;
	private String status;
	private String memo;
	private String creator;
	private String createrDate;

	// Constructors

	/** default constructor */
	public SysplPageFuncArea() {
	}

	/** minimal constructor */
	public SysplPageFuncArea(Long funcAreaId, String status) {
		this.funcAreaId = funcAreaId;
		this.status = status;
	}

	/** full constructor */
	public SysplPageFuncArea(Long funcAreaId, String funcAreaName,
			String funcAreaResource, String status, String memo,
			String creator, String createrDate) {
		this.funcAreaId = funcAreaId;
		this.funcAreaName = funcAreaName;
		this.funcAreaResource = funcAreaResource;
		this.status = status;
		this.memo = memo;
		this.creator = creator;
		this.createrDate = createrDate;
	}

	// Property accessors

	public Long getFuncAreaId() {
		return this.funcAreaId;
	}

	public void setFuncAreaId(Long funcAreaId) {
		this.funcAreaId = funcAreaId;
	}

	public String getFuncAreaName() {
		return this.funcAreaName;
	}

	public void setFuncAreaName(String funcAreaName) {
		this.funcAreaName = funcAreaName;
	}

	public String getFuncAreaResource() {
		return this.funcAreaResource;
	}

	public void setFuncAreaResource(String funcAreaResource) {
		this.funcAreaResource = funcAreaResource;
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

	public String getCreaterDate() {
		return this.createrDate;
	}

	public void setCreaterDate(String createrDate) {
		this.createrDate = createrDate;
	}

}