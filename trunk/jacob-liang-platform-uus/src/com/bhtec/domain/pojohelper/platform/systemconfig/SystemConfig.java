/**
 *功能说明：系统配置对象
 * @author jacobliang
 * @time @Nov 21, 2010 @1:34:13 PM
 */
package com.bhtec.domain.pojohelper.platform.systemconfig;

import java.util.List;

public class SystemConfig {
	private String systemName;
	private List<String> systemAdminCode;
	private String roleChange;
	private String logLevel;
	private String logKeepDays;
	
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public List<String> getSystemAdminCode() {
		return systemAdminCode;
	}
	public void setSystemAdminCode(List<String> systemAdminCode) {
		this.systemAdminCode = systemAdminCode;
	}
	public String getRoleChange() {
		return roleChange;
	}
	public void setRoleChange(String roleChange) {
		this.roleChange = roleChange;
	}
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	public String getLogKeepDays() {
		return logKeepDays;
	}
	public void setLogKeepDays(String logKeepDays) {
		this.logKeepDays = logKeepDays;
	}
}
