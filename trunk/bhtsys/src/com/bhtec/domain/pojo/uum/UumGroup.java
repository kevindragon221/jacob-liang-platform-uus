package com.bhtec.domain.pojo.uum;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * UumGroup entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UumGroup implements java.io.Serializable {

	// Fields

	private Long groupId;
	private String groupType;
	private String groupName;
	private String memo;
	private String status;
	private String creator;
	private Date createDate;
	private Set uumGroupMembers = new HashSet(0);

	// Constructors

	/** default constructor */
	public UumGroup() {
	}

	/** minimal constructor */
	public UumGroup(Long groupId, String status) {
		this.groupId = groupId;
		this.status = status;
	}

	/** full constructor */
	public UumGroup(Long groupId, String groupType, String groupName,
			String memo, String status, String creator, Date createDate,
			Set uumGroupMembers) {
		this.groupId = groupId;
		this.groupType = groupType;
		this.groupName = groupName;
		this.memo = memo;
		this.status = status;
		this.creator = creator;
		this.createDate = createDate;
		this.uumGroupMembers = uumGroupMembers;
	}

	// Property accessors

	public Long getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupType() {
		return this.groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	public Set getUumGroupMembers() {
		return this.uumGroupMembers;
	}

	public void setUumGroupMembers(Set uumGroupMembers) {
		this.uumGroupMembers = uumGroupMembers;
	}


}