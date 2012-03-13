package com.bhtec.domain.pojo.uum;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.googlecode.jsonplugin.annotations.JSON;

/**
 * UumOrgan entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UumOrgan implements java.io.Serializable {

	// Fields

	private Long orgId;
	private UumOrgan uumOrgan;
	private String orgSimpleName;
	private String orgFullName;
	private String orgCode;
	private String orgAddress1;
	private String orgAddress2;
	private String orgTel1;
	private String orgTel2;
	private Date orgBeginDate;
	private String orgType;
	private String orgFax;
	private String orgPostal;
	private String orgLegal;
	private String orgTaxNo;
	private String orgRegNo;
	private Long orgBelongDist;
	private String status;
	private String memo;
	private String creator;
	private Date createDate;
	private Set uumOrgRoleRefs = new HashSet(0);
	private Set uumUsers = new HashSet(0);
	private Set uumOrgans = new HashSet(0);
	
	private Long uporgId;
	private String upOrgName;
	private boolean isLeaf;
	private String dataIconCls;
	
	private String orgBelongDistName;
	// Constructors

	/** default constructor */
	public UumOrgan() {
	}

	/** minimal constructor */
	public UumOrgan(Long orgId, String status) {
		this.orgId = orgId;
		this.status = status;
	}

	/** full constructor */
	public UumOrgan(Long orgId, UumOrgan uumOrgan, String orgSimpleName,
			String orgFullName, String orgCode, String orgAddress1,
			String orgAddress2, String orgTel1, String orgTel2,
			Date orgBeginDate, String orgType, String orgFax, String orgPostal,
			String orgLegal, String orgTaxNo, String orgRegNo,
			Long orgBelongDist, String status, String memo, String creator,
			Date createDate, Set uumOrgRoleRefs, Set uumUsers, Set uumOrgans) {
		this.orgId = orgId;
		this.uumOrgan = uumOrgan;
		this.orgSimpleName = orgSimpleName;
		this.orgFullName = orgFullName;
		this.orgCode = orgCode;
		this.orgAddress1 = orgAddress1;
		this.orgAddress2 = orgAddress2;
		this.orgTel1 = orgTel1;
		this.orgTel2 = orgTel2;
		this.orgBeginDate = orgBeginDate;
		this.orgType = orgType;
		this.orgFax = orgFax;
		this.orgPostal = orgPostal;
		this.orgLegal = orgLegal;
		this.orgTaxNo = orgTaxNo;
		this.orgRegNo = orgRegNo;
		this.orgBelongDist = orgBelongDist;
		this.status = status;
		this.memo = memo;
		this.creator = creator;
		this.createDate = createDate;
		this.uumOrgRoleRefs = uumOrgRoleRefs;
		this.uumUsers = uumUsers;
		this.uumOrgans = uumOrgans;
	}

	// Property accessors

	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public UumOrgan getUumOrgan() {
		return this.uumOrgan;
	}

	public void setUumOrgan(UumOrgan uumOrgan) {
		this.uumOrgan = uumOrgan;
	}

	public String getOrgSimpleName() {
		return this.orgSimpleName;
	}

	public void setOrgSimpleName(String orgSimpleName) {
		this.orgSimpleName = orgSimpleName;
	}

	public String getOrgFullName() {
		return this.orgFullName;
	}

	public void setOrgFullName(String orgFullName) {
		this.orgFullName = orgFullName;
	}

	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgAddress1() {
		return this.orgAddress1;
	}

	public void setOrgAddress1(String orgAddress1) {
		this.orgAddress1 = orgAddress1;
	}

	public String getOrgAddress2() {
		return this.orgAddress2;
	}

	public void setOrgAddress2(String orgAddress2) {
		this.orgAddress2 = orgAddress2;
	}

	public String getOrgTel1() {
		return this.orgTel1;
	}

	public void setOrgTel1(String orgTel1) {
		this.orgTel1 = orgTel1;
	}

	public String getOrgTel2() {
		return this.orgTel2;
	}

	public void setOrgTel2(String orgTel2) {
		this.orgTel2 = orgTel2;
	}

	public Date getOrgBeginDate() {
		return this.orgBeginDate;
	}

	public void setOrgBeginDate(Date orgBeginDate) {
		this.orgBeginDate = orgBeginDate;
	}

	public String getOrgType() {
		return this.orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgFax() {
		return this.orgFax;
	}

	public void setOrgFax(String orgFax) {
		this.orgFax = orgFax;
	}

	public String getOrgPostal() {
		return this.orgPostal;
	}

	public void setOrgPostal(String orgPostal) {
		this.orgPostal = orgPostal;
	}

	public String getOrgLegal() {
		return this.orgLegal;
	}

	public void setOrgLegal(String orgLegal) {
		this.orgLegal = orgLegal;
	}

	public String getOrgTaxNo() {
		return this.orgTaxNo;
	}

	public void setOrgTaxNo(String orgTaxNo) {
		this.orgTaxNo = orgTaxNo;
	}

	public String getOrgRegNo() {
		return this.orgRegNo;
	}

	public void setOrgRegNo(String orgRegNo) {
		this.orgRegNo = orgRegNo;
	}

	public Long getOrgBelongDist() {
		return this.orgBelongDist;
	}

	public void setOrgBelongDist(Long orgBelongDist) {
		this.orgBelongDist = orgBelongDist;
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

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@JSON(serialize=false)
	public Set getUumOrgRoleRefs() {
		return this.uumOrgRoleRefs;
	}

	public void setUumOrgRoleRefs(Set uumOrgRoleRefs) {
		this.uumOrgRoleRefs = uumOrgRoleRefs;
	}
	@JSON(serialize=false)
	public Set getUumUsers() {
		return this.uumUsers;
	}

	public void setUumUsers(Set uumUsers) {
		this.uumUsers = uumUsers;
	}
	@JSON(serialize=false)
	public Set getUumOrgans() {
		return this.uumOrgans;
	}

	public void setUumOrgans(Set uumOrgans) {
		this.uumOrgans = uumOrgans;
	}

	public String getUpOrgName() {
		return upOrgName;
	}

	public void setUpOrgName(String upOrgName) {
		this.upOrgName = upOrgName;
	}


	public Long getUporgId() {
		return uporgId;
	}

	public void setUporgId(Long uporgId) {
		this.uporgId = uporgId;
	}

	public boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getDataIconCls() {
		return dataIconCls;
	}

	public void setDataIconCls(String dataIconCls) {
		this.dataIconCls = dataIconCls;
	}
	
	public boolean equals(Object obj){
		if(this.getClass() != obj.getClass())return false;
		UumOrgan uumOrgan = (UumOrgan)obj;
		if(this.orgId.longValue() ==  uumOrgan.getOrgId().longValue()){
			return true;
		}else{
			return false;
		}
	}

	public String getOrgBelongDistName() {
		return orgBelongDistName;
	}

	public void setOrgBelongDistName(String orgBelongDistName) {
		this.orgBelongDistName = orgBelongDistName;
	}

}