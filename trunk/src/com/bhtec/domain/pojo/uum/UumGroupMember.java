package com.bhtec.domain.pojo.uum;

import java.util.Comparator;

/**
 * UumGroupMember entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UumGroupMember implements java.io.Serializable {

	// Fields

	private Long groupMemberId;
	private UumGroup uumGroup;
	private String groupMemberType;
	private Long memberResourceId;

	private String memberResourceName;
	private String memberResourceCode;
	// Constructors

	/** default constructor */
	public UumGroupMember() {
	}

	/** minimal constructor */
	public UumGroupMember(Long groupMemberId) {
		this.groupMemberId = groupMemberId;
	}

	/** full constructor */
	public UumGroupMember(Long groupMemberId, UumGroup uumGroup,
			String groupMemberType, Long memberResourceId) {
		this.groupMemberId = groupMemberId;
		this.uumGroup = uumGroup;
		this.groupMemberType = groupMemberType;
		this.memberResourceId = memberResourceId;
	}

	// Property accessors

	public Long getGroupMemberId() {
		return this.groupMemberId;
	}

	public void setGroupMemberId(Long groupMemberId) {
		this.groupMemberId = groupMemberId;
	}

	public UumGroup getUumGroup() {
		return this.uumGroup;
	}

	public void setUumGroup(UumGroup uumGroup) {
		this.uumGroup = uumGroup;
	}

	public String getGroupMemberType() {
		return this.groupMemberType;
	}

	public void setGroupMemberType(String groupMemberType) {
		this.groupMemberType = groupMemberType;
	}

	public Long getMemberResourceId() {
		return this.memberResourceId;
	}

	public void setMemberResourceId(Long memberResourceId) {
		this.memberResourceId = memberResourceId;
	}
	
	public boolean equals(Object obj){
		if(this.getClass() != obj.getClass())return false;
		UumGroupMember uumGroupMember = (UumGroupMember)obj;
		if(uumGroupMember.getGroupMemberId().longValue() == this.groupMemberId.longValue()){
			return true;
		}else{
			return false;
		}
	}
	
	public UumGroupMemberComp getUumGroupMemberCompInst(){
		UumGroupMemberComp uumGroupMemberComp = new UumGroupMemberComp();
		return uumGroupMemberComp;
	}
	
	class UumGroupMemberComp implements Comparator{
		public int compare(Object arg0, Object arg1) {
			UumGroupMember uumGroupMember1 = (UumGroupMember)arg0;
			UumGroupMember uumGroupMember2 = (UumGroupMember)arg1;
			int flag = uumGroupMember1.getGroupMemberId().compareTo(uumGroupMember2.getGroupMemberId());
			return flag;
		}
		
	}

	public String getMemberResourceName() {
		return memberResourceName;
	}

	public void setMemberResourceName(String memberResourceName) {
		this.memberResourceName = memberResourceName;
	}

	public String getMemberResourceCode() {
		return memberResourceCode;
	}

	public void setMemberResourceCode(String memberResourceCode) {
		this.memberResourceCode = memberResourceCode;
	}
}