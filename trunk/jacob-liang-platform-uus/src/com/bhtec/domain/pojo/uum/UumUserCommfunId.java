package com.bhtec.domain.pojo.uum;

/**
 * UumUserCommfunId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UumUserCommfunId implements java.io.Serializable {

	// Fields

	private Long userId;
	private Long moduleId;

	// Constructors

	/** default constructor */
	public UumUserCommfunId() {
	}

	/** full constructor */
	public UumUserCommfunId(Long userId, Long moduleId) {
		this.userId = userId;
		this.moduleId = moduleId;
	}

	// Property accessors

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UumUserCommfunId))
			return false;
		UumUserCommfunId castOther = (UumUserCommfunId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null
				&& castOther.getUserId() != null && this.getUserId().equals(
				castOther.getUserId())))
				&& ((this.getModuleId() == castOther.getModuleId()) || (this
						.getModuleId() != null
						&& castOther.getModuleId() != null && this
						.getModuleId().equals(castOther.getModuleId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getModuleId() == null ? 0 : this.getModuleId().hashCode());
		return result;
	}

}