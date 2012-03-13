package com.bhtec.domain.pojo.platform;

import java.util.Comparator;

/**
 * SysplDicSmallType entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysplDicSmallType implements java.io.Serializable {

	// Fields
	private Long smallTypeId;
	private SysplDicBigType sysplDicBigType;
	private String smallTypeName;
	private String smallTypeCode;

	// Constructors

	/** default constructor */
	public SysplDicSmallType() {
	}

	/** minimal constructor */
	public SysplDicSmallType(Long smallTypeId) {
		this.smallTypeId = smallTypeId;
	}

	/** full constructor */
	public SysplDicSmallType(Long smallTypeId, SysplDicBigType sysplDicBigType,
			String smallTypeName, String smallTypeCode) {
		this.smallTypeId = smallTypeId;
		this.sysplDicBigType = sysplDicBigType;
		this.smallTypeName = smallTypeName;
		this.smallTypeCode = smallTypeCode;
	}

	// Property accessors

	public Long getSmallTypeId() {
		return this.smallTypeId;
	}

	public void setSmallTypeId(Long smallTypeId) {
		this.smallTypeId = smallTypeId;
	}

	public SysplDicBigType getSysplDicBigType() {
		return this.sysplDicBigType;
	}

	public void setSysplDicBigType(SysplDicBigType sysplDicBigType) {
		this.sysplDicBigType = sysplDicBigType;
	}

	public String getSmallTypeName() {
		return this.smallTypeName;
	}

	public void setSmallTypeName(String smallTypeName) {
		this.smallTypeName = smallTypeName;
	}

	public String getSmallTypeCode() {
		return this.smallTypeCode;
	}

	public void setSmallTypeCode(String smallTypeCode) {
		this.smallTypeCode = smallTypeCode;
	}
	
	public boolean equals(Object obj){
		if(this.getClass() != obj.getClass())return false;
		SysplDicSmallType sysplDicSmallType = (SysplDicSmallType)obj;
		if(sysplDicSmallType.getSmallTypeId().longValue() == this.smallTypeId.longValue()){
			return true;
		}else{
			return false;
		}
	}
	
	public SysplDicSmallTypeComp getSysplDicSmallTypeInst(){
		SysplDicSmallTypeComp sysplDicSmallTypeComp = new SysplDicSmallTypeComp();
		return sysplDicSmallTypeComp;
	}
	
	class SysplDicSmallTypeComp implements Comparator{
		public int compare(Object arg0, Object arg1) {
			SysplDicSmallType sysplDicSmallType1 = (SysplDicSmallType)arg0;
			SysplDicSmallType sysplDicSmallType2 = (SysplDicSmallType)arg1;
			int flag = sysplDicSmallType1.getSmallTypeId().compareTo(sysplDicSmallType2.getSmallTypeId());
			return flag;
		}
		
	}

}