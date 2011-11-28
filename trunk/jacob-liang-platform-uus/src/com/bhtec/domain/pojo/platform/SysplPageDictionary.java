package com.bhtec.domain.pojo.platform;

/**
 * SysplPageDictionary entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysplPageDictionary implements java.io.Serializable {

	// Fields

	private String pageName;
	private String pageSrcName;
	private String creator;
	private String createDate;

	// Constructors

	/** default constructor */
	public SysplPageDictionary() {
	}

	/** minimal constructor */
	public SysplPageDictionary(String pageName) {
		this.pageName = pageName;
	}

	/** full constructor */
	public SysplPageDictionary(String pageName, String pageSrcName,
			String creator, String createDate) {
		this.pageName = pageName;
		this.pageSrcName = pageSrcName;
		this.creator = creator;
		this.createDate = createDate;
	}

	// Property accessors

	public String getPageName() {
		return this.pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getPageSrcName() {
		return this.pageSrcName;
	}

	public void setPageSrcName(String pageSrcName) {
		this.pageSrcName = pageSrcName;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}