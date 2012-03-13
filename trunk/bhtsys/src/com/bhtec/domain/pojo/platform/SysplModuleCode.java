package com.bhtec.domain.pojo.platform;

/**
 * SysplModuleCode entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysplModuleCode implements java.io.Serializable {

	// Fields

	private String codeEngName;
	private String codeContent;

	// Constructors

	/** default constructor */
	public SysplModuleCode() {
	}

	/** minimal constructor */
	public SysplModuleCode(String codeEngName) {
		this.codeEngName = codeEngName;
	}

	/** full constructor */
	public SysplModuleCode(String codeEngName, String codeContent) {
		this.codeEngName = codeEngName;
		this.codeContent = codeContent;
	}

	// Property accessors

	public String getCodeEngName() {
		return this.codeEngName;
	}

	public void setCodeEngName(String codeEngName) {
		this.codeEngName = codeEngName;
	}

	public String getCodeContent() {
		return this.codeContent;
	}

	public void setCodeContent(String codeContent) {
		this.codeContent = codeContent;
	}

}