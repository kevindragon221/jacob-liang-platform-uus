package com.bhtec.domain.pojo.platform;

import java.util.HashSet;
import java.util.Set;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * SysplModOptRef entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysplModOptRef implements java.io.Serializable {

	// Fields

	private Long modOptId;
	private SysplOperate sysplOperate;
	private SysplModuleMemu sysplModuleMemu;
	
	// Constructors

	/** default constructor */
	public SysplModOptRef() {
	}

	/** minimal constructor */
	public SysplModOptRef(Long modOptId) {
		this.modOptId = modOptId;
	}

	/** full constructor */
	public SysplModOptRef(Long modOptId, SysplOperate sysplOperate,
			SysplModuleMemu sysplModuleMemu) {
		this.modOptId = modOptId;
		this.sysplOperate = sysplOperate;
		this.sysplModuleMemu = sysplModuleMemu;
	}

	// Property accessors

	public Long getModOptId() {
		return this.modOptId;
	}

	public void setModOptId(Long modOptId) {
		this.modOptId = modOptId;
	}

	public SysplOperate getSysplOperate() {
		return this.sysplOperate;
	}

	public void setSysplOperate(SysplOperate sysplOperate) {
		this.sysplOperate = sysplOperate;
	}
	@JSON(serialize=false)
	public SysplModuleMemu getSysplModuleMemu() {
		return this.sysplModuleMemu;
	}

	public void setSysplModuleMemu(SysplModuleMemu sysplModuleMemu) {
		this.sysplModuleMemu = sysplModuleMemu;
	}

	public boolean equals(Object obj){
		if(obj.getClass() != this.getClass())return false;
		SysplModOptRef sysplModOptRef = (SysplModOptRef)obj;
		if(sysplModOptRef.getModOptId().longValue() ==
			this.getModOptId().longValue()){
			return true;
		}else{
			return false;
		}
	}
}