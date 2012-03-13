package com.bhtec.domain.pojo.platform;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * SysplDicBigType entity. @author MyEclipse Persistence Tools
 */

public class SysplDicBigType  implements java.io.Serializable {


    // Fields    

     private Long bigTypeId;
     private String bigTypeName;
     private String bigTypeCode;
     private String memo;
     private String status;
     private String creator;
     private Date createDate;
     private Set sysplDicSmallTypes = new HashSet(0);


    // Constructors

    /** default constructor */
    public SysplDicBigType() {
    }

	/** minimal constructor */
    public SysplDicBigType(Long bigTypeId, String status) {
        this.bigTypeId = bigTypeId;
        this.status = status;
    }
    
    /** full constructor */
    public SysplDicBigType(Long bigTypeId, String bigTypeName, String bigTypeCode,
    		String memo, String status, String creator, Date createDate, Set sysplDicSmallTypes) {
        this.bigTypeId = bigTypeId;
        this.bigTypeName = bigTypeName;
        this.bigTypeCode = bigTypeCode;
        this.memo = memo;
        this.status = status;
        this.creator = creator;
        this.createDate = createDate;
        this.sysplDicSmallTypes = sysplDicSmallTypes;
    }

   
    // Property accessors

    public Long getBigTypeId() {
        return this.bigTypeId;
    }
    
    public void setBigTypeId(Long bigTypeId) {
        this.bigTypeId = bigTypeId;
    }

    public String getBigTypeName() {
        return this.bigTypeName;
    }
    
    public void setBigTypeName(String bigTypeName) {
        this.bigTypeName = bigTypeName;
    }

    public String getBigTypeCode() {
        return this.bigTypeCode;
    }
    
    public void setBigTypeCode(String bigTypeCode) {
        this.bigTypeCode = bigTypeCode;
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

    public Set getSysplDicSmallTypes() {
        return this.sysplDicSmallTypes;
    }
    
    public void setSysplDicSmallTypes(Set sysplDicSmallTypes) {
        this.sysplDicSmallTypes = sysplDicSmallTypes;
    }
   








}