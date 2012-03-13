package com.bhtec.domain.pojo.uum;

/**
 * Portal entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Portal implements java.io.Serializable {

	// Fields

	private Long portalId;
	private Long portalUserid;
	private String portlet1;
	private String portlet2;
	private String portlet3;
	private String portlet4;
	private String portlet5;
	private String portlet6;
	private String portlet7;
	private String portlet8;
	private String portlet9;

	// Constructors

	/** default constructor */
	public Portal() {
	}

	/** minimal constructor */
	public Portal(Long portalId) {
		this.portalId = portalId;
	}

	/** full constructor */
	public Portal(Long portalId, Long portalUserid, String portlet1,
			String portlet2, String portlet3, String portlet4, String portlet5,
			String portlet6, String portlet7, String portlet8, String portlet9) {
		this.portalId = portalId;
		this.portalUserid = portalUserid;
		this.portlet1 = portlet1;
		this.portlet2 = portlet2;
		this.portlet3 = portlet3;
		this.portlet4 = portlet4;
		this.portlet5 = portlet5;
		this.portlet6 = portlet6;
		this.portlet7 = portlet7;
		this.portlet8 = portlet8;
		this.portlet9 = portlet9;
	}

	// Property accessors

	public Long getPortalId() {
		return this.portalId;
	}

	public void setPortalId(Long portalId) {
		this.portalId = portalId;
	}

	public Long getPortalUserid() {
		return this.portalUserid;
	}

	public void setPortalUserid(Long portalUserid) {
		this.portalUserid = portalUserid;
	}

	public String getPortlet1() {
		return this.portlet1;
	}

	public void setPortlet1(String portlet1) {
		this.portlet1 = portlet1;
	}

	public String getPortlet2() {
		return this.portlet2;
	}

	public void setPortlet2(String portlet2) {
		this.portlet2 = portlet2;
	}

	public String getPortlet3() {
		return this.portlet3;
	}

	public void setPortlet3(String portlet3) {
		this.portlet3 = portlet3;
	}

	public String getPortlet4() {
		return this.portlet4;
	}

	public void setPortlet4(String portlet4) {
		this.portlet4 = portlet4;
	}

	public String getPortlet5() {
		return this.portlet5;
	}

	public void setPortlet5(String portlet5) {
		this.portlet5 = portlet5;
	}

	public String getPortlet6() {
		return this.portlet6;
	}

	public void setPortlet6(String portlet6) {
		this.portlet6 = portlet6;
	}

	public String getPortlet7() {
		return this.portlet7;
	}

	public void setPortlet7(String portlet7) {
		this.portlet7 = portlet7;
	}

	public String getPortlet8() {
		return this.portlet8;
	}

	public void setPortlet8(String portlet8) {
		this.portlet8 = portlet8;
	}

	public String getPortlet9() {
		return this.portlet9;
	}

	public void setPortlet9(String portlet9) {
		this.portlet9 = portlet9;
	}

}