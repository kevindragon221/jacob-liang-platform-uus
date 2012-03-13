/**
 *功能说明：解析系统XML配置文件
 * @author jacobliang
 * @time @Sep 11, 2010 @9:27:23 AM
 */
package com.bhtec.common.util;

import static com.bhtec.common.constant.Common.SYS_CONFIGFILE_XML;
import static com.bhtec.common.constant.Common.TOMCAT;
import static com.bhtec.common.constant.Common.WEBLOGIC;
import static com.bhtec.common.constant.Common.WEBSPHERE;
import static com.bhtec.common.tools.UtilTools.getClusterResourcePath;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;

import com.bhtec.common.tools.XmlOpUtil;
import com.bhtec.exception.ApplicationException;

public class SystemConifgXmlParse {
	static Logger log = Logger.getLogger(XmlOpUtil.class);
	private static SystemConifgXmlParse xmlSystemConifg = new SystemConifgXmlParse();
	private static long fileLastModifyTime = 0;
	private static SystemConfigBean systemConfigBean = null;
	
	private SystemConifgXmlParse(){
		
	}
	
	public static SystemConifgXmlParse getInstance(){
		loadSystemConfigDoc();
		return xmlSystemConifg;
	}
	
	/**
	 * 功能说明：解析系统配置文件并放属性到javabean
	 * @author jacobliang
	 * @time Dec 30, 2011 2:00:30 PM
	 */
	public static void loadSystemConfigDoc(){
		try {
			String fileName = getClusterResourcePath(SYS_CONFIGFILE_XML);
			File systemConfigFile  = new File(fileName);
			if(!systemConfigFile.exists())
				throw new ApplicationException("对不起，文件"+fileName+"找不到.");

			long sysConfigMod = systemConfigFile.lastModified();
			if(sysConfigMod > fileLastModifyTime){
				Document doc = XmlOpUtil.loadXMLFile(fileName);
				systemConfigBean = new SystemConfigBean();
				String loggerLevel = XmlOpUtil.getSelectSingleText(doc, "/systemConfig/loggerLevel");
				String logRecordKeepDays = XmlOpUtil.getSelectSingleText(doc, "/systemConfig/logRecordKeepDays");
				List<String> systemMgr = XmlOpUtil.selectNodesText(doc, "/systemConfig/systemMgr/userCode");
				String systemName = XmlOpUtil.getSelectSingleText(doc, "/systemConfig/systemName");
				String cluster = XmlOpUtil.getSelectSingleText(doc, "/systemConfig/cluster");
				String rowPrivilegeLevel = XmlOpUtil.getSelectSingleText(doc, "/systemConfig/rowPrivilegeLevel");
				String jmxTomcatDefault = XmlOpUtil.getSelectSingleAttribute(doc, "/systemConfig/JMX/tomcat", "default");
				String jmxWeblogicDefault = XmlOpUtil.getSelectSingleAttribute(doc, "/systemConfig/JMX/weblogic", "default");
				String jmxWebsphereDefault = XmlOpUtil.getSelectSingleAttribute(doc, "/systemConfig/JMX/websphere", "default");
				String[] defaultArr = {jmxTomcatDefault,jmxWeblogicDefault,jmxWebsphereDefault}; 
				for(int i=0,l=defaultArr.length;i<l;i++){
					String defaultDesc = defaultArr[i];
					if("true".equalsIgnoreCase(defaultDesc)){
						switch(i){
							case 0:
								systemConfigBean.setJmxDefault(TOMCAT);
								break;
							case 1:
								systemConfigBean.setJmxDefault(WEBLOGIC);
								break;
							case 2:
								systemConfigBean.setJmxDefault(WEBSPHERE);
								break;
						}
						break;
					}
				}
				String tomcatJmxURL = XmlOpUtil.getSelectSingleText(doc, "/systemConfig/JMX/tomcat/jmxURL");
				String tomcatObjectName = XmlOpUtil.getSelectSingleText(doc, "/systemConfig/JMX/tomcat/objectName");
				systemConfigBean.setLoggerLevel(loggerLevel);
				systemConfigBean.setLogRecordKeepDays(logRecordKeepDays);
				systemConfigBean.setSystemMgr(systemMgr);
				systemConfigBean.setSystemName(systemName);
				systemConfigBean.setCluster(cluster);
				systemConfigBean.setRowPrivilegeLevel(rowPrivilegeLevel);
				systemConfigBean.setTomcatJmxURL(tomcatJmxURL);
				systemConfigBean.setTomcatObjectName(tomcatObjectName);
				fileLastModifyTime = sysConfigMod;
				log.info("SystemConfig.xml reload success !");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args){
		try {
			for(;;){
				SystemConifgXmlParse xMLSystemConifg = SystemConifgXmlParse.getInstance();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static SystemConfigBean getSystemConfigBean() {
		return systemConfigBean;
	}

	public static void setSystemConfigBean(SystemConfigBean systemConfigBean) {
		SystemConifgXmlParse.systemConfigBean = systemConfigBean;
	}
	
}
