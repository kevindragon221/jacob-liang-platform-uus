package com.bhtec.domain.pojo.platform;

/**
 * SysplCodeId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysplCodeId implements java.io.Serializable {

	// Fields

	private Long codeId;
	private String codeName;
	private String moduleName;
	private String fieldName;
	private String delimite;
	private Integer partNum;
	private String part1;
	private String part2;
	private String part3;
	private String part4;

	// Constructors

	/** default constructor */
	public SysplCodeId() {
	}

	/** full constructor */
	public SysplCodeId(Long codeId, String codeName, String moduleName,
			String fieldName, String delimite, Integer partNum, String part1,
			String part2, String part3, String part4) {
		this.codeId = codeId;
		this.codeName = codeName;
		this.moduleName = moduleName;
		this.fieldName = fieldName;
		this.delimite = delimite;
		this.partNum = partNum;
		this.part1 = part1;
		this.part2 = part2;
		this.part3 = part3;
		this.part4 = part4;
	}

	// Property accessors

	public Long getCodeId() {
		return this.codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public String getCodeName() {
		return this.codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getDelimite() {
		return this.delimite;
	}

	public void setDelimite(String delimite) {
		this.delimite = delimite;
	}

	public Integer getPartNum() {
		return this.partNum;
	}

	public void setPartNum(Integer partNum) {
		this.partNum = partNum;
	}

	public String getPart1() {
		return this.part1;
	}

	public void setPart1(String part1) {
		this.part1 = part1;
	}

	public String getPart2() {
		return this.part2;
	}

	public void setPart2(String part2) {
		this.part2 = part2;
	}

	public String getPart3() {
		return this.part3;
	}

	public void setPart3(String part3) {
		this.part3 = part3;
	}

	public String getPart4() {
		return this.part4;
	}

	public void setPart4(String part4) {
		this.part4 = part4;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SysplCodeId))
			return false;
		SysplCodeId castOther = (SysplCodeId) other;

		return ((this.getCodeId() == castOther.getCodeId()) || (this
				.getCodeId() != null
				&& castOther.getCodeId() != null && this.getCodeId().equals(
				castOther.getCodeId())))
				&& ((this.getCodeName() == castOther.getCodeName()) || (this
						.getCodeName() != null
						&& castOther.getCodeName() != null && this
						.getCodeName().equals(castOther.getCodeName())))
				&& ((this.getModuleName() == castOther.getModuleName()) || (this
						.getModuleName() != null
						&& castOther.getModuleName() != null && this
						.getModuleName().equals(castOther.getModuleName())))
				&& ((this.getFieldName() == castOther.getFieldName()) || (this
						.getFieldName() != null
						&& castOther.getFieldName() != null && this
						.getFieldName().equals(castOther.getFieldName())))
				&& ((this.getDelimite() == castOther.getDelimite()) || (this
						.getDelimite() != null
						&& castOther.getDelimite() != null && this
						.getDelimite().equals(castOther.getDelimite())))
				&& ((this.getPartNum() == castOther.getPartNum()) || (this
						.getPartNum() != null
						&& castOther.getPartNum() != null && this.getPartNum()
						.equals(castOther.getPartNum())))
				&& ((this.getPart1() == castOther.getPart1()) || (this
						.getPart1() != null
						&& castOther.getPart1() != null && this.getPart1()
						.equals(castOther.getPart1())))
				&& ((this.getPart2() == castOther.getPart2()) || (this
						.getPart2() != null
						&& castOther.getPart2() != null && this.getPart2()
						.equals(castOther.getPart2())))
				&& ((this.getPart3() == castOther.getPart3()) || (this
						.getPart3() != null
						&& castOther.getPart3() != null && this.getPart3()
						.equals(castOther.getPart3())))
				&& ((this.getPart4() == castOther.getPart4()) || (this
						.getPart4() != null
						&& castOther.getPart4() != null && this.getPart4()
						.equals(castOther.getPart4())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCodeId() == null ? 0 : this.getCodeId().hashCode());
		result = 37 * result
				+ (getCodeName() == null ? 0 : this.getCodeName().hashCode());
		result = 37
				* result
				+ (getModuleName() == null ? 0 : this.getModuleName()
						.hashCode());
		result = 37 * result
				+ (getFieldName() == null ? 0 : this.getFieldName().hashCode());
		result = 37 * result
				+ (getDelimite() == null ? 0 : this.getDelimite().hashCode());
		result = 37 * result
				+ (getPartNum() == null ? 0 : this.getPartNum().hashCode());
		result = 37 * result
				+ (getPart1() == null ? 0 : this.getPart1().hashCode());
		result = 37 * result
				+ (getPart2() == null ? 0 : this.getPart2().hashCode());
		result = 37 * result
				+ (getPart3() == null ? 0 : this.getPart3().hashCode());
		result = 37 * result
				+ (getPart4() == null ? 0 : this.getPart4().hashCode());
		return result;
	}

}