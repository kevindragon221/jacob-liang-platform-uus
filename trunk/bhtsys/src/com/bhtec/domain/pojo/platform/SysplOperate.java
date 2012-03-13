package com.bhtec.domain.pojo.platform;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * SysplOperate entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysplOperate implements java.io.Serializable {

	// Fields

	private Long operateId;
	private String operateName;
	private String optImgLink;
	private Integer optOrder;
	private Integer optGroup;
	private String memo;
	private String status;
	private String creator;
	private Date createDate;
	private String optFunLink;
	private Set sysplModOptRefs = new HashSet(0);

	// Constructors

	/** default constructor */
	public SysplOperate() {
	}

	/** minimal constructor */
	public SysplOperate(Long operateId, String status) {
		this.operateId = operateId;
		this.status = status;
	}

	/** full constructor */
	public SysplOperate(Long operateId, String operateName, String optImgLink,
			Integer optOrder, Integer optGroup, String memo, String status,
			String creator, Date createDate, String optFunLink,
			Set sysplModOptRefs) {
		this.operateId = operateId;
		this.operateName = operateName;
		this.optImgLink = optImgLink;
		this.optOrder = optOrder;
		this.optGroup = optGroup;
		this.memo = memo;
		this.status = status;
		this.creator = creator;
		this.createDate = createDate;
		this.optFunLink = optFunLink;
		this.sysplModOptRefs = sysplModOptRefs;
	}

	// Property accessors

	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public String getOperateName() {
		return this.operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public String getOptImgLink() {
		return this.optImgLink;
	}

	public void setOptImgLink(String optImgLink) {
		this.optImgLink = optImgLink;
	}

	public Integer getOptOrder() {
		return this.optOrder;
	}

	public void setOptOrder(Integer optOrder) {
		this.optOrder = optOrder;
	}

	public Integer getOptGroup() {
		return this.optGroup;
	}

	public void setOptGroup(Integer optGroup) {
		this.optGroup = optGroup;
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

	public String getOptFunLink() {
		return this.optFunLink;
	}

	public void setOptFunLink(String optFunLink) {
		this.optFunLink = optFunLink;
	}
	@JSON(serialize=false)
	public Set getSysplModOptRefs() {
		return this.sysplModOptRefs;
	}
	
	public void setSysplModOptRefs(Set sysplModOptRefs) {
		this.sysplModOptRefs = sysplModOptRefs;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public boolean equals(Object obj){
		if(obj.getClass() != this.getClass())return false;
		SysplOperate sysplOperate = (SysplOperate)obj;
		if(this.operateId.longValue() == sysplOperate.getOperateId().longValue()){
			return true;
		}else{
			return false;
		}
	}
}