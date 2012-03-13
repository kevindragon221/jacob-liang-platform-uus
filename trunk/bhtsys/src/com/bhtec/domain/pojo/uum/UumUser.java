package com.bhtec.domain.pojo.uum;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * UumUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UumUser implements java.io.Serializable {

	// Fields

	private Long userId;
	private UumOrgan uumOrgan;
	private String userCode;
	private String userNamePy;
	private String userName;
	private String userPassword;
	private String userGender;
	private String userPosition;
	private String userPhotoUrl;
	private String userQq;
	private String userMsn;
	private String userMobile;
	private String userMobile2;
	private String userOfficeTel;
	private String userFamilyTel;
	private String userEmail;
	private Date userAvidate;
	private String userIsAgent;
	private String memo;
	private String status;
	private String creator;
	private Date createDate;
	private String userAddress;
	private Set uumRoleUserRefs = new HashSet(0);
	
	private Long uumOrgRoleId;
	private String uumRoleName;
	private String uumOrgName;
	private Long belongOrganId;

	// Constructors

	/** default constructor */
	public UumUser() {
	}

	/** minimal constructor */
	public UumUser(Long userId, String status) {
		this.userId = userId;
		this.status = status;
	}

	/** full constructor */
	public UumUser(Long userId, UumOrgan uumOrgan, String userCode,
			String userName, String userPassword, String userGender,
			String userPosition, String userPhotoUrl, String userQq,
			String userMsn, String userMobile, String userMobile2,
			String userOfficeTel, String userFamilyTel, String userEmail,
			Date userAvidate, String userIsAgent, String memo, String status,
			String creator, Date createDate, Set uumUserAgents,
			Set uumRoleUserRefs) {
		this.userId = userId;
		this.uumOrgan = uumOrgan;
		this.userCode = userCode;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userGender = userGender;
		this.userPosition = userPosition;
		this.userPhotoUrl = userPhotoUrl;
		this.userQq = userQq;
		this.userMsn = userMsn;
		this.userMobile = userMobile;
		this.userMobile2 = userMobile2;
		this.userOfficeTel = userOfficeTel;
		this.userFamilyTel = userFamilyTel;
		this.userEmail = userEmail;
		this.userAvidate = userAvidate;
		this.userIsAgent = userIsAgent;
		this.memo = memo;
		this.status = status;
		this.creator = creator;
		this.createDate = createDate;
		this.uumRoleUserRefs = uumRoleUserRefs;
	}

	// Property accessors

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public UumOrgan getUumOrgan() {
		return this.uumOrgan;
	}

	public void setUumOrgan(UumOrgan uumOrgan) {
		this.uumOrgan = uumOrgan;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserGender() {
		return this.userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserPosition() {
		return this.userPosition;
	}

	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}

	public String getUserPhotoUrl() {
		return this.userPhotoUrl;
	}

	public void setUserPhotoUrl(String userPhotoUrl) {
		this.userPhotoUrl = userPhotoUrl;
	}

	public String getUserQq() {
		return this.userQq;
	}

	public void setUserQq(String userQq) {
		this.userQq = userQq;
	}

	public String getUserMsn() {
		return this.userMsn;
	}

	public void setUserMsn(String userMsn) {
		this.userMsn = userMsn;
	}

	public String getUserMobile() {
		return this.userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserMobile2() {
		return this.userMobile2;
	}

	public void setUserMobile2(String userMobile2) {
		this.userMobile2 = userMobile2;
	}

	public String getUserOfficeTel() {
		return this.userOfficeTel;
	}

	public void setUserOfficeTel(String userOfficeTel) {
		this.userOfficeTel = userOfficeTel;
	}

	public String getUserFamilyTel() {
		return this.userFamilyTel;
	}

	public void setUserFamilyTel(String userFamilyTel) {
		this.userFamilyTel = userFamilyTel;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Date getUserAvidate() {
		return this.userAvidate;
	}

	public void setUserAvidate(Date userAvidate) {
		this.userAvidate = userAvidate;
	}

	public String getUserIsAgent() {
		return this.userIsAgent;
	}

	public void setUserIsAgent(String userIsAgent) {
		this.userIsAgent = userIsAgent;
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
	@JSON(serialize=false)
	public Set getUumRoleUserRefs() {
		return this.uumRoleUserRefs;
	}

	public void setUumRoleUserRefs(Set uumRoleUserRefs) {
		this.uumRoleUserRefs = uumRoleUserRefs;
	}

	public String getUumRoleName() {
		return uumRoleName;
	}

	public void setUumRoleName(String uumRoleName) {
		this.uumRoleName = uumRoleName;
	}

	public Long getUumOrgRoleId() {
		return uumOrgRoleId;
	}

	public void setUumOrgRoleId(Long uumOrgRoleId) {
		this.uumOrgRoleId = uumOrgRoleId;
	}

	public Long getBelongOrganId() {
		return belongOrganId;
	}

	public void setBelongOrganId(Long belongOrganId) {
		this.belongOrganId = belongOrganId;
	}

	public String getUumOrgName() {
		return uumOrgName;
	}

	public void setUumOrgName(String uumOrgName) {
		this.uumOrgName = uumOrgName;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	
	public boolean equals(Object obj){
		if(obj.getClass() != this.getClass())return false;
		UumUser uumUser = (UumUser)obj;
		if(uumUser.getUserCode().equals(this.getUserCode()))
			return true;
		return false;
	}

	public String getUserNamePy() {
		return userNamePy;
	}

	public void setUserNamePy(String userNamePy) {
		this.userNamePy = userNamePy;
	}

}