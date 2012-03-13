package com.bhtec.domain.pojo.platform;

import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * SysplModuleMemu entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings({ "unchecked", "serial" })
public class SysplModuleMemu implements java.io.Serializable,Comparator {

	// Fields

	private Long moduleId;
	private SysplModuleMemu sysplModuleMemu;
	private String modName;
	private String modEnId;
	private String modImgCls;
	private String modLevel;
	private Integer modOrder;
	private String status;
	private String memo;
	private String creator;
	private Date createDate;
	private Set sysplModuleMemus = new HashSet(0);
	
	private Long upModId;
	private String upModName;
	private boolean isLeaf;
	private String optFunLink;//为操作准备功能链接
	private String belongToSys;//所属系统
	private String modPageType;//页面类型

	// Constructors

	/** default constructor */
	public SysplModuleMemu() {
	}

	/** minimal constructor */
	public SysplModuleMemu(Long moduleId) {
		this.moduleId = moduleId;
	}

	/** full constructor */
	public SysplModuleMemu(Long moduleId, SysplModuleMemu sysplModuleMemu,
			String modName, String modEnId, String modImgCls, String modLevel,
			Integer modOrder,  String status,
			String memo, String creator, Date createDate, Set sysplModuleMemus) {
		this.moduleId = moduleId;
		this.sysplModuleMemu = sysplModuleMemu;
		this.modName = modName;
		this.modEnId = modEnId;
		this.modImgCls = modImgCls;
		this.modLevel = modLevel;
		this.modOrder = modOrder;
		this.status = status;
		this.memo = memo;
		this.creator = creator;
		this.createDate = createDate;
		this.sysplModuleMemus = sysplModuleMemus;
	}

	// Property accessors

	public Long getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	@JSON(serialize=false)
	public SysplModuleMemu getSysplModuleMemu() {
		return this.sysplModuleMemu;
	}

	public void setSysplModuleMemu(SysplModuleMemu sysplModuleMemu) {
		this.sysplModuleMemu = sysplModuleMemu;
	}

	public String getModName() {
		return this.modName;
	}

	public void setModName(String modName) {
		this.modName = modName;
	}

	public String getModEnId() {
		return this.modEnId;
	}

	public void setModEnId(String modEnId) {
		this.modEnId = modEnId;
	}

	public String getModImgCls() {
		return this.modImgCls;
	}

	public void setModImgCls(String modImgCls) {
		this.modImgCls = modImgCls;
	}

	public String getModLevel() {
		return this.modLevel;
	}

	public void setModLevel(String modLevel) {
		this.modLevel = modLevel;
	}

	public Integer getModOrder() {
		return this.modOrder;
	}

	public void setModOrder(Integer modOrder) {
		this.modOrder = modOrder;
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
	public Set getSysplModuleMemus() {
		return this.sysplModuleMemus;
	}

	public void setSysplModuleMemus(Set sysplModuleMemus) {
		this.sysplModuleMemus = sysplModuleMemus;
	}
	
	public boolean equals(Object obj){
		if(this.getClass() != obj.getClass())return false;
		SysplModuleMemu sysplModuleMemu = (SysplModuleMemu)obj;
		if(this.moduleId.longValue() ==  sysplModuleMemu.getModuleId().longValue()){
			return true;
		}else{
			return false;
		}
	}

	public Long getUpModId() {
		return upModId;
	}

	public void setUpModId(Long upModId) {
		this.upModId = upModId;
	}

	public String getUpModName() {
		return upModName;
	}

	public void setUpModName(String upModName) {
		this.upModName = upModName;
	}

	public boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public int compare(Object obj1, Object obj2) {
		SysplModuleMemu sysplModuleMemu1 = (SysplModuleMemu)obj1;
		SysplModuleMemu sysplModuleMemu2 = (SysplModuleMemu)obj2;
		return sysplModuleMemu1.getModuleId().compareTo(sysplModuleMemu2.getModuleId());
	}

	public String getOptFunLink() {
		return optFunLink;
	}

	public void setOptFunLink(String optFunLink) {
		this.optFunLink = optFunLink;
	}

	public String getBelongToSys() {
		return belongToSys;
	}

	public void setBelongToSys(String belongToSys) {
		this.belongToSys = belongToSys;
	}

	public String getModPageType() {
		return modPageType;
	}

	public void setModPageType(String modPageType) {
		this.modPageType = modPageType;
	}

}