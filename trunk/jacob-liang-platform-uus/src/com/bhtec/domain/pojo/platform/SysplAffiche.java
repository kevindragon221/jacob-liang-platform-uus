package com.bhtec.domain.pojo.platform;

import java.util.Date;
import java.util.List;

/**
 * SysplAffiche entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysplAffiche implements java.io.Serializable {

	// Fields

	private Long afficheId;
	private String afficheTitle;
	private Date afficheInvalidate;
	private Short affichePulish;
	private String afficheContent;
	private List<SysplAccessory> accessoryList;
	// Constructors

	/** default constructor */
	public SysplAffiche() {
	}

	/** minimal constructor */
	public SysplAffiche(Long afficheId) {
		this.afficheId = afficheId;
	}

	/** full constructor */
	public SysplAffiche(Long afficheId, String afficheTitle,
			Date afficheInvalidate, Short affichePulish, String afficheContent,
			String afficheAccessory) {
		this.afficheId = afficheId;
		this.afficheTitle = afficheTitle;
		this.afficheInvalidate = afficheInvalidate;
		this.affichePulish = affichePulish;
		this.afficheContent = afficheContent;
	}

	// Property accessors

	public Long getAfficheId() {
		return this.afficheId;
	}

	public void setAfficheId(Long afficheId) {
		this.afficheId = afficheId;
	}

	public String getAfficheTitle() {
		return this.afficheTitle;
	}

	public void setAfficheTitle(String afficheTitle) {
		this.afficheTitle = afficheTitle;
	}

	public Date getAfficheInvalidate() {
		return this.afficheInvalidate;
	}

	public void setAfficheInvalidate(Date afficheInvalidate) {
		this.afficheInvalidate = afficheInvalidate;
	}

	public Short getAffichePulish() {
		return this.affichePulish;
	}

	public void setAffichePulish(Short affichePulish) {
		this.affichePulish = affichePulish;
	}

	public String getAfficheContent() {
		return this.afficheContent;
	}

	public void setAfficheContent(String afficheContent) {
		this.afficheContent = afficheContent;
	}

	public List<SysplAccessory> getAccessoryList() {
		return accessoryList;
	}

	public void setAccessoryList(List<SysplAccessory> accessoryList) {
		this.accessoryList = accessoryList;
	}
}