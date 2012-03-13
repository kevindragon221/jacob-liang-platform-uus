package com.bhtec.domain.pojo.uum;

/**
 * UumUserPageLayout entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UumUserPageLayout implements java.io.Serializable {

	// Fields

	private Long userId;
	private String header;
	private String navigate;
	private String outlookbar;
	private String mainpage;
	private String statebar;

	// Constructors

	/** default constructor */
	public UumUserPageLayout() {
	}

	/** minimal constructor */
	public UumUserPageLayout(Long userId) {
		this.userId = userId;
	}

	/** full constructor */
	public UumUserPageLayout(Long userId, String header, String navigate,
			String outlookbar, String mainpage, String statebar) {
		this.userId = userId;
		this.header = header;
		this.navigate = navigate;
		this.outlookbar = outlookbar;
		this.mainpage = mainpage;
		this.statebar = statebar;
	}

	// Property accessors

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getHeader() {
		return this.header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getNavigate() {
		return this.navigate;
	}

	public void setNavigate(String navigate) {
		this.navigate = navigate;
	}

	public String getOutlookbar() {
		return this.outlookbar;
	}

	public void setOutlookbar(String outlookbar) {
		this.outlookbar = outlookbar;
	}

	public String getMainpage() {
		return this.mainpage;
	}

	public void setMainpage(String mainpage) {
		this.mainpage = mainpage;
	}

	public String getStatebar() {
		return this.statebar;
	}

	public void setStatebar(String statebar) {
		this.statebar = statebar;
	}

}