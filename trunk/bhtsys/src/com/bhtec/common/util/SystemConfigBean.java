/**
 *功能说明：
 * @author jacobliang
 * @time @Dec 30, 2011 @11:45:49 AM
 */
package com.bhtec.common.util;

import java.util.List;

public class SystemConfigBean {
	private String loggerLevel;
	private String logRecordKeepDays;
	private List<String> systemMgr;
	private String systemName;
	private String cluster;
	private String rowPrivilegeLevel;
	
	private String tomcatJmxURL;
	private String tomcatObjectName;
	private String jmxDefault;
	
	public String getLoggerLevel() {
		return loggerLevel;
	}
	public void setLoggerLevel(String loggerLevel) {
		this.loggerLevel = loggerLevel;
	}
	public String getLogRecordKeepDays() {
		return logRecordKeepDays;
	}
	public void setLogRecordKeepDays(String logRecordKeepDays) {
		this.logRecordKeepDays = logRecordKeepDays;
	}
	public List<String> getSystemMgr() {
		return systemMgr;
	}
	public void setSystemMgr(List<String> systemMgr) {
		this.systemMgr = systemMgr;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getCluster() {
		return cluster;
	}
	public void setCluster(String cluster) {
		this.cluster = cluster;
	}
	public String getTomcatJmxURL() {
		return tomcatJmxURL;
	}
	public void setTomcatJmxURL(String tomcatJmxURL) {
		this.tomcatJmxURL = tomcatJmxURL;
	}
	public String getTomcatObjectName() {
		return tomcatObjectName;
	}
	public void setTomcatObjectName(String tomcatObjectName) {
		this.tomcatObjectName = tomcatObjectName;
	}
	public String getJmxDefault() {
		return jmxDefault;
	}
	public void setJmxDefault(String jmxDefault) {
		this.jmxDefault = jmxDefault;
	}
	public String getRowPrivilegeLevel() {
		return rowPrivilegeLevel;
	}
	public void setRowPrivilegeLevel(String rowPrivilegeLevel) {
		this.rowPrivilegeLevel = rowPrivilegeLevel;
	}
}
